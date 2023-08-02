package kr.codesquad.issuetracker.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import kr.codesquad.issuetracker.domain.ImageFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class S3Service {

    private static final String UPLOADED_IMAGES = "public/uploaded-images/";
    private static final String PROFILE_IMAGES = "public/profile-images/";

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(MultipartFile file) {

        ImageFile imageFile = ImageFile.from(file);

        // 버킷에 저장할 파일 이름 생성
        String fileName = UPLOADED_IMAGES + imageFile.getRandomName();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(imageFile.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, imageFile.getImageInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return URLDecoder.decode(amazonS3Client.getUrl(bucket, fileName).toString(), StandardCharsets.UTF_8);
    }
}
