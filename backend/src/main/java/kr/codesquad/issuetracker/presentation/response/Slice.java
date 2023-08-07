package kr.codesquad.issuetracker.presentation.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Slice<T> {

	private static final int HAS_NEXT_DATA_SIZE = 11;

	private List<T> data;
	private Boolean hasMore;
	private Integer nextCursor;

	public Slice(List<T> data, Integer cursor) {
		if (data.size() == HAS_NEXT_DATA_SIZE) {
			this.data = data.subList(0, 10);
			this.hasMore = true;
			this.nextCursor = cursor;
			return;
		}
		this.data = data;
		this.hasMore = false;
		this.nextCursor = cursor;
	}
}
