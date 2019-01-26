package com.danit.services.amazon;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.danit.exceptions.S3BucketOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class AmazonClientService {

  private AmazonS3 s3client;

  @Value("${amazon-properties.bucketName}")
  private String bucketName;
  @Value("${amazon-properties.accessKey}")
  private String accessKey;
  @Value("${amazon-properties.secretKey}")
  private String secretKey;

  public void deleteFileFromS3Bucket(String fileUrl, String folderName) {
    String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
    s3client.deleteObject(new DeleteObjectRequest(bucketName + "/" + folderName, fileName));
  }

  public InputStream getFile(String fileName, String folderName) {
    return s3client.getObject(bucketName + "/" + folderName, fileName).getObjectContent();
  }

  public String uploadFile(MultipartFile multipartFile, String folderName) {
    try {
      File file = convertMultiPartToFile(multipartFile);
      String fileName = generateFileName(multipartFile);
      uploadFileTos3bucket(fileName, folderName, file);
      file.delete();
      return fileName;
    } catch (Exception e) {
      throw new S3BucketOperationException(e.getMessage());
    }
  }

  private File convertMultiPartToFile(MultipartFile file) throws IOException {
    File outputFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
      fos.write(file.getBytes());
    }
    return outputFile;
  }

  private String generateFileName(MultipartFile multiPart) {
    return new Date().getTime() + "-" + multiPart.getOriginalFilename()
        .replace(" ", "_");
  }

  private void uploadFileTos3bucket(String fileName, String folderName, File file) {
    s3client.putObject(new PutObjectRequest(bucketName + "/" + folderName, fileName, file)
        .withCannedAcl(CannedAccessControlList.PublicRead));
  }

  @PostConstruct
  private void initializeAmazon() {
    AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
    this.s3client = AmazonS3Client.builder()
        .withRegion(Regions.EU_CENTRAL_1)
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .build();
  }

}
