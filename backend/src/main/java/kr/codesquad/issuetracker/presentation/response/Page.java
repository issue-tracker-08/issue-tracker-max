package kr.codesquad.issuetracker.presentation.response;

import java.util.List;

import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueCountMapper;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Page<T> {

	private Pagination pagination;
	private List<T> data;

	@AllArgsConstructor
	@Getter
	public static class Pagination {

		private int currentPage;
		private int totalPages;
		private int totalCounts;
		private int openCounts;
		private int closedCounts;
	}

	public static Page<IssueSimpleMapper> createIssuePage(List<IssueSimpleMapper> issues, IssueCountMapper issueCountMapper, int page, int size) {
		final int totalPages = (int) Math.ceil((double) issueCountMapper.getTotalCounts() / size);
		final Pagination pagination = new Pagination(page, totalPages, issueCountMapper.getTotalCounts(),
			issueCountMapper.getOpenCounts(),
			issueCountMapper.getClosedCounts());

		return new Page<>(pagination, issues);
	}
}
