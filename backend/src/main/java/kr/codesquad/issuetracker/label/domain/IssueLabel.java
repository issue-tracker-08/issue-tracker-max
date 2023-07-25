package kr.codesquad.issuetracker.label.domain;

import lombok.Getter;

@Getter
public class IssueLabel {

	private Integer id;
	private Integer issueId;
	private Integer labelId;
}
