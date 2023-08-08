package kr.codesquad.issuetracker.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserAccount {

	private Integer id;
	private String loginId;
	private String password;
	private String profileUrl;
	private Boolean isDeleted;

	public UserAccount(Integer id, String loginId, String password, String profileUrl) {
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.profileUrl = profileUrl;
	}

	public UserAccount(String loginId, String password) {
		this.loginId = loginId;
		this.password = password;
		this.profileUrl = "https://issue-tracker-s3.s3.ap-northeast-2.amazonaws.com/public/profile/default_profile.png";
	}

	public boolean isSamePassword(String password) {
		return this.password.equals(password);
	}

	public static UserAccount createUserProfile(Integer id, String loginId, String profileUrl) {
		return new UserAccount(id, loginId, null, profileUrl, false);
	}
}
