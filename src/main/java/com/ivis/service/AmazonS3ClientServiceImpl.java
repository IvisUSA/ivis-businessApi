//package com.ivis.service;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.amazonaws.AmazonServiceException;
//import com.amazonaws.services.s3.AmazonS3;
//
//import com.amazonaws.services.s3.model.PutObjectRequest;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//
//import org.slf4j.Logger;
//import com.amazonaws.services.s3.AmazonS3;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Async;
//import com.amazonaws.services.s3.model.S3Object;
//import com.amazonaws.services.s3.model.S3ObjectInputStream;
//import com.amazonaws.util.IOUtils;
//
//@Service
//public class AmazonS3ClientServiceImpl implements AWSS3Service {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(AmazonS3ClientServiceImpl.class);
//
//	@Autowired
//	AWSS3Config s3config;
//	
//	 @Autowired
//	  private AmazonS3 amazonS3;
//	
//
//	@Value("${aws.s3.bucket}")
//	private String bucketName;
//
//	@Override
//	// @Async annotation ensures that the method is executed in a different
//	// background thread
//	// but not consume the main thread.
//	@Async
//	public void uploadFile(final MultipartFile multipartFile, String camName, LocalDateTime ldt) {
//		LOGGER.info("File upload in progress.");
//		try {
//			final File file = convertMultiPartFileToFile(multipartFile);
//			uploadFileToS3Bucket(bucketName, file, camName, ldt);
//
//			LOGGER.info("File upload is completed.");
//			file.delete(); // To remove the file locally created in the project folder.
//		} catch (final AmazonServiceException ex) {
//			LOGGER.info("File upload is failed.");
//			LOGGER.error("Error= {} while uploading file.", ex.getMessage());
//		}
//	}
//
//	private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
//		final File file = new File(multipartFile.getOriginalFilename());
//		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
//			outputStream.write(multipartFile.getBytes());
//		} catch (final IOException ex) {
//			LOGGER.error("Error converting the multi-part file to file= ", ex.getMessage());
//		}
//		return file;
//	}
//
//	private void uploadFileToS3Bucket(final String bucketName, final File file, String camName, LocalDateTime ldt) {
//
//		String[] arrOfStr = camName.split("C");
//
//		final String uniqueFileName = "AnalyticData/" + arrOfStr[0] + "/" + camName + "_" + ldt + "_" + file.getName();
//		LOGGER.info("Uploading file with name= " + uniqueFileName);
//		final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file);
//
//		AmazonS3 s3Client = s3config.getAmazonS3Cient();
//		s3Client.putObject(putObjectRequest);
//
//	}
//	
//	 @Override
//	  public byte[] downloadFileFromS3(String fileName) {
//	    S3Object s3Object = amazonS3.getObject(bucketName, fileName);
//	    S3ObjectInputStream inputStream = s3Object.getObjectContent();
//	    try {
//	      byte[] content = IOUtils.toByteArray(inputStream);
//	      return content;
//	    } catch (IOException e) {
//	      e.printStackTrace();
//	    }
//	    return null;
//	  }
//
//}
//
