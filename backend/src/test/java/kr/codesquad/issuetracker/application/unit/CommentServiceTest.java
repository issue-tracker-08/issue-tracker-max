package kr.codesquad.issuetracker.application.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.codesquad.issuetracker.application.CommentService;
import kr.codesquad.issuetracker.domain.Comment;
import kr.codesquad.issuetracker.infrastructure.persistence.CommentRepository;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

	@Mock
	private CommentRepository commentRepository;

	@InjectMocks
	private CommentService commentService;

	@DisplayName("댓글을 작성에 성공한다.")
	@Test
	void register() {
		//given
		willDoNothing().given(commentRepository).save(any(Comment.class));

		//when & then
		assertThatCode(() -> commentService.register(1, "안녕하세요", 2))
			.doesNotThrowAnyException();
	}
}
