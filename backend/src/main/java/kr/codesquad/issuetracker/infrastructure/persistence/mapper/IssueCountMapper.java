package kr.codesquad.issuetracker.infrastructure.persistence.mapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(value = AccessLevel.PRIVATE)
@Getter
public class IssueCountMapper {

	private int totalCounts;
	private int openCounts;

	public int getClosedCounts() {
		return totalCounts - openCounts;
	}
}
