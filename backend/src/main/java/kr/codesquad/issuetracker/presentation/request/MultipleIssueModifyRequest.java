package kr.codesquad.issuetracker.presentation.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MultipleIssueModifyRequest {

	private String state;
	private List<Integer> issueIds;
}
