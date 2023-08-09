package kr.codesquad.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import kr.codesquad.issuetracker.infrastructure.config.AwsProperties;

@EnableConfigurationProperties(AwsProperties.class)
public class AwsConfig {
}
