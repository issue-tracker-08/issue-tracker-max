package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesquad.issuetracker.domain.IssueAssignee;

@Repository
public class IssueAssigneeRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	public IssueAssigneeRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("issue_assignee")
			.usingGeneratedKeyColumns("id")
			.usingColumns("issue_id", "user_account_id");
	}

	public void saveAll(List<IssueAssignee> assignees) {
		jdbcInsert.executeBatch(SqlParameterSourceUtils.createBatch(assignees));
	}

	public void deleteAll(List<IssueAssignee> assignees) {
		String sql = "DELETE FROM issue_assignee WHERE issue_id = :issueId AND user_account_id = :userAccountId";

		jdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(assignees));
	}
}
