package kr.codesquad.issuetracker.presentation.request;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueRegisterRequest {

	@Size(max = 45, message = "이슈의 제목은 45자를 넘을 수 없습니다.")
	@NotEmpty(message = "이슈의 제목은 비워둘 수 없습니다.")
	private String title;
	private String content;
	private List<Integer> assignees;
	private List<Integer> labels;
	private Integer milestone;

	public Optional<List<Integer>> getAssignees() {
		if (assignees == null || assignees.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(assignees);
	}

	public Optional<List<Integer>> getLabels() {
		if (labels == null || labels.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(labels);
	}

	public Optional<Integer> getMilestone() {
		return Optional.ofNullable(milestone);
	}
}

