package kr.codesquad.issuetracker.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueModifyRequest {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class IssueTitleModifyRequest {

		private String title;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class IssueContentModifyRequest {

		private String content;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class IssueIsOpenModifyRequest {

		private Boolean isOpen;
	}
}
