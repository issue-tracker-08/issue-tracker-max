package kr.codesquad.issuetracker.presentation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.issuetracker.application.MilestoneService;
import kr.codesquad.issuetracker.presentation.converter.OpenState;
import kr.codesquad.issuetracker.presentation.request.MilestoneCommonRequest;
import kr.codesquad.issuetracker.presentation.response.MilestoneResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/milestones")
@RestController
public class MilestoneController {

	private final MilestoneService milestoneService;

	@GetMapping
	public ResponseEntity<List<MilestoneResponse>> findAll(
		@RequestParam(value = "state", required = false, defaultValue = "open") OpenState state) {
		return ResponseEntity.ok(milestoneService.findAll(state.isOpen()));
	}

	@PostMapping
	public ResponseEntity<Void> register(@Valid @RequestBody MilestoneCommonRequest request) {
		milestoneService.register(request.getMilestoneName(), request.getDescription(), request.getDueDate());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{milestoneId}")
	public ResponseEntity<Void> modify(@PathVariable Integer milestoneId,
		@Valid @RequestBody MilestoneCommonRequest request) {
		milestoneService.modify(milestoneId, request.getMilestoneName(), request.getDescription(),
			request.getDueDate());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{milestoneId}")
	public ResponseEntity<Void> remove(@PathVariable Integer milestoneId) {
		milestoneService.remove(milestoneId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PutMapping(path = "/{milestoneId}", params = "state")
	public ResponseEntity<Void> changeOpenState(@PathVariable Integer milestoneId,
		@RequestParam("state") OpenState state) {
		milestoneService.changeOpenState(milestoneId, state.isOpen());
		return ResponseEntity.ok().build();
	}
}
