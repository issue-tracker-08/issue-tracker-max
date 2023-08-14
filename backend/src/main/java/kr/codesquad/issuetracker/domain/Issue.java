package kr.codesquad.issuetracker.domain;

import java.time.LocalDateTime;
import java.util.Objects;

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

	public Issue(Integer id, String title, String content, Boolean isOpen, Integer userAccountId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.isOpen = isOpen;
		this.userAccountId = userAccountId;
	}

	public boolean isAuthor(Integer userAccountId) {
		return Objects.equals(this.userAccountId, userAccountId);
	}

	public void modifyTitle(String title) {
		this.title = title;
	}

	public void modifyContent(String content) {
		this.content = content;
	}

	public void modifyOpenStatus(String isOpen) {
		this.isOpen = Boolean.parseBoolean(isOpen);
	}
}
