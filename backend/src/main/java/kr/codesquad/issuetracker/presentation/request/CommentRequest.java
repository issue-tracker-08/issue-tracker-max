package kr.codesquad.issuetracker.presentation.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentRequest {

	@NotEmpty(message = "댓글의 본문은 비워둘 수 없습니다.")
	@Size(max = 256, message = "댓글의 본문은 256자를 넘길 수 없습니다.")
	private String content;
}
