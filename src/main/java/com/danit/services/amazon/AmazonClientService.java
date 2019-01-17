package com.danit.services.amazon;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class AmazonClientService {

  private AmazonS3 s3client;

  @Value("${amazon-properties.endpointUrl}")
  private String endpointUrl;
  @Value("${amazon-properties.bucketName}")
  private String bucketName;
  @Value("${amazon-properties.accessKey}")
  private String accessKey;
  @Value("${amazon-properties.secretKey}")
  private String secretKey;

  @PostConstruct
  private void initializeAmazon() {
    AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
    this.s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
  }

  public String deleteFileFromS3Bucket(String fileUrl) {
    String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
    s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
    return "Successfully deleted";
  }

  public String uploadFile(MultipartFile multipartFile) {

    String fileUrl = "";
    try {
      File file = convertMultiPartToFile(multipartFile);
      String fileName = generateFileName(multipartFile);
      fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
      uploadFileTos3bucket(fileName, file);
      file.delete();
    } catch (Exception e) {
      log.info("can't upload file to s3 bucket", e);
    }
    return fileUrl;
  }

  private File convertMultiPartToFile(MultipartFile file) throws IOException {
    File outputFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
      fos.write(file.getBytes());
    }
    return outputFile;
  }

  private String generateFileName(MultipartFile multiPart) {
    return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
  }

  private void uploadFileTos3bucket(String fileName, File file) {
    s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
        .withCannedAcl(CannedAccessControlList.PublicRead));
  }

}
