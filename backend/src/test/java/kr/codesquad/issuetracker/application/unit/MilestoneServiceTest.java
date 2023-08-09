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

import kr.codesquad.issuetracker.application.MilestoneService;
import kr.codesquad.issuetracker.domain.Milestone;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
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

	@DisplayName("마일스톤을 수정할 때 ")
	@Nested
	class ModifyTest {

		@DisplayName("마일스톤 수정에 성공한다.")
		@Test
		void modify() {
			// given
			given(milestoneRepository.findById(anyInt())).willReturn(Optional.of(new Milestone("마일스톤", null, null)));
			willDoNothing().given(milestoneRepository).update(any(Milestone.class));

			// when & then
			assertThatCode(() -> milestoneService.modify(1, "BE #1주차 스프린트", null, null))
				.doesNotThrowAnyException();
		}

		@DisplayName("존재하지 않는 마일스톤 ID가 주어지면 예외를 던진다.")
		@Test
		void givenNotExistsMilestoneId_thenThrowsException() {
			// given
			given(milestoneRepository.findById(anyInt())).willReturn(Optional.empty());

			// when & then
			assertThatThrownBy(() -> milestoneService.modify(0, "BE #1주차 스프린트", null, null))
				.isInstanceOf(ApplicationException.class)
				.extracting("errorCode").isEqualTo(ErrorCode.MILESTONE_NOT_FOUND);
		}
	}

	@DisplayName("마일스톤 삭제에 성공한다.")
	@Test
	void remove() {
		// given
		willDoNothing().given(milestoneRepository).deleteById(anyInt());

		// when & then
		assertThatCode(() -> milestoneService.remove(1))
			.doesNotThrowAnyException();
	}
}
