package kr.codesquad.issuetracker.application;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import kr.codesquad.issuetracker.domain.ImageFile;
import kr.codesquad.issuetracker.infrastructure.config.AwsProperties;

@Service
public class S3Service {

    private static final String UPLOADED_IMAGES_DIR = "public/uploaded-images/";
    private static final String PROFILE_IMAGES_DIR = "public/profile-images/";

    private final AmazonS3Client amazonS3Client;
    private final String bucket;

    public S3Service(AmazonS3Client amazonS3Client, AwsProperties awsProperties) {
        this.amazonS3Client = amazonS3Client;
        this.bucket = awsProperties.getS3().getBucket();
    }

    public String uploadImage(MultipartFile file) {
        ImageFile imageFile = ImageFile.from(file);

        // 버킷에 저장할 파일 이름 생성
        String fileName = UPLOADED_IMAGES_DIR + imageFile.getRandomName();

        uploadImageToS3(imageFile, fileName);
        return getObjectUri(fileName);
    }

    private void uploadImageToS3(ImageFile imageFile, String fileName) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(imageFile.getContentType());
        metadata.setContentLength(imageFile.getFileSize());
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, imageFile.getImageInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private String getObjectUri(String fileName) {
        return URLDecoder.decode(amazonS3Client.getUrl(bucket, fileName).toString(), StandardCharsets.UTF_8);
    }
}
