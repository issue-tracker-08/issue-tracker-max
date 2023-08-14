package kr.codesquad.issuetracker.presentation.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OauthSignupRequest {

	private String email;
	private String username;
}
