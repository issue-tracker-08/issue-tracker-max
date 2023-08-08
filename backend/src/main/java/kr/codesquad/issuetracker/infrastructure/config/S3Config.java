package kr.codesquad.issuetracker.infrastructure.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import kr.codesquad.config.AwsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import(AwsConfig.class)
@Profile({"!test"})
public class S3Config {

    private final String accessKey;
    private final String secretKey;
    private final String region;

    public S3Config(AwsProperties awsProperties) {
        this.accessKey = awsProperties.getCredentials().getAccessKey();
        this.secretKey = awsProperties.getCredentials().getSecretKey();
        this.region = awsProperties.getRegion().get("static");
    }

    @Bean
    public AmazonS3Client S3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }
}
