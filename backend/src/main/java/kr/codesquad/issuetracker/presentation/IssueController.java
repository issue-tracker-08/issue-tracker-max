package kr.codesquad.issuetracker.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.issuetracker.application.IssueService;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/issues")
@RequiredArgsConstructor
@RestController
public class IssueController {
	private final IssueService issueService;
	@GetMapping
	public ResponseEntity<List<IssueSimpleMapper>> findAll() {
		return ResponseEntity.ok(issueService.findAll());
	}
}
