package kr.codesquad.issuetracker.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginSuccessResponse {

	private TokenResponse token;
	private UserAccountResponse user;

	@Getter
	public static class TokenResponse {

		private final String tokenType;
		private final String accessToken;

		public TokenResponse(String accessToken) {
			this.tokenType = "Bearer";
			this.accessToken = accessToken;
		}
	}

	@Getter
	@AllArgsConstructor
	public static class UserAccountResponse {

		private final String profileUrl;
		private final String username;
	}

	public LoginSuccessResponse(String accessToken, String profileUrl, String username) {
		this.token = new TokenResponse(accessToken);
		this.user = new UserAccountResponse(profileUrl, username);
	}
}
