package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.Map;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesquad.issuetracker.presentation.response.IssueDetailResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MilestoneRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public IssueDetailResponse.MilestoneInfo findMilestoneInfoByIssueId(Integer issueId) {
		String sql =
			"SELECT m.id, m.name, SUM(i.is_open = TRUE) as open_count, SUM(i.is_open = FALSE) as closed_count " +
				"FROM milestone m " +
				"JOIN issue i ON m.id = i.milestone_id AND m.is_deleted = FALSE " +
				"WHERE i.id = :issueId " +
				"GROUP BY m.id";

		return DataAccessUtils.singleResult(
			jdbcTemplate.query(sql, Map.of("issueId", issueId), (rs, rowNum) -> new IssueDetailResponse.MilestoneInfo(
				rs.getInt("id"),
				rs.getString("name"),
				rs.getInt("open_count"),
				rs.getInt("closed_count")
			)));
	}
}
