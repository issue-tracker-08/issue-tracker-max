package kr.codesquad.issuetracker.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kr.codesquad.issuetracker.ApplicationTest;
import kr.codesquad.issuetracker.acceptance.DatabaseInitializer;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;

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

	@DisplayName("전체 이슈 목록을 조회할 수 있다.")
	@Test
	public void findAllIssue() {
		//given

		//when
		List<IssueSimpleMapper> issueSimpleMappers = issueService.findAll();
		IssueSimpleMapper firstIssue = issueSimpleMappers.get(0);
		IssueSimpleMapper lastIssue = issueSimpleMappers.get(2);

		//then
		assertAll(
			() -> assertThat(issueSimpleMappers.size()).isEqualTo(3),
			() -> assertThat(firstIssue.getIssueNumber()).isEqualTo(3),
			() -> assertThat(firstIssue.getLabelSimpleEntities().size()).isEqualTo(0),
			() -> assertThat(firstIssue.getAssigneeSimpleEntities().size()).isEqualTo(2),
			() -> assertThat(lastIssue.getIssueNumber()).isEqualTo(1),
			() -> assertThat(lastIssue.getLabelSimpleEntities().size()).isEqualTo(3),
			() -> assertThat(lastIssue.getAssigneeSimpleEntities().size()).isEqualTo(3)
		);
	}
}