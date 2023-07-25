package kr.codesquad.issuetracker.milestone.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Milestone {

	private Integer id;
	private String name;
	private String description;
	private LocalDateTime dueDate;
	private Boolean isDeleted;
}
