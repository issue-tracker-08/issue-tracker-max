package kr.codesquad.issuetracker.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.domain.Comment;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.persistence.CommentRepository;
import kr.codesquad.issuetracker.presentation.response.CommentsResponse;
import kr.codesquad.issuetracker.presentation.response.Slice;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;

	@Transactional
	public void register(Integer userId, String content, Integer issueId) {
		Comment comment = new Comment(content, userId, issueId);
		commentRepository.save(comment);
	}

	@Transactional
	public void modify(String modifiedComment, Integer commentId, Integer userId) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));
		if (!comment.getUserAccountId().equals(userId)) {
			throw new ApplicationException(ErrorCode.NO_AUTHORIZATION);
		}
		comment.modifyContent(modifiedComment);
		commentRepository.update(comment);
	}

	@Transactional(readOnly = true)
	public Slice<CommentsResponse> getComments(Integer issueId, Integer cursor) {
		List<CommentsResponse> comments = commentRepository.findAll(issueId, cursor);
		if (comments.isEmpty()) {
			return new Slice<>(List.of(), false, 0);
		}
		cursor = comments.get(comments.size() - 1).getId();
		if (comments.size() == 11) {
			return new Slice<>(comments.subList(0, 10), true, cursor);
		}
		return new Slice<>(comments, false, cursor);
	}
}
