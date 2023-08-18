package kr.codesquad.issuetracker.application.s3;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import kr.codesquad.issuetracker.domain.ImageFile;
import kr.codesquad.issuetracker.infrastructure.config.AwsProperties;

@Component
public class S3Uploader {

	private final AmazonS3Client amazonS3Client;
	private final String bucket;

	public S3Uploader(AmazonS3Client amazonS3Client, AwsProperties awsProperties) {
		this.amazonS3Client = amazonS3Client;
		this.bucket = awsProperties.getS3().getBucket();
	}

	public String uploadImageToS3(ImageFile imageFile, String fileName) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(imageFile.getContentType());
		metadata.setContentLength(imageFile.getFileSize());
		amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, imageFile.getImageInputStream(), metadata)
			.withCannedAcl(CannedAccessControlList.PublicRead));
		return getObjectUri(fileName);
	}

	private String getObjectUri(String fileName) {
		return URLDecoder.decode(amazonS3Client.getUrl(bucket, fileName).toString(), StandardCharsets.UTF_8);
	}
}
