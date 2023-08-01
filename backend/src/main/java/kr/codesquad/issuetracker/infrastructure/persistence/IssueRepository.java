package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import kr.codesquad.issuetracker.domain.IssueAssignee;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;

@Repository
public class IssueRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public IssueRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public boolean existsById(Integer issueId) {
		String sql = "SELECT EXISTS (SELECT 1 FROM issue WHERE id = :issueId)";

		return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Map.of("issueId", issueId), Boolean.class));
	}

	public void saveAssignees(List<IssueAssignee> issueAssignees) {
		String sql = "INSERT INTO issue_assignee (issue_id, user_account_id) VALUES (:issueId, :userAccountId)";

		jdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(issueAssignees));
	}

	public void deleteAssignees(List<IssueAssignee> issueAssignees) {
		String sql = "DELETE FROM issue_assignee WHERE issue_id = :issueId AND user_account_id = :userAccountId";

		jdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(issueAssignees));
	}

	public List<IssueSimpleMapper> findAll() {
		String sql = "SELECT i.id, i.is_open, i.title, i.created_at, m.name as milestone, "
			+ "CONCAT('[', GROUP_CONCAT(DISTINCT JSON_OBJECT( "
			+ "'name', l.name, 'fontColor', l.font_color, 'backgroundColor', l.background_color)), ']') as labels, "
			+ "IFNULL(ua2.login_id, '(알수없음)') as author_name, "
			+ "CONCAT('[', GROUP_CONCAT(DISTINCT JSON_OBJECT( "
			+ "'loginId', ua.login_id, 'profileUrl', ua.profile_url)), ']') as assignee "
			+ "FROM issue i "
			+ "LEFT JOIN issue_label il ON i.id = il.issue_id "
			+ "LEFT JOIN label l ON l.id = il.label_id AND l.is_deleted = false "
			+ "LEFT JOIN issue_assignee ia ON i.id = ia.issue_id "
			+ "LEFT JOIN user_account ua ON ua.id = ia.user_account_id AND ua.is_deleted = false "
			+ "LEFT JOIN milestone m ON i.milestone_id = m.id AND m.is_deleted = false "
			+ "LEFT JOIN user_account ua2 ON ua2.id = i.user_account_id AND ua2.is_deleted = false "
			+ "WHERE i.is_deleted = false "
			+ "GROUP BY i.id "
			+ "ORDER BY i.id DESC";

		return jdbcTemplate.query(sql, Collections.emptyMap(), mapSimpleIssue());
	}

	private static RowMapper<IssueSimpleMapper> mapSimpleIssue() {
		return (rs, rowNum) -> IssueSimpleMapper.of(
			rs.getInt("id"),
			rs.getBoolean("is_open"),
			rs.getString("labels"),
			rs.getString("title"),
			rs.getString("milestone"),
			rs.getString("assignee"),
			rs.getTimestamp("created_at").toLocalDateTime());
	}
}
