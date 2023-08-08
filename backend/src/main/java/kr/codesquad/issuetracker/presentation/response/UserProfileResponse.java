package kr.codesquad.issuetracker.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserProfileResponse {

	private Integer userAccountId;
	private String username;
	private String profileUrl;
}
