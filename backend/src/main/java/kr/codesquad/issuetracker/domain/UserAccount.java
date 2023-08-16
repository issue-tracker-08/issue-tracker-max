package kr.codesquad.issuetracker.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserAccount {

	private static final String DEFAULT_PROFILE_URL = "https://issue-tracker-s3.s3.ap-northeast-2.amazonaws.com/public/profile/default_profile.png";

	private Integer id;
	private String loginId;
	private String password;
	private String email;
	private String profileUrl;
	private Boolean isDeleted;

	public UserAccount(Integer id, String loginId, String password, String profileUrl) {
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.profileUrl = profileUrl;
	}

	public UserAccount(String loginId, String password) {
		this(loginId, password, DEFAULT_PROFILE_URL);
	}

	public UserAccount(String loginId, String password, String avatarUrl) {
		this(null, loginId, password, avatarUrl);
	}

	public static UserAccount fromOAuthData(String username, String email) {
		return new UserAccount(null, username, "", email, DEFAULT_PROFILE_URL, false);
	}

	public boolean isSamePassword(String password) {
		return this.password.equals(password);
	}

	public static UserAccount createUserProfile(Integer id, String loginId, String profileUrl) {
		return new UserAccount(id, loginId, null, null, profileUrl, false);
	}
}
