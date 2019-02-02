package com.danit.controllers.service;

import com.danit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/storage/")
public class BucketController {

  private UserService userService;

  @Autowired
  BucketController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/avatar/upload")
  public void setCurrentUserAvatar(@RequestParam(value = "file") MultipartFile file, Principal principal) {
    userService.setCurrentUserAvatar(file);
  }

  @DeleteMapping("/avatar/delete")
  public void deleteCurrentUserAvatar(Principal principal) {
    userService.deleteCurrentUserAvatar();
  }

  @GetMapping(value = "/avatar", produces = MediaType.IMAGE_PNG_VALUE)
  public ResponseEntity<byte[]> getCurrentUserAvatar(Principal principal) {
    HttpHeaders headers = new HttpHeaders();
    headers.setCacheControl(CacheControl.noCache().getHeaderValue());
    return new ResponseEntity<>(userService.getUserAvatarBase64Image(), headers, HttpStatus.OK);
  }
}
