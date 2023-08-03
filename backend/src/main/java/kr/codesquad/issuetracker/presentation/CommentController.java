package kr.codesquad.issuetracker.presentation;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.codesquad.issuetracker.application.CommentService;
import kr.codesquad.issuetracker.presentation.auth.AuthPrincipal;
import kr.codesquad.issuetracker.presentation.request.CommentRequest;
import kr.codesquad.issuetracker.presentation.response.CommentsResponse;
import kr.codesquad.issuetracker.presentation.response.Slice;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/issues/{issueId}/comments")
@RequiredArgsConstructor
@RestController
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<Void> register(@AuthPrincipal Integer userId,
		@Valid @RequestBody CommentRequest request, @PathVariable Integer issueId) {
		commentService.register(userId, request.getContent(), issueId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<Void> modify(@RequestBody CommentRequest request, @PathVariable Integer commentId,
		@AuthPrincipal Integer userId) {
		commentService.modify(request.getContent(), commentId, userId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping
	public ResponseEntity<Slice<CommentsResponse>> commentList(@PathVariable Integer issueId,
		@RequestParam Integer cursor) {
		return ResponseEntity.status(HttpStatus.OK).body(commentService.getComments(issueId, cursor));
	}
}
