package kr.codesquad.issuetracker.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.issuetracker.application.LabelService;
import kr.codesquad.issuetracker.presentation.response.LabelResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/labels")
@RestController
public class LabelController {

	private final LabelService labelService;

	@GetMapping
	public ResponseEntity<List<LabelResponse>> findAll() {
		return ResponseEntity.ok(labelService.findAll());
	}
}
