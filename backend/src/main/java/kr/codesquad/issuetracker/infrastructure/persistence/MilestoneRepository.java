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
			"SELECT milestone.id, milestone.name, "
				+ "SUM(issue.is_open = TRUE) as open_count, SUM(issue.is_open = FALSE) as closed_count " +
				"FROM milestone " +
				"JOIN issue ON milestone.id = issue.milestone_id AND milestone.is_deleted = FALSE " +
				"WHERE issue.id = :issueId " +
				"GROUP BY milestone.id";

		return DataAccessUtils.singleResult(
			jdbcTemplate.query(sql, Map.of("issueId", issueId), (rs, rowNum) -> new MilestoneResponse(
				rs.getInt("id"),
				rs.getString("name"),
				rs.getInt("open_count"),
				rs.getInt("closed_count")
			)));
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
}
