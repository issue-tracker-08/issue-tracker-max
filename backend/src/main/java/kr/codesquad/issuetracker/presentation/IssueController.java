package kr.codesquad.issuetracker.presentation;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.issuetracker.application.IssueService;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;
import kr.codesquad.issuetracker.presentation.auth.AuthPrincipal;
import kr.codesquad.issuetracker.presentation.request.AssigneeRequest;
import kr.codesquad.issuetracker.presentation.request.IssueLabelRequest;
import kr.codesquad.issuetracker.presentation.request.IssueMilestoneRequest;
import kr.codesquad.issuetracker.presentation.request.IssueModifyRequest;
import kr.codesquad.issuetracker.presentation.request.IssueRegisterRequest;
import kr.codesquad.issuetracker.presentation.response.IssueDetailResponse;
import kr.codesquad.issuetracker.presentation.response.IssueDetailSidebarResponse;
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
	public ResponseEntity<Map<String, Integer>> register(@AuthPrincipal Integer userId,
		@Valid @RequestBody IssueRegisterRequest request) {
		return ResponseEntity.ok(Map.of("issueId", issueService.register(userId, request)));
	}

	@GetMapping("/{issueId}")
	public ResponseEntity<IssueDetailResponse> getIssueDetails(@PathVariable Integer issueId) {
		return ResponseEntity.ok()
			.body(issueService.getIssueDetails(issueId));
	}

	@GetMapping("/{issueId}/sidebar")
	public ResponseEntity<IssueDetailSidebarResponse> getIssueDetailsSidebar(@PathVariable Integer issueId) {
		return ResponseEntity.ok()
			.body(issueService.getIssueDetailsSidebar(issueId));
	}

	@PutMapping("/{issueId}/title")
	public ResponseEntity<Void> modifyIssueTitle(@AuthPrincipal Integer userId,
		@PathVariable Integer issueId, @RequestBody IssueModifyRequest.IssueTitleModifyRequest request) {
		issueService.modifyIssueTitle(userId, issueId, request.getTitle());
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{issueId}/content")
	public ResponseEntity<Void> modifyIssueContent(@AuthPrincipal Integer userId,
		@PathVariable Integer issueId, @RequestBody IssueModifyRequest.IssueContentModifyRequest request) {
		issueService.modifyIssueContent(userId, issueId, request.getContent());
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{issueId}/isOpen")
	public ResponseEntity<Void> modifyIssueContent(@AuthPrincipal Integer userId,
		@PathVariable Integer issueId, @RequestBody IssueModifyRequest.IssueIsOpenModifyRequest request) {
		issueService.modifyIssueOpenStatus(userId, issueId, request.getIsOpen());
		return ResponseEntity.ok().build();
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
