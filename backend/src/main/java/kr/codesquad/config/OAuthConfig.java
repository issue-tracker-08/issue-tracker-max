package kr.codesquad.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import kr.codesquad.issuetracker.infrastructure.config.oauth.OauthProperties;

@EnableConfigurationProperties(OauthProperties.class)
public class OAuthConfig {
}
