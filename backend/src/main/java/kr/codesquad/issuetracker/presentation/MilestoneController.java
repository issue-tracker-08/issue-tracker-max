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

import kr.codesquad.issuetracker.application.MilestoneService;
import kr.codesquad.issuetracker.presentation.request.MilestoneRegisterRequest;
import kr.codesquad.issuetracker.presentation.response.MilestoneResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/milestones")
@RestController
public class MilestoneController {

	private final MilestoneService milestoneService;

	@GetMapping
	public ResponseEntity<List<MilestoneResponse>> findAll() {
		return ResponseEntity.ok(milestoneService.findAll());
	}

	@PostMapping
	public ResponseEntity<Void> register(@Valid @RequestBody MilestoneRegisterRequest request) {
		milestoneService.register(request.getMilestoneName(), request.getDescription(), request.getDueDate());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
