package kr.codesquad.issuetracker.presentation.request;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueRegisterRequest {

	@NotEmpty(message = "이슈의 제목은 비워둘 수 없습니다.")
	private String title;
	private String content;
	private List<Integer> assignees;
	private List<Integer> labels;
	private Integer milestone;

	public Optional<List<Integer>> getAssignees() {
		return Optional.ofNullable(assignees);
	}

	public Optional<List<Integer>> getLabels() {
		return Optional.ofNullable(labels);
	}

	public Optional<Integer> getMilestone() {
		return Optional.ofNullable(milestone);
	}
}

