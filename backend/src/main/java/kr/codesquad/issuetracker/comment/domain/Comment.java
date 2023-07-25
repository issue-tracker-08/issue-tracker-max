package kr.codesquad.issuetracker.comment.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Comment {

	private Integer id;
	private String content;
	private LocalDateTime createdAt;
	private Boolean isDeleted;
	private Integer userAccountId;
	private Integer issueId;
}
