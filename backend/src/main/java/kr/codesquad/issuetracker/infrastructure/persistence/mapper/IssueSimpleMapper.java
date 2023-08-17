package kr.codesquad.issuetracker.infrastructure.persistence.mapper;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(value = AccessLevel.PRIVATE)
@Getter
public class IssueSimpleMapper {

	private Integer issueNumber;
	private Boolean isOpen;
	private String title;
	private LocalDateTime createdAt;
	private String milestone;
	private AuthorMapper author;
	private List<LabelSimpleMapper> labels;
	private List<AssigneeSimpleMapper> assignees;
}
