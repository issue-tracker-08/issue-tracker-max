package kr.codesquad.issuetracker.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.codesquad.issuetracker.ApplicationTest;
import kr.codesquad.issuetracker.domain.Issue;
import kr.codesquad.issuetracker.domain.UserAccount;
import kr.codesquad.issuetracker.infrastructure.persistence.IssueRepository;
import kr.codesquad.issuetracker.infrastructure.persistence.UserAccountRepository;
import kr.codesquad.issuetracker.presentation.response.CommentsResponse;
import kr.codesquad.issuetracker.presentation.response.Slice;

@ApplicationTest
class CommentServiceTest {

	@Autowired
	private CommentService commentService;
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private UserAccountRepository accountRepository;

	@DisplayName("댓글 목록 조회에 성공한다.")
	@Test
	void getCommentsTest() {
		// given
		accountRepository.save(new UserAccount("pieeee", "123123"));
		issueRepository.save(new Issue("제목", "내용", true, 1, null));
		for (int i = 0; i < 15; i++) {
			commentService.register(1, "댓글", 1);
		}

		// when
		Slice<CommentsResponse> comments = commentService.getComments(1, 0);

		// then
		assertAll(
			() -> assertThat(comments.getHasMore()).isTrue(),
			() -> assertThat(comments.getNextCursor()).isEqualTo(10),
			() -> assertThat(comments.getData().size()).isEqualTo(10)
		);
	}

	@DisplayName("해당 이슈에 아무 댓글이 없을 시 빈 리스트를 반환한다.")
	@Test
	void emptyCommentList() {
		// given
		accountRepository.save(new UserAccount("pieeee", "123123"));
		issueRepository.save(new Issue("제목", "내용", true, 1, null));

		// when
		Slice<CommentsResponse> comments = commentService.getComments(1, 0);

		// then
		assertAll(
			() -> assertThat(comments.getData()).isEmpty(),
			() -> assertThat(comments.getHasMore()).isFalse(),
			() -> assertThat(comments.getNextCursor()).isEqualTo(0)
		);
	}
}
