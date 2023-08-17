package kr.codesquad.issuetracker.exception;

import java.util.Map;

import org.springframework.http.HttpHeaders;
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

	@ExceptionHandler(InitialLoginException.class)
	public ResponseEntity<Map<String, String>> handleInitialLoginException(InitialLoginException e) {
		return ResponseEntity.status(HttpStatus.FOUND)
			.header(HttpHeaders.LOCATION, "http://localhost:5173")
			.body(Map.of("email", e.getEmail()));
	}
}
