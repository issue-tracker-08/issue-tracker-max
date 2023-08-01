package kr.codesquad.issuetracker.domain;

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

	public Issue(String title, String content, Boolean isOpen, Integer userAccountId, Integer milestoneId) {
		this.title = title;
		this.content = content;
		this.isOpen = isOpen;
		this.userAccountId = userAccountId;
		this.milestoneId = milestoneId;
	}
}
