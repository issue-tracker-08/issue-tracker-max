package kr.codesquad.issuetracker.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Milestone {

	private Integer id;
	private String name;
	private String description;
	private LocalDateTime dueDate;
	private Boolean isDeleted;

	public Milestone(String name, String description, LocalDateTime dueDate) {
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
	}
}
