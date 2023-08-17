package kr.codesquad.issuetracker.infrastructure.persistence.mapper;

import lombok.Getter;

@Getter
public class IssueCountMapper {

	private int openIssueCounts;
	private int totalCounts;

	public IssueCountMapper(int openIssueCounts, int totalCounts) {
		this.openIssueCounts = openIssueCounts;
		this.totalCounts = totalCounts;
	}
}
