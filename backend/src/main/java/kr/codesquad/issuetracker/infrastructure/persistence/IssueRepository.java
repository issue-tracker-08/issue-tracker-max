package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesquad.issuetracker.domain.Issue;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;
import kr.codesquad.issuetracker.presentation.response.IssueDetailResponse;

@Repository
public class IssueRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	public IssueRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("issue")
			.usingGeneratedKeyColumns("id")
			.usingColumns("title", "is_open", "content", "user_account_id", "milestone_id");
	}

	public List<IssueSimpleMapper> findAll() {
		String sql = "SELECT issue.id, issue.is_open, issue.title, issue.created_at, milestone.name as milestone, "
			+ "CONCAT('[', GROUP_CONCAT(DISTINCT JSON_OBJECT( "
			+ "'name', label.name, 'fontColor', label.font_color, 'backgroundColor', label.background_color)), ']') as labels, "
			+ "IFNULL(user2.login_id, '(알수없음)') as author_name, "
			+ "CONCAT('[', GROUP_CONCAT(DISTINCT JSON_OBJECT( "
			+ "'username', user.login_id, 'profileUrl', user.profile_url)), ']') as assignee "
			+ "FROM issue "
			+ "LEFT JOIN issue_label ON issue.id = issue_label.issue_id "
			+ "LEFT JOIN label ON label.id = issue_label.label_id AND label.is_deleted = false "
			+ "LEFT JOIN issue_assignee assignee ON issue.id = assignee.issue_id "
			+ "LEFT JOIN user_account user ON user.id = assignee.user_account_id AND user.is_deleted = false "
			+ "LEFT JOIN milestone ON issue.milestone_id = milestone.id AND milestone.is_deleted = false "
			+ "LEFT JOIN user_account user2 ON user2.id = issue.user_account_id AND user2.is_deleted = false "
			+ "WHERE issue.is_deleted = false "
			+ "GROUP BY issue.id "
			+ "ORDER BY issue.id DESC";

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

	public Integer save(Issue issue) {
		return jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(issue)).intValue();
	}

	public boolean existsById(Integer issueId) {
		String sql = "SELECT EXISTS (SELECT 1 FROM issue WHERE id = :issueId)";

		return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Map.of("issueId", issueId), Boolean.class));
	}

	public Optional<IssueDetailResponse> findIssueDetailResponseById(Integer issueId) {
		String sql =
			"SELECT issue.id, issue.title, issue.is_open, issue.created_at, issue.content, " +
				"user.login_id, user.profile_url " +
				"FROM issue " +
				"JOIN user_account user ON issue.user_account_id = user.id AND user.is_deleted = FALSE " +
				"WHERE issue.id = :issueId";

		return Optional.ofNullable(DataAccessUtils.singleResult(
			jdbcTemplate.query(sql, Map.of("issueId", issueId), (rs, rowNum) -> new IssueDetailResponse(
				rs.getInt("id"),
				rs.getString("title"),
				rs.getBoolean("is_open"),
				rs.getTimestamp("created_at").toLocalDateTime(),
				rs.getString("content"),
				new IssueDetailResponse.Author(rs.getString("login_id"), rs.getString("profile_url"))
			))));
	}

	public Integer findMilestoneIdById(Integer issueId) {
		String sql = "SELECT milestone_id FROM issue WHERE id = :issueId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("issueId", issueId);

		Integer milestoneId = DataAccessUtils.singleResult(
			jdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getInt("milestone_id")));
		if (Objects.equals(milestoneId, 0)) {
			return null;
		}
		return milestoneId;
	}

	public Optional<Issue> findById(Integer issueId) {
		String sql = "SELECT id, title, is_open, content, user_account_id FROM issue WHERE id = :issueId";

		return Optional.ofNullable(DataAccessUtils.singleResult(
			jdbcTemplate.query(sql, Map.of("issueId", issueId), (rs, rowNum) -> new Issue(
				rs.getInt("id"),
				rs.getString("title"),
				rs.getString("content"),
				rs.getBoolean("is_open"),
				rs.getInt("user_account_id")
			))));
	}

	public void updateIssue(Issue issue) {
		String sql = "UPDATE issue "
			+ "SET title = :title, is_open = :isOpen, content = :content "
			+ "WHERE id = :issueId";

		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("title", issue.getTitle())
			.addValue("isOpen", issue.getIsOpen())
			.addValue("content", issue.getContent())
			.addValue("issueId", issue.getId());

		jdbcTemplate.update(sql, param);
	}

	public void updateIssueMilestone(Integer issueId, Integer milestoneId) {
		String sql = "UPDATE issue SET milestone_id = :milestoneId WHERE id = :issueId";

		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("milestoneId", milestoneId)
			.addValue("issueId", issueId);

		jdbcTemplate.update(sql, params);
	}
}
