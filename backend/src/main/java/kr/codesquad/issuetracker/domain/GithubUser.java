package kr.codesquad.issuetracker.domain;

import java.util.Map;

public class GithubUser {

	private final Map<String, Object> userInfo;

	public GithubUser(Map<String, Object> userInfo) {
		this.userInfo = userInfo;
	}

	public String getEmail() {
		return userInfo.get("email").toString();
	}

	public String getAvatarUrl() {
		return userInfo.get("avatar_url").toString();
	}
}
