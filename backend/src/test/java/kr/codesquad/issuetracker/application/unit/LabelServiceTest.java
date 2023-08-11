package kr.codesquad.issuetracker.application.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.codesquad.issuetracker.application.LabelService;
import kr.codesquad.issuetracker.domain.Label;
import kr.codesquad.issuetracker.infrastructure.persistence.LabelRepository;

@ExtendWith(MockitoExtension.class)
class LabelServiceTest {

	@Mock
	private LabelRepository labelRepository;

	@InjectMocks
	private LabelService labelService;

	@DisplayName("레이블 생성에 성공한다.")
	@Test
	void register() {
		// given
		willDoNothing().given(labelRepository).save(any(Label.class));

		// when & then
		assertThatCode(() -> labelService.register("feat", null, "#1234", "black"))
			.doesNotThrowAnyException();
	}

	@DisplayName("레이블 수정에 성공한다.")
	@Test
	void modify() {
		// given
		willDoNothing().given(labelRepository).update(any(Label.class));

		// when & then
		assertThatCode(() -> labelService.modify(1, "after", null, "1111", "black"))
			.doesNotThrowAnyException();
	}

	@DisplayName("레이블 삭제에 성공한다.")
	@Test
	void remove() {
		// given
		willDoNothing().given(labelRepository).deleteById(anyInt());

		// when & then
		assertThatCode(() -> labelService.remove(1)).doesNotThrowAnyException();
	}
}
