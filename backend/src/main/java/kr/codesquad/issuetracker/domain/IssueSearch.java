package kr.codesquad.issuetracker.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IssueSearch {

	private Boolean isOpen;
	private String author;
	private String commenter;
	private String milestoneName;
	private Boolean hasAssignee;
	private Boolean hasMilestone;
	private Boolean hasLabel;

	@Builder.Default()
	private List<String> assigneeNames = new ArrayList<>();
	@Builder.Default()
	private List<String> labelNames = new ArrayList<>();

	public void registerIssueStatus(String issueStatus) {
		if ("open".equals(issueStatus)) {
			isOpen = true;
		}
		if ("close".equals(issueStatus)) {
			isOpen = false;
		}
	}

	public void registerAuthor(String author) {
		this.author = author;
	}

	public void registerCommenter(String commenter) {
		this.commenter = commenter;
	}

	public void addAssignee(String assigneeName) {
		assigneeNames.add(assigneeName);
	}

	public void registerMilestone(String milestoneName) {
		this.milestoneName = milestoneName;
	}

	public void addLabel(String labelName) {
		labelNames.add(labelName);
	}

	public void registerNone(String name) {
		if ("assignee".equals(name)) {
			hasAssignee = Boolean.FALSE;
			return;
		}
		if ("label".equals(name)) {
			hasLabel = Boolean.FALSE;
			return;
		}
		if ("milestone".equals(name)) {
			hasMilestone = Boolean.FALSE;
		}
	}
}
