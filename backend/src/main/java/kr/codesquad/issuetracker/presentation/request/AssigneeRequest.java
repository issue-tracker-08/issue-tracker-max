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

	private List<Integer> addUserAccountsId;
	private List<Integer> removeUserAccountsId;

	public List<Integer> getAddUserAccountsId() {
		return Optional.ofNullable(addUserAccountsId)
			.orElseGet(Collections::emptyList);
	}

	public List<Integer> getRemoveUserAccountsId() {
		return Optional.ofNullable(removeUserAccountsId)
			.orElseGet(Collections::emptyList);
	}
}
