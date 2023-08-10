package kr.codesquad.issuetracker.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import kr.codesquad.issuetracker.ApplicationTest;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.presentation.response.MilestoneResponse;

@ApplicationTest
class MilestoneServiceTest {

	@Autowired
	private MilestoneService milestoneService;

	@MockBean
	private S3Service s3Service;

	@DisplayName("마일스톤 등록에 성공한다.")
	@Test
	void register() {
		// given
		LocalDate dueDate = LocalDate.of(2023, 9, 1);

		// when
		milestoneService.register("BE 1주차 스프린트", "열심히 하자", dueDate);

		// then
		List<MilestoneResponse> milestones = milestoneService.findAll();
		assertAll(
			() -> assertThat(milestones).hasSize(1),
			() -> assertThat(milestones.get(0).getMilestoneId()).isEqualTo(1),
			() -> assertThat(milestones.get(0).getMilestoneName()).isEqualTo("BE 1주차 스프린트"),
			() -> assertThat(milestones.get(0).getDescription()).isEqualTo("열심히 하자"),
			() -> assertThat(milestones.get(0).getDueDate()).isEqualTo(dueDate)
		);
	}

	@DisplayName("마일스톤을 수정할 때")
	@Nested
	class ModifyTest {

		@DisplayName("마일스톤 수정에 성공한다.")
		@Test
		void modify() {
			// given
			LocalDate dueDate = LocalDate.of(2023, 9, 1);
			milestoneService.register("BE 1주차 스프린트", null, null);

			// when
			milestoneService.modify(1, "BE 2주차 스프린트", "2주차", dueDate);

			// then
			List<MilestoneResponse> result = milestoneService.findAll();

			assertAll(
				() -> assertThat(result.get(0).getMilestoneName()).isEqualTo("BE 2주차 스프린트"),
				() -> assertThat(result.get(0).getDescription()).isEqualTo("2주차"),
				() -> assertThat(result.get(0).getDueDate()).isEqualTo(dueDate)
			);
		}

		@DisplayName("존재하지 않는 마일스톤 ID가 주어지면 예외를 던진다.")
		@Test
		void givenNotExistsMilestoneId_thenThrowsException() {
			// given

			// when & then
			assertThatThrownBy(() -> milestoneService.modify(1, "BE 2주차 스프린트", "2주차", null))
				.isInstanceOf(ApplicationException.class)
				.extracting("errorCode").isEqualTo(ErrorCode.MILESTONE_NOT_FOUND);
		}
	}

	@DisplayName("마일스톤 삭제에 성공한다.")
	@Test
	void remove() {
		// given
		milestoneService.register("BE Sprint #1", null, null);

		// when
		milestoneService.remove(1);

		// then
		assertThat(milestoneService.findAll()).hasSize(0);
	}
}
