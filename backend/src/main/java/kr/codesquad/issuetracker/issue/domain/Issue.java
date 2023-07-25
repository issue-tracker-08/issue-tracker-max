package kr.codesquad.issuetracker.issue.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Issue {

	private Integer id;
	private String title;
	private String content;
	private Boolean isOpen;
	private LocalDateTime createdAt;
	private Boolean isDeleted;
	private Integer userAccountId;
	private Integer milestoneId;
}
