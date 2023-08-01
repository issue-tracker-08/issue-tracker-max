package kr.codesquad.issuetracker.presentation.request;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import kr.codesquad.issuetracker.domain.IssueAssignee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssigneeRequest {

	private Integer issueId;
	private List<Integer> addUserAccountsId;
	private List<Integer> removeUserAccountsId;

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public List<IssueAssignee> getAddIssueAssignees() {
		return toIssueAssignees(addUserAccountsId);
	}

	public List<IssueAssignee> getRemoveIssueAssignees() {
		return toIssueAssignees(removeUserAccountsId);
	}

	private List<IssueAssignee> toIssueAssignees(List<Integer> userAccountsId) {
		if (Objects.isNull(userAccountsId)) {
			return Collections.emptyList();
		}

		return userAccountsId.stream()
			.map(userAccountId -> new IssueAssignee(issueId, userAccountId))
			.collect(Collectors.toList());
	}
}
