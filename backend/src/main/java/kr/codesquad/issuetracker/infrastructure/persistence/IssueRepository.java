package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesquad.issuetracker.domain.Issue;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;
import kr.codesquad.issuetracker.presentation.response.IssueDetailResponse;
import kr.codesquad.issuetracker.presentation.response.LabelResponse;
import kr.codesquad.issuetracker.presentation.response.MilestoneResponse;

@Repository
public class IssueRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	private final MilestoneRepository milestoneRepository;

	public IssueRepository(DataSource dataSource, MilestoneRepository milestoneRepository) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("issue")
			.usingGeneratedKeyColumns("id")
			.usingColumns("title", "is_open", "content", "user_account_id", "milestone_id");
		this.milestoneRepository = milestoneRepository;
	}

	public List<IssueSimpleMapper> findAll() {
		String sql = "SELECT i.id, i.is_open, i.title, i.created_at, m.name as milestone, "
			+ "CONCAT('[', GROUP_CONCAT(DISTINCT JSON_OBJECT( "
			+ "'name', l.name, 'fontColor', l.font_color, 'backgroundColor', l.background_color)), ']') as labels, "
			+ "IFNULL(ua2.login_id, '(알수없음)') as author_name, "
			+ "CONCAT('[', GROUP_CONCAT(DISTINCT JSON_OBJECT( "
			+ "'username', ua.login_id, 'profileUrl', ua.profile_url)), ']') as assignee "
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

	public Integer save(Issue issue) {
		return jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(issue)).intValue();
	}

	public boolean existsById(Integer issueId) {
		String sql = "SELECT EXISTS (SELECT 1 FROM issue WHERE id = :issueId)";

		return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Map.of("issueId", issueId), Boolean.class));
	}

	public IssueDetailResponse findIssueDetailResponseById(Integer issueId) {
		List<IssueDetailResponse.Assignee> assignees = findAssigneeById(issueId);
		List<LabelResponse> labels = findLabelInfoById(issueId);
		MilestoneResponse milestone = milestoneRepository.findMilestoneByIssueId(issueId);

		String sql = "SELECT i.id, i.title, i.is_open, i.created_at, i.content, ua.login_id, ua.profile_url " +
			"FROM issue i " +
			"JOIN user_account ua ON i.user_account_id = ua.id AND ua.is_deleted = FALSE " +
			"WHERE i.id = :issueId";

		return DataAccessUtils.singleResult(
			jdbcTemplate.query(sql, Map.of("issueId", issueId), (rs, rowNum) -> new IssueDetailResponse(
				rs.getInt("id"),
				rs.getString("title"),
				rs.getBoolean("is_open"),
				rs.getTimestamp("created_at").toLocalDateTime(),
				rs.getString("content"),
				new IssueDetailResponse.Author(rs.getString("login_id"), rs.getString("profile_url")),
				assignees,
				labels,
				milestone
			)));
	}

	private List<IssueDetailResponse.Assignee> findAssigneeById(Integer issueId) {
		String sql = "SELECT ia.user_account_id, ua.login_id, ua.profile_url " +
			"FROM issue i " +
			"JOIN issue_assignee ia ON i.id = ia.issue_id " +
			"JOIN user_account ua ON ia.user_account_id = ua.id AND ua.is_deleted = FALSE " +
			"WHERE i.id = :issueId";

		return jdbcTemplate.query(sql, Map.of("issueId", issueId), (rs, rowNum) -> new IssueDetailResponse.Assignee(
			rs.getInt("user_account_id"),
			rs.getString("login_id"),
			rs.getString("profile_url")
		));
	}

	private List<LabelResponse> findLabelInfoById(Integer issueId) {
		String sql = "SELECT l.id, l.name, l.font_color, l.background_color " +
			"FROM issue i " +
			"JOIN issue_label il ON i.id = il.issue_id " +
			"JOIN label l ON il.label_id = l.id AND l.is_deleted = FALSE " +
			"WHERE i.id = :issueId";

		return jdbcTemplate.query(sql, Map.of("issueId", issueId), (rs, rowNum) -> new LabelResponse(
			rs.getInt("id"),
			rs.getString("name"),
			rs.getString("font_color"),
			rs.getString("background_color")
		));
	}
}
