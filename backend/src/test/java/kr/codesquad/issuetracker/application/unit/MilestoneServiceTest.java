package kr.codesquad.issuetracker.application.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.codesquad.issuetracker.application.MilestoneService;
import kr.codesquad.issuetracker.domain.Milestone;
import kr.codesquad.issuetracker.infrastructure.persistence.MilestoneRepository;

@ExtendWith(MockitoExtension.class)
class MilestoneServiceTest {

	@Mock
	private MilestoneRepository milestoneRepository;

	@InjectMocks
	private MilestoneService milestoneService;

	@DisplayName("마일스톤 등록에 성공한다.")
	@Test
	void register() {
		// given
		willDoNothing().given(milestoneRepository).save(any(Milestone.class));

		// when & then
		assertThatCode(() -> milestoneService.register("BE 1주차 스프린트", null, null))
			.doesNotThrowAnyException();
	}
}
