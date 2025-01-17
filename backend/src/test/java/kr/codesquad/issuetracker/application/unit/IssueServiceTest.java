package kr.codesquad.issuetracker.application.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.codesquad.issuetracker.application.IssueService;
import kr.codesquad.issuetracker.domain.Issue;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.persistence.IssueAssigneeRepository;
import kr.codesquad.issuetracker.infrastructure.persistence.IssueLabelRepository;
import kr.codesquad.issuetracker.infrastructure.persistence.IssueRepository;

@ExtendWith(MockitoExtension.class)
class IssueServiceTest {

	@Mock
	private IssueRepository issueRepository;
	@Mock
	private IssueLabelRepository issueLabelRepository;
	@Mock
	private IssueAssigneeRepository issueAssigneeRepository;

	@InjectMocks
	private IssueService issueService;

	@DisplayName("이슈를 수정할 때")
	@Nested
	class IssueModifyTest {

		@DisplayName("올바른 수정데이터가 주어지면 변경에 성공한다.")
		@Test
		void issueModifySuccess() {
			// given
			given(issueRepository.findById(anyInt())).willReturn(Optional.of(new Issue("", "", true, 1, null)));
			willDoNothing().given(issueRepository).updateIssue(any(Issue.class));

			// when & then
			assertThatCode(() -> issueService.modifyIssueTitle(1, 1, "변경된 제목"))
				.doesNotThrowAnyException();
		}
	}
}
