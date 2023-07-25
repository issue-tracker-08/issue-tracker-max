package kr.codesquad.issuetracker.useraccount.domain;

import lombok.Getter;

@Getter
public class IssueAssignee {

	private Integer id;
	private Integer issueId;
	private Integer userAccountId;
}
