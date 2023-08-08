package kr.codesquad.issuetracker.presentation.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MilestoneRegisterRequest {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@NotEmpty(message = "마일스톤의 이름은 빈 값이 들어올 수 없습니다.")
	@Size(max = 45, message = "마일스톤의 이름은 45자를 넘을 수 없습니다.")
	private String milestoneName;
	private String description;
	private String dueDate;

	public String getMilestoneName() {
		return milestoneName;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getDueDate() {
		if (dueDate != null) {
			return LocalDateTime.parse(dueDate, formatter);
		}
		return null;
	}
}
