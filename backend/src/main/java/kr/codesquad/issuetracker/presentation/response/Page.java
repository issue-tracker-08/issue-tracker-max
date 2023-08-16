package kr.codesquad.issuetracker.presentation.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Page<T> {

	@Getter
	@AllArgsConstructor
	public static class Pagination {
		private int currentPage;
		private int totalCounts;
		private int totalPages;
	}

	private Pagination pagination;
	private List<T> data;

	public static <T> Page<T> of(List<T> data, int totalCounts, int page, int size) {
		int totalPages = (int)Math.ceil((double)totalCounts / size);
		return new Page<>(new Pagination(page, totalCounts, totalPages), data);
	}
}
