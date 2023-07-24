package kr.codesquad.issuetracker.useraccount.domain;

import lombok.Getter;

@Getter
public class UserAccount {

	private Integer id;
	private String loginId;
	private String password;
	private String profileUrl;
}
