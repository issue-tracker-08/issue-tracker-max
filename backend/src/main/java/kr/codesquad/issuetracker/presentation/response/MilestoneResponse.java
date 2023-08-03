package kr.codesquad.issuetracker.presentation.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneResponse {

	private Integer milestoneId;
	private String milestoneName;
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private String description;
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private LocalDateTime dueDate;
	private Integer openIssueCount;
	private Integer closedIssueCount;

	public MilestoneResponse(Integer milestoneId, String milestoneName, Integer openIssueCount,
		Integer closedIssueCount) {
		this(milestoneId, milestoneName, null, null, openIssueCount, closedIssueCount);
	}

}
