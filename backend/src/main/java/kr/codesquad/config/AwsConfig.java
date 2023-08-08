package kr.codesquad.config;

import kr.codesquad.issuetracker.infrastructure.config.AwsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AwsProperties.class)
public class AwsConfig {
}
