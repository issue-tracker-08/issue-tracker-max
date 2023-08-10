package kr.codesquad.issuetracker.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import kr.codesquad.issuetracker.ApplicationTest;
import kr.codesquad.issuetracker.presentation.response.LabelResponse;

@ApplicationTest
class LabelServiceTest {

	@Autowired
	private LabelService labelService;

	@MockBean
	private S3Service s3Service;

	@DisplayName("label 목록 조회시 이름순으로 정렬이 된다.")
	@Test
	void findLabelsSortedNameAscending() {
		// given
		labelService.register("feat", "기능 개발", "#FFF", "#EDE");
		labelService.register("setting", "기능 개발", "#FFF", "#EDE");
		labelService.register("backend", "기능 개발", "#FFF", "#EDE");

		// when & then
		assertThat(labelService.findAll())
			.isSortedAccordingTo(Comparator.comparing(LabelResponse::getName));
	}

	@DisplayName("label 수정에 성공한다.")
	@Test
	void modify() {
		labelService.register("before", null, "white", "1234");
		labelService.modify(1, "after", null, "black", "1111");
		List<LabelResponse> result = labelService.findAll();

		assertAll(
			() -> assertThat(result.get(0).getName()).isEqualTo("after"),
			() -> assertThat(result.get(0).getFontColor()).isEqualTo("black"),
			() -> assertThat(result.get(0).getBackgroundColor()).isEqualTo("1111"));

	}
}
