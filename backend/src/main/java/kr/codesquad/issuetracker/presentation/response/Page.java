package kr.codesquad.issuetracker.presentation.response;

import java.util.List;

import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueCountMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Page<T> {

	private int openIssueCounts;
	private int closedIssueCounts;
	private Pagination pagination;
	private List<T> data;

	@Getter
	@AllArgsConstructor
	public static class Pagination {
		private int currentPage;
		private int totalCounts;
		private int totalPages;

	}

	public static <T> Page<T> of(List<T> data, IssueCountMapper counts, int page, int size) {
		int totalPages = (int)Math.ceil((double)counts.getTotalCounts() / size);
		return new Page<>(counts.getOpenIssueCounts(), counts.getTotalCounts() - counts.getOpenIssueCounts(),
			new Pagination(page, counts.getTotalCounts(), totalPages), data);
	}
}
