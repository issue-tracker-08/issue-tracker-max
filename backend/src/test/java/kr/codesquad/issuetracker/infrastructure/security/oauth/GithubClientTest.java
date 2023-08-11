package kr.codesquad.issuetracker.infrastructure.security.oauth;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import kr.codesquad.issuetracker.domain.GithubUser;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.exception.OAuthAccessTokenException;
import kr.codesquad.issuetracker.infrastructure.config.oauth.OauthProperties;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

class GithubClientTest {

	private static final String ACCESS_TOKEN_RESPONSE = "{\n"
		+ "  \"access_token\":\"gho_16C7e42F292c6912E7710c838347Ae178B4a\",\n"
		+ "  \"scope\":\"user:email\",\n"
		+ "  \"token_type\":\"bearer\"\n"
		+ "}";
	private static final String USER_INFO_RESPONSE = "{\n"
		+ "    \"login\" : \"23Yong\", \n"
		+ "    \"id\" : 1, \n"
		+ "    \"node_id\" : \"MDQ6VXNlcjY2OTgxODUx\", \n"
		+ "    \"avatar_url\" : \"https://avatars.githubusercontent.com/u/66981851?v=4\", \n"
		+ "    \"gravatar_id\" : \"\", \n"
		+ "    \"url\" : \"https://api.github.com/users/23Yong\", \n"
		+ "    \"html_url\" : \"https://github.com/23Yong\", \n"
		+ "    \"followers_url\" : \"https://api.github.com/users/23Yong/followers\", \n"
		+ "    \"following_url\" : \"https://api.github.com/users/23Yong/following{/other_user}\", \n"
		+ "    \"gists_url\" : \"https://api.github.com/users/23Yong/gists{/gist_id}\", \n"
		+ "    \"starred_url\" : \"https://api.github.com/users/23Yong/starred{/owner}{/repo}\", \n"
		+ "    \"subscriptions_url\" : \"https://api.github.com/users/23Yong/subscriptions\", \n"
		+ "    \"organizations_url\" : \"https://api.github.com/users/23Yong/orgs\", \n"
		+ "    \"repos_url\" : \"https://api.github.com/users/23Yong/repos\", \n"
		+ "    \"events_url\" : \"https://api.github.com/users/23Yong/events{/privacy}\", \n"
		+ "    \"received_events_url\" : \"https://api.github.com/users/23Yong/received_events\", \n"
		+ "    \"type\" : \"User\", \n"
		+ "    \"site_admin\" : false, \n"
		+ "    \"name\" : \"JeongYong Park\", \n"
		+ "    \"company\" : null, \n"
		+ "    \"blog\" : \"\", \n"
		+ "    \"location\" : \"Bucheon\", \n"
		+ "    \"email\" : null, \n"
		+ "    \"hireable\" : null, \n"
		+ "    \"bio\" : \"Hello\", \n"
		+ "    \"twitter_username\" : null, \n"
		+ "    \"public_repos\" : 22, \n"
		+ "    \"public_gists\" : 0, \n"
		+ "    \"followers\" : 22, \n"
		+ "    \"following\" : 24, \n"
		+ "    \"created_at\" : \"2020-06-16T01:40:43Z\", \n"
		+ "    \"updated_at\" : \"2023-07-18T15:00:15Z\""
		+ "}";
	private static final String TOKEN_REQUEST_ERROR = "{\n"
		+ "  \"error\": \"incorrect_client_credentials\",\n"
		+ "  \"error_description\": \"The client_id and/or client_secret passed are incorrect.\",\n"
		+ "  \"error_uri\": \"/apps/managing-oauth-apps/troubleshooting-oauth-app-access-token-request-errors/#incorrect-client-credentials\"\n"
		+ "}";

	@DisplayName("코드와 함께 서버로 요청을 보낼 때 사용자의 정보를 가져오는데 성공한다.")
	@Test
	void getOAuthUser() throws IOException {
		//  given
		MockWebServer mockGithubServer = new MockWebServer();
		mockGithubServer.start();

		setUpMockWebServer(mockGithubServer, ACCESS_TOKEN_RESPONSE);
		setUpMockWebServer(mockGithubServer, USER_INFO_RESPONSE);

		GithubClient githubClient = new GithubClient(
			new OauthProperties(
				"clientId",
				"secretId",
				String.format("http://%s:%s", mockGithubServer.getHostName(), mockGithubServer.getPort()),
				String.format("http://%s:%s", mockGithubServer.getHostName(), mockGithubServer.getPort())
			),
			WebClient.create()
		);

		// when
		GithubUser oAuthUser = githubClient.getOAuthUser("code");

		// then
		assertThat(oAuthUser.getUsername()).isEqualTo("23Yong");
		mockGithubServer.shutdown();
	}

	@DisplayName("액세스 토큰 요청 중 오류가 생기면 예외를 던진다.")
	@Test
	void getErrorResponseFromServer_thenThrowsException() throws IOException {
		// given
		MockWebServer mockGithubServer = new MockWebServer();
		mockGithubServer.start();

		setUpMockWebServer(mockGithubServer, TOKEN_REQUEST_ERROR);

		GithubClient githubClient = new GithubClient(
			new OauthProperties(
				"clientId",
				"secretId",
				String.format("http://%s:%s", mockGithubServer.getHostName(), mockGithubServer.getPort()),
				String.format("http://%s:%s", mockGithubServer.getHostName(), mockGithubServer.getPort())
			),
			WebClient.create()
		);

		// when & then
		assertThatThrownBy(() -> githubClient.getOAuthUser("code"))
			.isInstanceOf(OAuthAccessTokenException.class)
			.extracting("errorCode").isEqualTo(ErrorCode.GITHUB_FAILED_LOGIN);
	}

	private void setUpMockWebServer(MockWebServer mockGithubServer, String stub) {
		mockGithubServer.enqueue(new MockResponse()
			.setBody(stub)
			.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
	}
}
