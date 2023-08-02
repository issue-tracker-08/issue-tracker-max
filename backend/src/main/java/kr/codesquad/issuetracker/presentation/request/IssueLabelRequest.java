package kr.codesquad.issuetracker.presentation.request;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IssueLabelRequest {

	private List<Integer> addLabelsId;
	private List<Integer> removeLabelsId;

	public List<Integer> getAddLabelsId() {
		return Optional.ofNullable(addLabelsId)
			.orElseGet(Collections::emptyList);
	}

	public List<Integer> getRemoveLabelsId() {
		return Optional.ofNullable(removeLabelsId)
			.orElseGet(Collections::emptyList);
	}
}
