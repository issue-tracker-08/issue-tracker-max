package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesquad.issuetracker.domain.IssueLabel;

@Repository
public class IssueLabelRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	public IssueLabelRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("issue_label")
			.usingGeneratedKeyColumns("id")
			.usingColumns("issue_id", "label_id");
	}

	public void saveAll(List<IssueLabel> issueLabels) {
		jdbcInsert.executeBatch(SqlParameterSourceUtils.createBatch(issueLabels));
	}

	public void deleteAll(List<IssueLabel> issueLabels) {
		String sql = "DELETE FROM issue_label WHERE issue_id = :issueId AND label_id = :labelId";

		jdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(issueLabels));
	}
}
