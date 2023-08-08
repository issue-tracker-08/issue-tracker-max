package kr.codesquad.issuetracker.domain;

import lombok.Getter;

@Getter
public class IssueLabel {

	private Integer id;
	private Integer issueId;
	private Integer labelId;

	public IssueLabel(Integer issueId, Integer labelId) {
		this.issueId = issueId;
		this.labelId = labelId;
	}
}
