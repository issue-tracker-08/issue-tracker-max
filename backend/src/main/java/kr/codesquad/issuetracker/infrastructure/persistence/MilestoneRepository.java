package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.List;
import java.util.Map;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesquad.issuetracker.presentation.response.MilestoneResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MilestoneRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public MilestoneResponse findMilestoneByIssueId(Integer issueId) {
		String sql =
			"SELECT m.id, m.name, SUM(i.is_open = TRUE) as open_count, SUM(i.is_open = FALSE) as closed_count " +
				"FROM milestone m " +
				"JOIN issue i ON m.id = i.milestone_id AND m.is_deleted = FALSE " +
				"WHERE i.id = :issueId " +
				"GROUP BY m.id";

		return DataAccessUtils.singleResult(
			jdbcTemplate.query(sql, Map.of("issueId", issueId), (rs, rowNum) -> new MilestoneResponse(
				rs.getInt("id"),
				rs.getString("name"),
				rs.getInt("open_count"),
				rs.getInt("closed_count")
			)));
	}

	public List<MilestoneResponse> findAll() {
		String sql = "SELECT m.id, m.name, m.description, m.due_date, "
			+ "IFNULL(SUM(i.is_open = TRUE), 0) as open_count, "
			+ "IFNULL(SUM(i.is_open = FALSE), 0) as closed_count "
			+ "FROM milestone m "
			+ "LEFT JOIN issue i ON m.id = i.milestone_id AND i.is_deleted = FALSE "
			+ "WHERE m.is_deleted = FALSE "
			+ "GROUP BY m.id";

		return jdbcTemplate.query(sql, (rs, rowNum) -> new MilestoneResponse(
			rs.getInt("id"),
			rs.getString("name"),
			rs.getString("description"),
			rs.getTimestamp("due_date").toLocalDateTime(),
			rs.getInt("open_count"),
			rs.getInt("closed_count")
		));
	}
}
