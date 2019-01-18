package com.danit.controllers;

import com.danit.services.amazon.AmazonClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage/")
public class BucketController {

  private AmazonClientService amazonClientService;

  @Autowired
  BucketController(AmazonClientService amazonClientService) {
    this.amazonClientService = amazonClientService;
  }

  @PostMapping("/uploadFile")
  public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
    return amazonClientService.uploadFile(file);
  }

  @DeleteMapping("/deleteFile")
  public String deleteFile(@RequestPart(value = "url") String fileUrl) {
    return amazonClientService.deleteFileFromS3Bucket(fileUrl);
  }
}
