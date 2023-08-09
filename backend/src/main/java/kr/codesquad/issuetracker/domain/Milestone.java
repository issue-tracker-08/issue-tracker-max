package kr.codesquad.issuetracker.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Milestone {

	private Integer id;
	private String name;
	private String description;
	private LocalDateTime dueDate;
	private Boolean isDeleted;

	public Milestone(Integer id, String name, String description, Timestamp dueDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		if (dueDate != null) {
			this.dueDate = dueDate.toLocalDateTime();
		}
	}

	public Milestone(String name, String description, LocalDateTime dueDate) {
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
	}

	public void modify(String name, String description, LocalDateTime dueDate) {
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
	}
}
