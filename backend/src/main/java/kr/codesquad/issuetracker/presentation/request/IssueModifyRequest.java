package kr.codesquad.issuetracker.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueModifyRequest {

	private String title;
	private String content;
	private Boolean isOpen;
}
