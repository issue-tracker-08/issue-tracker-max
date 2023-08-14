package kr.codesquad.issuetracker.exception;

import lombok.Getter;

@Getter
public class InitialLoginException extends RuntimeException {

	private final String email;

	public InitialLoginException(String message, String email) {
		super(message);
		this.email = email;
	}
}
