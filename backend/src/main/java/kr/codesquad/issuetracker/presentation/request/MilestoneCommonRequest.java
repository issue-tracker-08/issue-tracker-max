package kr.codesquad.issuetracker.presentation.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneCommonRequest {

	@NotBlank(message = "마일스톤의 이름은 빈 값이 들어올 수 없습니다.")
	@Size(max = 45, message = "마일스톤의 이름은 45자를 넘을 수 없습니다.")
	private String milestoneName;
	private String description;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate dueDate;
}
