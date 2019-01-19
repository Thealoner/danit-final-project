package com.danit.controllers;

import com.amazonaws.util.IOUtils;
import com.danit.services.amazon.AmazonClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/storage/")
public class BucketController {

  private AmazonClientService amazonClientService;

  @Autowired
  BucketController(AmazonClientService amazonClientService) {
    this.amazonClientService = amazonClientService;
  }

  @PostMapping("/uploadFile")
  public void uploadFile(@RequestPart(value = "file") MultipartFile file) {
    amazonClientService.uploadFile(file);
  }

  @DeleteMapping("/deleteFile")
  public void deleteFile(@RequestPart(value = "url") String fileUrl) {
    amazonClientService.deleteFileFromS3Bucket(fileUrl);
  }

  @GetMapping("/{fileName}")
  public ResponseEntity<byte[]> getImageAsResponseEntity(
      @PathVariable(name = "fileName") String fileName) throws IOException {
    HttpHeaders headers = new HttpHeaders();
    InputStream in = amazonClientService.getFile(fileName);
    byte[] media = IOUtils.toByteArray(in);
    headers.setCacheControl(CacheControl.noCache().getHeaderValue());
    return new ResponseEntity<>(media, headers, HttpStatus.OK);
  }
}
