package kr.codesquad.issuetracker.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.issuetracker.application.IssueService;
import kr.codesquad.issuetracker.presentation.response.IssueSimpleResponse;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/issues")
@RequiredArgsConstructor
@RestController
public class IssueController {
	private final IssueService issueService;
	@GetMapping
	public ResponseEntity<List<IssueSimpleResponse>> findAll() {
		return ResponseEntity.ok(issueService.findAll());
	}
}
