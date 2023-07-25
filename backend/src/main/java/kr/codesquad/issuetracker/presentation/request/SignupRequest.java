package kr.codesquad.issuetracker.presentation.request;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignupRequest {

	@Size(min = 6, max = 16, message = "아이디는 6자리 이상, 16자리 이하여야 합니다.")
	private String loginId;
	@Size(min = 6, max = 12, message = "비밀번호는 6자리 이상, 12자리 이하여야 합니다.")
	private String password;
}
