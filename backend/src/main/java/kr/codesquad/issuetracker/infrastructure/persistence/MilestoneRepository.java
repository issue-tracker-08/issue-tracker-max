package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
			rs.getTimestamp("due_date"),
			rs.getInt("open_count"),
			rs.getInt("closed_count")
		));
	}

	public void save(Milestone milestone) {
		jdbcInsert.execute(new BeanPropertySqlParameterSource(milestone));
	}

	public Optional<Milestone> findById(Integer milestoneId) {
		String sql = "SELECT id, name, description, due_date "
			+ "FROM milestone "
			+ "WHERE id = :milestoneId AND is_deleted = FALSE";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("milestoneId", milestoneId);
		return Optional.ofNullable(DataAccessUtils.singleResult(
			jdbcTemplate.query(sql, params, (rs, rowNum) -> new Milestone(
				rs.getInt("id"),
				rs.getString("name"),
				rs.getString("description"),
				rs.getTimestamp("due_date")))));
	}

	public void update(Milestone milestone) {
		String sql = "UPDATE milestone "
			+ "SET name = :name, description = :description, due_date = :dueDate "
			+ "WHERE id = :id";

		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("name", milestone.getName())
			.addValue("description", milestone.getDescription())
			.addValue("dueDate", milestone.getDueDate())
			.addValue("id", milestone.getId());
		jdbcTemplate.update(sql, params);
	}
}
