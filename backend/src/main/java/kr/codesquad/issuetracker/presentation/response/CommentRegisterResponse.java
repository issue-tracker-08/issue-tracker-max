package kr.codesquad.issuetracker.presentation.response;

import java.time.LocalDateTime;

import kr.codesquad.issuetracker.presentation.support.DateTimeFormatterUtil;
import lombok.Getter;

@Getter
public class CommentRegisterResponse {

	private final Integer commentId;
	private final String content;
	private final String createdAt;

	public CommentRegisterResponse(Integer commentId, String content, LocalDateTime createdAt) {
		this.commentId = commentId;
		this.content = content;
		this.createdAt = DateTimeFormatterUtil.toString(createdAt);
	}
}
