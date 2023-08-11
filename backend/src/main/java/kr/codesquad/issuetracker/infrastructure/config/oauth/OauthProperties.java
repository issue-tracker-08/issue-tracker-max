package kr.codesquad.issuetracker.infrastructure.config.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@Getter
@ConfigurationProperties("oauth")
public class OauthProperties {

	private final String clientId;
	private final String secretId;
	private final String githubOauthUrl;
	private final String githubResourceUrl;

	@ConstructorBinding
	public OauthProperties(String clientId, String secretId, String githubOauthUrl, String githubResourceUrl) {
		this.clientId = clientId;
		this.secretId = secretId;
		this.githubOauthUrl = githubOauthUrl;
		this.githubResourceUrl = githubResourceUrl;
	}
}
