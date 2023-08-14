package kr.codesquad.issuetracker.utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import kr.codesquad.issuetracker.domain.IssueSearch;
import kr.codesquad.issuetracker.domain.IssueSearchFilter;

class IssueSearchParserTest {

	@DisplayName("이슈 목록 검색 조건에 따라 정상적으로 파싱이 된다.")
	@MethodSource("getSearchData")
	@ParameterizedTest(name = "{index}. 검색 조건 = \"{0}\"")
	void test(String searchBar, IssueSearch expectIssueSearch) {
		//when
		IssueSearch issueSearch = IssueSearchParser.parse("bean", searchBar);

		//then
		assertAll(
			() -> assertThat(issueSearch.getIsOpen()).isEqualTo(expectIssueSearch.getIsOpen()),
			() -> assertThat(issueSearch.getAuthor()).isEqualTo(expectIssueSearch.getAuthor()),
			() -> assertThat(issueSearch.getCommenter()).isEqualTo(expectIssueSearch.getCommenter()),
			() -> assertThat(issueSearch.getAssigneeNames()).isEqualTo(expectIssueSearch.getAssigneeNames()),
			() -> assertThat(issueSearch.getMilestoneName()).isEqualTo(expectIssueSearch.getMilestoneName()),
			() -> assertThat(issueSearch.getLabelNames()).isEqualTo(expectIssueSearch.getLabelNames()),
			() -> assertThat(issueSearch.getHasAssignee()).isEqualTo(expectIssueSearch.getHasAssignee()),
			() -> assertThat(issueSearch.getHasMilestone()).isEqualTo(expectIssueSearch.getHasMilestone()),
			() -> assertThat(issueSearch.getHasLabel()).isEqualTo(expectIssueSearch.getHasLabel())
		);
	}

	static Stream<Arguments> getSearchData() {
		return Stream.of(
			Arguments.of(
				"is:open author:@me assignee:tommy assignee:pie",
				IssueSearch.builder()
					.isOpen(true)
					.author("bean")
					.assigneeNames(List.of("tommy", "pie"))
					.build()
			),
			Arguments.of(
				"is:close commenter:tommy milestone:\"milestone name\" label:label1 label:label2",
				IssueSearch.builder()
					.isOpen(false)
					.commenter("tommy")
					.milestoneName("milestone name")
					.labelNames(List.of("label1", "label2"))
					.build()
			),
			Arguments.of(
				"is:open author:bruni label:\"label one\" label:\"label two\" label:label3 milestone:milestone1",
				IssueSearch.builder()
					.isOpen(true)
					.author("bruni")
					.milestoneName("milestone1")
					.labelNames(List.of("label one", "label two", "label3"))
					.build()
			)
		);
	}
}
