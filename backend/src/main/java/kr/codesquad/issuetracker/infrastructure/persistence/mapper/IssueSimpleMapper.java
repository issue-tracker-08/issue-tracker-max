package kr.codesquad.issuetracker.infrastructure.persistence.mapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class IssueSimpleMapper {
	private Integer issueNumber;
	private boolean isOpen;
	private List<LabelSimpleEntity> labelSimpleEntities;
	private String title;
	private String milestone;
	private List<AssigneeSimpleEntity> assigneeSimpleEntities;
	private LocalDateTime createdAt;

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	public static class LabelSimpleEntity {
		private String name;
		private String fontColor;
		private String backgroundColor;

		public boolean hasValue() {
			return StringUtils.hasText(name);
		}
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	public static class AssigneeSimpleEntity {
		private String loginId;
		private String profileUrl;

		public boolean hasValue() {
			return StringUtils.hasText(loginId);
		}
	}

	public static IssueSimpleMapper of(Integer issueNumber, boolean isOpen, String labels, String title,
		String milestone, String assignees, LocalDateTime createdAt) {
		final ObjectMapper objectMapper = new ObjectMapper();
		try {
			var labelEntities = Arrays.stream(objectMapper.readValue(labels, LabelSimpleEntity[].class))
				.filter(LabelSimpleEntity::hasValue)
				.sorted(Comparator.comparing(LabelSimpleEntity::getName))
				.collect(Collectors.toList());
			var assigneeEntities = Arrays.stream(objectMapper.readValue(assignees, AssigneeSimpleEntity[].class))
				.filter(AssigneeSimpleEntity::hasValue)
				.collect(Collectors.toList());

			return new IssueSimpleMapper(issueNumber, isOpen, labelEntities, title,
				milestone, assigneeEntities, createdAt);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
