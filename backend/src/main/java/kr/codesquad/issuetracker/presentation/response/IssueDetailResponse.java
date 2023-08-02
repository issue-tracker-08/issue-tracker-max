package kr.codesquad.issuetracker.presentation.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueDetailResponse {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private Integer issueId;
	private String title;
	private Boolean isOpen;
	private String createdAt;
	private String content;
	private Author author;
	private List<Assignee> assignees;
	private List<LabelInfo> labels;
	private MilestoneInfo milestone;

	public IssueDetailResponse(Integer issueId, String title, Boolean isOpen, LocalDateTime createdAt, String content,
		Author author, List<Assignee> assignees, List<LabelInfo> labels, MilestoneInfo milestone) {
		this.issueId = issueId;
		this.title = title;
		this.isOpen = isOpen;
		this.createdAt = formatter.format(createdAt);
		this.content = content;
		this.author = author;
		this.assignees = assignees;
		this.labels = labels;
		this.milestone = milestone;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Author {

		private String username;
		private String profileUrl;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Assignee {

		private Integer userAccountId;
		private String username;
		private String profileUrl;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LabelInfo {

		private Integer labelId;
		private String labelName;
		private String fontColor;
		private String backgroundColor;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MilestoneInfo {

		private Integer milestoneId;
		private String milestoneName;
		private Integer openIssueCount;
		private Integer closedIssueCount;
	}
}
