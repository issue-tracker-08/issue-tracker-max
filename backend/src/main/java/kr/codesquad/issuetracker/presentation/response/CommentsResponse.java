package kr.codesquad.issuetracker.presentation.response;

import java.time.LocalDateTime;

import kr.codesquad.issuetracker.presentation.support.DateTimeFormatterUtil;
import lombok.Getter;

@Getter
public class CommentsResponse {

	private Integer commentId;
	private String username;
	private String profileUrl;
	private String content;
	private String createdAt;

	public CommentsResponse(Integer commentId, String username, String profileUrl, String content,
		LocalDateTime createdAt) {
		this.commentId = commentId;
		this.username = username;
		this.profileUrl = profileUrl;
		this.content = content;
		this.createdAt = DateTimeFormatterUtil.toString(createdAt);
	}
}
