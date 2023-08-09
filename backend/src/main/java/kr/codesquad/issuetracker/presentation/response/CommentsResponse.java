package kr.codesquad.issuetracker.presentation.response;

import java.time.LocalDateTime;

import kr.codesquad.issuetracker.presentation.support.DateTimeFormatterUtil;
import lombok.Getter;

@Getter
public class CommentsResponse {

	private Integer id;
	private String username;
	private String profileUrl;
	private String content;
	private String createdAt;

	public CommentsResponse(Integer id, String username, String profileUrl, String content, LocalDateTime createdAt) {
		this.id = id;
		this.username = username;
		this.profileUrl = profileUrl;
		this.content = content;
		this.createdAt = DateTimeFormatterUtil.toString(createdAt);
	}
}
