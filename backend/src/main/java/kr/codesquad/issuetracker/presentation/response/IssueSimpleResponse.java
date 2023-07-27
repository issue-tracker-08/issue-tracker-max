package kr.codesquad.issuetracker.presentation.response;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import kr.codesquad.issuetracker.infrastructure.IssueSimpleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
*   Controller <- Dto(필요한 값들) <- Service <- Entity(전체 값들) <- Repository
* */

@AllArgsConstructor
@Getter
public class IssueSimpleResponse {
	private final Integer issueNumber;
	private final Boolean isOpen;
	private final String title;
	private final List<LabelSimpleResponse> labels;
	private final String milestone;
	private final List<AssigneeSimpleResponse> assignee;
	private final LocalDateTime createdAt;

	@AllArgsConstructor
	@Getter
	public static class LabelSimpleResponse {
		private String name;
		private String fontColor;
		private String backgroundColor;

		public static LabelSimpleResponse from(IssueSimpleEntity.LabelSimpleEntity labelSimpleEntity) {
			return new LabelSimpleResponse(
				labelSimpleEntity.getName(),
				labelSimpleEntity.getFontColor(),
				labelSimpleEntity.getBackgroundColor()
			);
		}
	}

	@AllArgsConstructor
	@Getter
	public static class AssigneeSimpleResponse {
		private String loginId;
		private String profileUrl;

		public static AssigneeSimpleResponse from(IssueSimpleEntity.AssigneeSimpleEntity assigneeSimpleEntity) {
			return new AssigneeSimpleResponse(
				assigneeSimpleEntity.getLoginId(),
				assigneeSimpleEntity.getProfileUrl()
			);
		}
	}

	public static IssueSimpleResponse from(IssueSimpleEntity issue) {
		var labelResponses = issue.getLabelSimpleEntities().stream()
			.map(LabelSimpleResponse::from)
			.sorted(Comparator.comparing(LabelSimpleResponse::getName))
			.collect(Collectors.toList());

		var assigneeResponses = issue.getAssigneeSimpleEntities().stream()
			.map(AssigneeSimpleResponse::from)
			.collect(Collectors.toList());

		return new IssueSimpleResponse(issue.getIssueNumber(), issue.isOpen(), issue.getTitle(),
			labelResponses, issue.getMilestone(), assigneeResponses, issue.getCreateAt()
		);
	}
}
