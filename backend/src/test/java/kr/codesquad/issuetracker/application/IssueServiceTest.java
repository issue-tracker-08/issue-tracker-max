package kr.codesquad.issuetracker.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.codesquad.issuetracker.ApplicationTest;
import kr.codesquad.issuetracker.acceptance.DatabaseInitializer;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.fixture.FixtureFactory;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;
import kr.codesquad.issuetracker.presentation.request.AssigneeRequest;
import kr.codesquad.issuetracker.presentation.request.IssueRegisterRequest;
import kr.codesquad.issuetracker.presentation.response.IssueDetailResponse;

@ApplicationTest
class IssueServiceTest {

	@Autowired
	private DatabaseInitializer databaseInitializer;

	@Autowired
	private IssueService issueService;

	@BeforeEach
	void setUp() {
		databaseInitializer.initTables();
	}

	@DisplayName("이슈 등록에 성공한다.")
	@Test
	void registerIssueTest() {
		// given
		IssueRegisterRequest request = FixtureFactory
			.createIssueRegisterRequest("프로젝트 세팅하기", List.of(1, 2), List.of(1, 2));

		// when
		issueService.register(1, request);

		// then
		List<IssueSimpleMapper> result = issueService.findAll();
		assertAll(
			() -> assertThat(result.get(0).getIssueNumber()).isNotNull(),
			() -> assertThat(result.get(0).isOpen()).isTrue(),
			() -> assertThat(result.get(0).getTitle()).isEqualTo("프로젝트 세팅하기")
		);
	}

	@DisplayName("전체 이슈 목록을 조회시")
	@Nested
	class IssueListTest {
		@DisplayName("최근에 생성된 순서대로 조회가 된다.")
		@Test
		public void sortedIssueNumbers() {
			var issueNumbers = issueService.findAll().stream()
				.mapToInt(IssueSimpleMapper::getIssueNumber)
				.toArray();

			assertThat(issueNumbers).isSortedAccordingTo(Comparator.reverseOrder());
		}

		@DisplayName("이슈별 담당자, 레이블의 중복이 제거가 된다.")
		@Test
		public void matchIssueLabelAndAssigneeCount() {
			var issueSimpleMappers = issueService.findAll();
			var firstIssue = issueSimpleMappers.get(0);
			var lastIssue = issueSimpleMappers.get(2);

			assertAll(
				() -> assertThat(issueSimpleMappers.size()).isEqualTo(3),
				() -> assertThat(firstIssue.getLabels().size()).isEqualTo(0),
				() -> assertThat(firstIssue.getAssignees().size()).isEqualTo(2),
				() -> assertThat(lastIssue.getLabels().size()).isEqualTo(3),
				() -> assertThat(lastIssue.getAssignees().size()).isEqualTo(3)
			);
		}
	}

	@DisplayName("이슈 상세페이지를 조회할 때")
	@Nested
	class IssueDetailTest {

		@DisplayName("이슈 상세페이지 조회에 성공한다.")
		@Test
		void getIssueDetail() {
			// given

			// when
			IssueDetailResponse result = issueService.getIssueDetails(1);

			// then
			assertAll(
				() -> assertThat(result.getIssueId()).isNotNull(),
				() -> assertThat(result.getAuthor().getUsername()).isNotNull(),
				() -> assertThat(result.getAuthor().getProfileUrl()).isNotNull(),
				() -> assertThat(result.getAssignees()).isNotNull(),
				() -> assertThat(result.getLabels()).isNotNull()
			);
		}

		@DisplayName("존재하지 않는 이슈 아이디가 주어져 예외를 던진다.")
		@Test
		void givenNotExistsIssueId_thenThrowsException() {
			// given

			// when & then
			assertThatThrownBy(() -> issueService.getIssueDetails(0))
				.isInstanceOf(ApplicationException.class)
				.extracting("errorCode").isEqualTo(ErrorCode.ISSUE_NOT_FOUND);
		}
	}

	@DisplayName("존재하지 않는 이슈의 담당자를 수정하면 ISSUE_NOT_FOUND 예외가 발생한다.")
	@Test
	public void failedToUpdateAssignee_IfNoExistsIssue() {
		var assigneeRequest = new AssigneeRequest();
		assigneeRequest.setIssueId(-1);

		assertThatThrownBy(() -> issueService.updateAssignees(assigneeRequest))
			.isInstanceOf(ApplicationException.class)
			.extracting("errorCode").isEqualTo(ErrorCode.ISSUE_NOT_FOUND);
	}
}
