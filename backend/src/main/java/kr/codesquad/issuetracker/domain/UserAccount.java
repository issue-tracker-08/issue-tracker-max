package kr.codesquad.issuetracker.domain;

import lombok.Getter;

@Getter
public class UserAccount {

	private Integer id;
	private String loginId;
	private String password;
	private String profileUrl;
	private Boolean isDeleted;

	public UserAccount(String loginId, String password) {
		this.loginId = loginId;
		this.password = password;
		this.profileUrl = "defaultImageUrl";
	}
}
