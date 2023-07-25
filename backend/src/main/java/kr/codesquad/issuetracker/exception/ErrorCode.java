package kr.codesquad.issuetracker.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	DUPLICATED_LOGIN_ID(400, "중복된 로그인 아이디가 존재합니다.");

	private final int statusCode;
	private final String message;

	ErrorCode(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
}
