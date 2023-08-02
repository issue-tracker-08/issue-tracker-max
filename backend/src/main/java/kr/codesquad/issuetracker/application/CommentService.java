package kr.codesquad.issuetracker.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.domain.Comment;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.persistence.CommentRepository;
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
	public void modify(String modifiedComment, Integer commentId) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));
		comment.modifyContent(modifiedComment);
		commentRepository.update(comment);
	}
}
