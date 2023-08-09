package kr.codesquad.issuetracker.presentation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.issuetracker.application.LabelService;
import kr.codesquad.issuetracker.presentation.request.LabelRequest;
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

	@PostMapping
	public ResponseEntity<Void> register(@Valid @RequestBody LabelRequest request) {
		labelService.register(request.getName(), request.getDescription(), request.getFontColor(),
			request.getBackgroundColor());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
