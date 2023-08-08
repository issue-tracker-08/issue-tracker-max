package kr.codesquad.issuetracker.presentation.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueDetailSidebarResponse {

	private List<Integer> assigneeIds;
	private List<Integer> labelIds;
	private Integer milestoneId;
}
