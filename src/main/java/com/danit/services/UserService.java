package com.danit.services;

import com.amazonaws.util.IOUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.danit.ApplicationProperties;
import com.danit.dto.service.PasswordStoreDto;
import com.danit.dto.service.UserListRequestDto;
import com.danit.exceptions.EmailNotFoundException;
import com.danit.exceptions.ImageFormatException;
import com.danit.exceptions.InvalidJwtTokenException;
import com.danit.exceptions.UserPasswordsNonEqualsException;
import com.danit.models.User;
import com.danit.repositories.UserRepository;
import com.danit.services.amazon.AmazonClientService;
import com.danit.services.email.EmailService;
import com.danit.utils.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserService extends AbstractBaseEntityService<User, UserListRequestDto> {

  private AmazonClientService amazonClientService;

  private ServiceUtils serviceUtils;

  private EmailService emailService;

  private UserRepository userRepository;

  private ApplicationProperties applicationProperties;

  private BCryptPasswordEncoder bcryptPasswordEncoder;

  @Value("${avatar-image-properties.width}")
  private int imageWidth;

  @Value("${avatar-image-properties.height}")
  private int imageHeight;

  public UserService(AmazonClientService amazonClientService, ServiceUtils serviceUtils, EmailService emailService,
                     UserRepository userRepository,
                     ApplicationProperties applicationProperties, BCryptPasswordEncoder bcryptPasswordEncoder) {
    this.amazonClientService = amazonClientService;
    this.serviceUtils = serviceUtils;
    this.emailService = emailService;
    this.userRepository = userRepository;
    this.applicationProperties = applicationProperties;
    this.bcryptPasswordEncoder = bcryptPasswordEncoder;
  }

  public Page<User> findAllUsersWithRoleId(long id, Pageable pageable) {
    return userRepository.findAllByRoles_Id(id, pageable);
  }

  public void generatePasswordResetConfirmationMail(String email) {
    User user = userRepository.findByEmail(email);
    if (Objects.nonNull(user)) {
      String token = JWT.create()
          .withSubject(user.getUsername())
          .withExpiresAt(new Date(System.currentTimeMillis() +
              applicationProperties.getAuthTokenExpTime()))
          .sign(Algorithm.HMAC512(applicationProperties.getSecretKey().getBytes()));
      emailService.sendSimpleMessage(email, "password reset", token);
    } else {
      throw new EmailNotFoundException("can't find user with email= " + email);
    }
  }

  public void updateUserPasswordByJwtTokenValidation(PasswordStoreDto data) {
    DecodedJWT decodedJwt;
    try {
      decodedJwt = JWT.require(Algorithm.HMAC512(applicationProperties.getSecretKey().getBytes()))
          .build()
          .verify(data.getToken());
    } catch (JWTVerificationException e) {
      throw new InvalidJwtTokenException("jwt token is invalid");
    }
    User user = userRepository.findByUsername(decodedJwt.getSubject());
    if (Objects.nonNull(user)) {
      user.setPassword(
          bcryptPasswordEncoder.encode(data.getNewPassword()));
      List<User> users = new ArrayList<>();
      users.add(user);
      super.updateEntities(users);
    } else {
      throw new InvalidJwtTokenException("jwt token contains nonexistent user= " + decodedJwt.getSubject());
    }
  }

  public void changeUserPasswordByOldPasswordValidation(PasswordStoreDto data) {
    User user = serviceUtils.getUserFromAuthContext();
    if (bcryptPasswordEncoder.matches(data.getOldPassword(), user.getPassword())) {
      user.setPassword(
          bcryptPasswordEncoder.encode(data.getNewPassword()));
      List<User> users = new ArrayList<>();
      users.add(user);
      super.updateEntities(users);
    } else {
      throw new UserPasswordsNonEqualsException("previous password is incorrect");
    }
  }

  public byte[] getUserAvatarBase64Image() {
    String fileName = getCurrentUserAvatarImageName();
    InputStream in = fileName.length() > 0
        ? amazonClientService.getFile(fileName, "avatars")
        : amazonClientService.getFile("default_avatar.png", "avatars");
    byte[] media = new byte[0];
    try {
      media = IOUtils.toByteArray(in);
    } catch (IOException e) {
      log.error("can't convert avatar image to byte array", e);
    }
    return Base64.getEncoder().encode(media);
  }

  @Transactional
  public void setCurrentUserAvatar(MultipartFile file) {
    try {
      BufferedImage image = ImageIO.read(file.getInputStream());
      if (!file.getOriginalFilename().toLowerCase().endsWith(".png")) {
        throw new ImageFormatException("only .png format is acceptable");
      }
      if (image.getWidth() > imageWidth || image.getHeight() > imageHeight) {
        throw new ImageFormatException(image.getWidth() > imageWidth ?
            "exceeded max width of image, limit= " + imageWidth
            : "exceeded max height of image, limit= " + imageHeight);
      }
      deleteCurrentUserAvatar();
      setUserAvatar(
          amazonClientService.uploadFile(file, "avatars"));
    } catch (IOException e) {
      log.error("cant convert incoming avatar data to buffered image", e);
    }
  }

  @Transactional
  public void deleteCurrentUserAvatar() {
    String fileName = getCurrentUserAvatarImageName();
    if (fileName.length() > 0) {
      amazonClientService.deleteFileFromS3Bucket(fileName, "avatars");
      setUserAvatar("");
    }
  }

  public User findUserByUsername(String name) {
    return userRepository.findByUsername(name);
  }

  private String getCurrentUserAvatarImageName() {
    return serviceUtils.getUserFromAuthContext().getAvatarImageName();
  }

  private void setUserAvatar(String fileName) {
    User user = serviceUtils.getUserFromAuthContext();
    user.setAvatarImageName(fileName);
  }

}