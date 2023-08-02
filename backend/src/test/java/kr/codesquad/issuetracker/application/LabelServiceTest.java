package kr.codesquad.issuetracker.application;

import static org.assertj.core.api.Assertions.*;

import java.util.Comparator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.codesquad.issuetracker.ApplicationTest;
import kr.codesquad.issuetracker.presentation.response.LabelResponse;

@ApplicationTest
class LabelServiceTest {

	@Autowired
	private LabelService labelService;

	@DisplayName("label 목록 조회시 이름순으로 정렬이 된다.")
	@Test
	void findLabelsSortedNameAscending() {
		assertThat(labelService.findAll())
			.isSortedAccordingTo(Comparator.comparing(LabelResponse::getName));
	}
}