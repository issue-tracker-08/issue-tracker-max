package kr.codesquad.issuetracker.presentation.converter;

import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum OpenState {

	OPEN(Boolean.TRUE), CLOSED(Boolean.FALSE);

	private final boolean isOpen;

	OpenState(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public static OpenState of(String openState) {
		if (openState.equals("open")) {
			return OPEN;
		}
		if (openState.equals("closed")) {
			return CLOSED;
		}
		throw new ApplicationException(ErrorCode.INVALID_INPUT);
	}
}
