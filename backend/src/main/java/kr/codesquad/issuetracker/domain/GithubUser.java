package kr.codesquad.issuetracker.domain;

import java.util.Map;

public class GithubUser {

	private final Map<String, Object> userInfo;

	public GithubUser(Map<String, Object> userInfo) {
		this.userInfo = userInfo;
	}

	public String getUsername() {
		String login = userInfo.get("login").toString();
		String id = userInfo.get("id").toString();
		return login + id;
	}
}
