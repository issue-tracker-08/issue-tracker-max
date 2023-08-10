package kr.codesquad.issuetracker.infrastructure.persistence.mapper;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
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
