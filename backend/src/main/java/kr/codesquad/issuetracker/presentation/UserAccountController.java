package kr.codesquad.issuetracker.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.issuetracker.application.UserAccountService;
import kr.codesquad.issuetracker.presentation.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserAccountController {

	private final UserAccountService userAccountService;

	@GetMapping
	public ResponseEntity<List<UserProfileResponse>> findAll() {
		return ResponseEntity.ok(userAccountService.findAll());
	}
}
