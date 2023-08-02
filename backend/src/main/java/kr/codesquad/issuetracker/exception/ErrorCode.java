package kr.codesquad.issuetracker.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	DUPLICATED_LOGIN_ID(400, "중복된 로그인 아이디가 존재합니다."),
	USER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
	NOT_FOUND(404, "해당 리소스를 찾을 수 없습니다."),
	INVALID_INPUT(400, "입력 형식이 잘못되었습니다."),
	FAILED_LOGIN(400, "비밀번호가 일치하지 않습니다."),
	FAILED_ENCRYPTION(500, "해싱 인코딩에 실패했습니다."),

	// JWT
	EMPTY_JWT(401, "헤더에 토큰값이 존재하지 않거나 유효하지 않습니다."),
	INVALID_JWT(401, "유효한 토큰이 아닙니다."),
	EXPIRED_JWT(401, "만료된 토큰입니다"),

	// ISSUE
	ISSUE_NOT_FOUND(404, "존재하지 않는 이슈입니다.");

	private final int statusCode;
	private final String message;

	ErrorCode(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
}
