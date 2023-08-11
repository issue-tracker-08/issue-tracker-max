package kr.codesquad.issuetracker.infrastructure.security.oauth;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import kr.codesquad.issuetracker.domain.GithubUser;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.exception.OAuthAccessTokenException;
import kr.codesquad.issuetracker.infrastructure.config.oauth.OauthProperties;

@Component
public class GithubClient {

	private final String clientId;
	private final String secretId;
	private final OauthProperties oauthProperties;
	private final WebClient githubLoginClient;
	private final WebClient githubResourceClient;

	public GithubClient(OauthProperties oauthProperties, WebClient webClient) {
		this.clientId = oauthProperties.getClientId();
		this.secretId = oauthProperties.getSecretId();
		this.oauthProperties = oauthProperties;
		this.githubLoginClient = buildGithubLoginClient(oauthProperties, webClient);
		this.githubResourceClient = buildGithubOpenApiClient(oauthProperties, webClient);
	}

	private WebClient buildGithubLoginClient(OauthProperties oauthProperties, WebClient webClient) {
		return webClient.mutate()
			.baseUrl(oauthProperties.getGithubOauthUrl())
			.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
			.build();
	}

	private WebClient buildGithubOpenApiClient(OauthProperties oauthProperties, WebClient webClient) {
		return webClient.mutate()
			.baseUrl(oauthProperties.getGithubResourceUrl())
			.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
			.build();
	}

	public GithubUser getOAuthUser(final String code) {
		String accessToken = getAccessToken(code);
		return getGithubUser(accessToken);
	}

	private String getAccessToken(final String code) {
		Map<String, Object> response = githubLoginClient
			.post()
			.uri(uriBuilder -> uriBuilder
				.path("/access_token")
				.queryParam("code", code)
				.queryParam("client_id", clientId)
				.queryParam("client_secret", secretId)
				.build())
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.blockOptional()
			.orElseThrow(() -> new ApplicationException(ErrorCode.GITHUB_FAILED_LOGIN));

		validateExistsAccessToken(response);
		return response.get("access_token").toString();
	}

	private void validateExistsAccessToken(Map<String, Object> response) {
		if (!response.containsKey("access_token")) {
			throw new OAuthAccessTokenException(response.get("error_description").toString(),
				ErrorCode.GITHUB_FAILED_LOGIN);
		}
	}

	private GithubUser getGithubUser(final String token) {
		Map<String, Object> response = githubResourceClient
			.get()
			.uri("/user")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.blockOptional()
			.orElseThrow(() -> new ApplicationException(ErrorCode.GITHUB_FAILED_LOGIN));
		return new GithubUser(response);
	}

	public String getClientId() {
		return clientId;
	}

	public String getGithubLoginBaseUrl() {
		return oauthProperties.getGithubOauthUrl();
	}
}
