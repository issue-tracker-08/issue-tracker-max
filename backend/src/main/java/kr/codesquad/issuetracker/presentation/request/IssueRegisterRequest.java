package kr.codesquad.issuetracker.presentation.request;

import java.util.Collections;
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

	public List<Integer> getAssignees() {
		return Optional.ofNullable(assignees)
			.orElseGet(Collections::emptyList);
	}

	public List<Integer> getLabels() {
		return Optional.ofNullable(labels)
			.orElseGet(Collections::emptyList);
	}

	public Optional<Integer> getMilestone() {
		return Optional.ofNullable(milestone);
	}
}

