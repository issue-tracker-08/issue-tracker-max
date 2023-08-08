package kr.codesquad.issuetracker.presentation.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LabelRequest {

	@NotEmpty(message = "레이블 이름을 비워둘 수 없습니다.")
	@Size(max = 45, message = "레이블의 이름은 45자를 넘길 수 없습니다.")
	private String name;
	private String description;
	@NotEmpty(message = "폰트컬러는 비워둘 수 없습니다.")
	private String fontColor;
	@NotEmpty(message = "배경색상은 비워둘 수 없습니다.")
	private String backgroundColor;

}
