package kr.codesquad.issuetracker.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private int statusCode;
	private String message;

	public ErrorResponse(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
}
