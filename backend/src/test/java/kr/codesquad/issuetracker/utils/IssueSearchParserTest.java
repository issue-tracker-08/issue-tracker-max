package kr.codesquad.issuetracker.utils;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import kr.codesquad.issuetracker.domain.IssueSearch;

class IssueSearchParserTest {

	@DisplayName("이슈 목록 검색 조건에 따라 정상적으로 파싱이 된다.")
	@MethodSource("getSearchData")
	@ParameterizedTest(name = "{index}. 검색 조건 = \"{0}\"")
	void test(String searchBar, IssueSearch expectIssueSearch) {
		//when
		IssueSearch issueSearch = IssueSearchParser.parse("bean", searchBar);

		//then
		assertThat(issueSearch).isEqualTo(expectIssueSearch);
	}

	static Stream<Arguments> getSearchData() {
		return Stream.of(
			Arguments.of(
				"is:open author:@me assignee:tommy assignee:pie",
				new IssueSearch(true, "bean", null, List.of("tommy", "pie"), null, new ArrayList<>())
			),
			Arguments.of(
				"is:close commenter:tommy milestone:\"milestone name\" label:label1 label:label2",
				new IssueSearch(false, null, "tommy", new ArrayList<>(), "milestone name",
					List.of("label1", "label2"))
			),
			Arguments.of(
				"is:open author:bruni label:\"label one\" label:\"label two\" label:label3 milestone:milestone1",
				new IssueSearch(true, "bruni", null, new ArrayList<>(), "milestone1",
					List.of("label one", "label two", "label3"))
			)
		);
	}
}
