package kr.codesquad.issuetracker.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import kr.codesquad.issuetracker.ApplicationTest;
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
		LocalDateTime dueDate = LocalDateTime.of(2023, 9, 1, 0, 0, 0);

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
}
