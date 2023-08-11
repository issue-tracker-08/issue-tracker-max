package kr.codesquad.issuetracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidException(MethodArgumentNotValidException e) {
		String exMessage = e.getBindingResult().getFieldError().getDefaultMessage();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(ErrorCode.INVALID_INPUT, exMessage));
	}

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e) {
		ErrorCode errorCode = e.getErrorCode();
		return ResponseEntity.status(errorCode.getStatusCode())
			.body(new ErrorResponse(errorCode));
	}

	@ExceptionHandler(OAuthAccessTokenException.class)
	public ResponseEntity<ErrorResponse> handleApplicationException(OAuthAccessTokenException e) {
		ErrorCode errorCode = e.getErrorCode();
		return ResponseEntity.status(errorCode.getStatusCode())
			.body(new ErrorResponse(errorCode, e.getMessage()));
	}
}
