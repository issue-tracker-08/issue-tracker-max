package kr.codesquad.issuetracker.presentation.request;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentRequest {

	@NotEmpty(message = "댓글의 내용은 비워둘 수 없습니다.")
	private String content;
}
