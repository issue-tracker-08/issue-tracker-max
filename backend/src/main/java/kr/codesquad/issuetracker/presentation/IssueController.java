package kr.codesquad.issuetracker.presentation;

import java.util.Map;

import javax.validation.Valid;

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

import kr.codesquad.issuetracker.application.IssueService;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;
import kr.codesquad.issuetracker.presentation.auth.AuthPrincipal;
import kr.codesquad.issuetracker.presentation.auth.Principal;
import kr.codesquad.issuetracker.presentation.converter.OpenState;
import kr.codesquad.issuetracker.presentation.request.AssigneeRequest;
import kr.codesquad.issuetracker.presentation.request.IssueLabelRequest;
import kr.codesquad.issuetracker.presentation.request.IssueMilestoneRequest;
import kr.codesquad.issuetracker.presentation.request.IssueModifyRequest;
import kr.codesquad.issuetracker.presentation.request.IssueRegisterRequest;
import kr.codesquad.issuetracker.presentation.request.MultipleIssueModifyRequest;
import kr.codesquad.issuetracker.presentation.response.IssueDetailResponse;
import kr.codesquad.issuetracker.presentation.response.IssueDetailSidebarResponse;
import kr.codesquad.issuetracker.presentation.response.Page;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/issues")
@RequiredArgsConstructor
@RestController
public class IssueController {

	private final IssueService issueService;

	@GetMapping
	public ResponseEntity<Page<IssueSimpleMapper>> findAll(@AuthPrincipal Principal principal,
		@RequestParam(value = "q", required = false) String searchBar, @RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "20") int size) {
		return ResponseEntity.ok(issueService.findAll(principal.getLoginId(), searchBar, page, size));
	}

	@PostMapping
	public ResponseEntity<Map<String, Integer>> register(@AuthPrincipal Principal principal,
		@Valid @RequestBody IssueRegisterRequest request) {
		return ResponseEntity.ok(Map.of("issueId", issueService.register(principal.getUserId(), request)));
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
	public ResponseEntity<Void> modifyIssueTitle(@AuthPrincipal Principal principal,
		@PathVariable Integer issueId, @RequestBody IssueModifyRequest.IssueTitleModifyRequest request) {
		issueService.modifyIssueTitle(principal.getUserId(), issueId, request.getTitle());
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{issueId}/content")
	public ResponseEntity<Void> modifyIssueContent(@AuthPrincipal Principal principal,
		@PathVariable Integer issueId, @RequestBody IssueModifyRequest.IssueContentModifyRequest request) {
		issueService.modifyIssueContent(principal.getUserId(), issueId, request.getContent());
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{issueId}/isOpen")
	public ResponseEntity<Void> modifyIssueContent(@AuthPrincipal Principal principal,
		@PathVariable Integer issueId, @RequestBody IssueModifyRequest.IssueIsOpenModifyRequest request) {
		issueService.modifyIssueOpenStatus(principal.getUserId(), issueId, request.getIsOpen());
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

	@PutMapping
	public ResponseEntity<Void> modifyStateOfMultipleIssue(@RequestBody MultipleIssueModifyRequest request) {
		issueService.modifyMultipleIssueState(OpenState.of(request.getState()), request.getIssueIds());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("{issueId}")
	public ResponseEntity<Void> removeIssue(@PathVariable Integer issueId, @AuthPrincipal Principal principal) {
		issueService.remove(issueId);
		return ResponseEntity.ok().build();
	}
}
