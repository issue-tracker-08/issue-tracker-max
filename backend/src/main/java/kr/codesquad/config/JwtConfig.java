package kr.codesquad.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import kr.codesquad.issuetracker.infrastructure.config.jwt.JwtProperties;

@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {
}
