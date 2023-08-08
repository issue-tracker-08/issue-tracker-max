package kr.codesquad.issuetracker.presentation;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.issuetracker.application.AuthService;
import kr.codesquad.issuetracker.presentation.request.LoginRequest;
import kr.codesquad.issuetracker.presentation.request.SignupRequest;
import kr.codesquad.issuetracker.presentation.response.LoginSuccessResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest request) {
		authService.signup(request.getLoginId(), request.getPassword());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/login")
	public ResponseEntity<LoginSuccessResponse> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(authService.login(request.getLoginId(), request.getPassword()));
	}
}
