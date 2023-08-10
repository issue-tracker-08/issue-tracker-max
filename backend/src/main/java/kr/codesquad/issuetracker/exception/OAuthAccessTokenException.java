package kr.codesquad.issuetracker.exception;

import lombok.Getter;

@Getter
public class OAuthAccessTokenException extends RuntimeException {

	private final ErrorCode errorCode;

	public OAuthAccessTokenException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}
