package kr.codesquad.issuetracker.presentation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.issuetracker.application.IssueService;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;
import kr.codesquad.issuetracker.presentation.auth.AuthPrincipal;
import kr.codesquad.issuetracker.presentation.request.AssigneeRequest;
import kr.codesquad.issuetracker.presentation.request.IssueLabelRequest;
import kr.codesquad.issuetracker.presentation.request.IssueMilestoneRequest;
import kr.codesquad.issuetracker.presentation.request.IssueRegisterRequest;
import kr.codesquad.issuetracker.presentation.response.IssueDetailResponse;
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

	@PostMapping
	public ResponseEntity<Void> register(@AuthPrincipal Integer userId,
		@Valid @RequestBody IssueRegisterRequest request) {
		Integer issueId = issueService.register(userId, request);
		return ResponseEntity.status(HttpStatus.FOUND)
			.header(HttpHeaders.LOCATION, "/api/issues/" + issueId)
			.build();
	}

	@GetMapping("/{issueId}")
	public ResponseEntity<IssueDetailResponse> getIssueDetails(@PathVariable Integer issueId) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(issueService.getIssueDetails(issueId));
	}

	@PostMapping("/{issueId}/assignees")
	public ResponseEntity<Void> updateAssignees(@PathVariable Integer issueId,
		@RequestBody AssigneeRequest assigneeRequest) {
		issueService.updateAssignees(issueId, assigneeRequest);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/{issueId}/labels")
	public ResponseEntity<Void> updateIssueLabels(@PathVariable Integer issueId,
		@RequestBody IssueLabelRequest issueLabelRequest) {
		issueService.updateIssueLabels(issueId, issueLabelRequest);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/{issueId}/milestone")
	public ResponseEntity<Void> updateIssueMilestone(@PathVariable Integer issueId,
		@RequestBody IssueMilestoneRequest milestoneRequest) {
		issueService.updateIssueMilestone(issueId, milestoneRequest.getMilestoneId());

		return ResponseEntity.ok().build();
	}
}
