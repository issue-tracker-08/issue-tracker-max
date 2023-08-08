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
		private final long expirationTime;

		public TokenResponse(String accessToken, long expirationTime) {
			this.tokenType = "Bearer";
			this.accessToken = accessToken;
			this.expirationTime = expirationTime;
		}
	}

	@Getter
	@AllArgsConstructor
	public static class UserAccountResponse {

		private final String profileUrl;
		private final String username;
	}

	public LoginSuccessResponse(TokenResponse token, String profileUrl, String username) {
		this.token = new TokenResponse(token.getAccessToken(), token.getExpirationTime());
		this.user = new UserAccountResponse(profileUrl, username);
	}
}
