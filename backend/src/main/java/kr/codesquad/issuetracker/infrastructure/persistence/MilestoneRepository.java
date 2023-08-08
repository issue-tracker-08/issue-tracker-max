package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesquad.issuetracker.domain.Milestone;
import kr.codesquad.issuetracker.presentation.response.MilestoneResponse;

@Repository
public class MilestoneRepository {

	private final SimpleJdbcInsert jdbcInsert;
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MilestoneRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("milestone")
			.usingColumns("name", "description", "due_date")
			.usingGeneratedKeyColumns("id");
	}

	public List<MilestoneResponse> findAll() {
		String sql = "SELECT milestone.id, milestone.name, milestone.description, milestone.due_date, "
			+ "IFNULL(SUM(issue.is_open = TRUE), 0) as open_count, "
			+ "IFNULL(SUM(issue.is_open = FALSE), 0) as closed_count "
			+ "FROM milestone "
			+ "LEFT JOIN issue ON milestone.id = issue.milestone_id AND issue.is_deleted = FALSE "
			+ "WHERE milestone.is_deleted = FALSE "
			+ "GROUP BY milestone.id";

		return jdbcTemplate.query(sql, (rs, rowNum) -> new MilestoneResponse(
			rs.getInt("id"),
			rs.getString("name"),
			rs.getString("description"),
			rs.getTimestamp("due_date").toLocalDateTime(),
			rs.getInt("open_count"),
			rs.getInt("closed_count")
		));
	}

	public void save(Milestone milestone) {
		jdbcInsert.execute(new BeanPropertySqlParameterSource(milestone));
	}
}
