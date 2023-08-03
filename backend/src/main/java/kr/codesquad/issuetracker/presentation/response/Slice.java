package kr.codesquad.issuetracker.presentation.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Slice<T> {

	private List<T> data;
	private Boolean hasMore;
	private Integer nextCursor;
}
