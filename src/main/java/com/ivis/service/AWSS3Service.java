package com.ivis.service;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {

	void uploadFile(MultipartFile multipartFile, String camName, LocalDateTime ldt);
	byte[] downloadFileFromS3(String fileName);
}
