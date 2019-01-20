package com.danit.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.danit.ApplicationProperties;
import com.danit.dto.service.PasswordStoreDto;
import com.danit.dto.service.UserListRequestDto;
import com.danit.exceptions.EmailNotFoundException;
import com.danit.exceptions.InvalidJwtTokenException;
import com.danit.exceptions.UserPasswordsNonEqualsException;
import com.danit.models.User;
import com.danit.repositories.UserRepository;
import com.danit.services.email.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserService extends AbstractBaseEntityService<User, UserListRequestDto> {

  private EmailService emailService;

  private UserRepository userRepository;

  private ApplicationProperties applicationProperties;

  private BCryptPasswordEncoder bcryptPasswordEncoder;

  public UserService(EmailService emailService, UserRepository userRepository,
                     ApplicationProperties applicationProperties, BCryptPasswordEncoder bcryptPasswordEncoder) {
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

  public void updateUserPasswordByJWTtokenValidation(PasswordStoreDto data) {
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
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    User user = userRepository.findByUsername(userName);
    if(bcryptPasswordEncoder.encode(data.getOldPassword())
        .equals(user.getPassword())) {
      user.setPassword(
          bcryptPasswordEncoder.encode(data.getNewPassword()));
      List<User> users = new ArrayList<>();
      users.add(user);
      super.updateEntities(users);
    } else {
      throw new UserPasswordsNonEqualsException("old password and new password mismatch");
    }

  }

}