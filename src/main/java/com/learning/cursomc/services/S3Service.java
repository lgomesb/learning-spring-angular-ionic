package com.learning.cursomc.services;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.learning.cursomc.resources.exception.FileException;

@Service
public class S3Service {

	private final Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3Client;

	@Value("${s3.bucket}")
	private String bucketName;

	public URI uploadFile(MultipartFile multipartFile) throws Exception {
		String fileName = multipartFile.getOriginalFilename();
		InputStream is = multipartFile.getInputStream();
		String contentType = multipartFile.getContentType();
		return uploadFile(is, fileName, contentType);
	}

	public URI uploadFile(InputStream is, String fileName, String contentType) {
		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			LOG.info("Iniciando upload");
			s3Client.putObject(bucketName, fileName, is, meta);
			LOG.info("Upload finalizado");
			return s3Client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URL para URI: " + e.getMessage());
		}
	
	}

}
