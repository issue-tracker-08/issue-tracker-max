package kr.codesquad.issuetracker.presentation.request;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssigneeRequest {

	private List<Integer> addUserAccountId;
	private List<Integer> removeUserAccountId;

	public List<Integer> getAddUserAccountId() {
		return Optional.ofNullable(addUserAccountId)
			.orElseGet(Collections::emptyList);
	}

	public List<Integer> getRemoveUserAccountId() {
		return Optional.ofNullable(removeUserAccountId)
			.orElseGet(Collections::emptyList);
	}
}
