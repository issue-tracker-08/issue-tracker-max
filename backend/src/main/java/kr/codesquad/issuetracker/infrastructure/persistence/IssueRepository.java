package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesquad.issuetracker.domain.Issue;
import kr.codesquad.issuetracker.presentation.converter.OpenState;
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
				"user_account.login_id, user_account.profile_url, ("
				+ "SELECT COUNT(1) "
				+ "FROM comment "
				+ "WHERE comment.issue_id = :issueId AND comment.is_deleted = FALSE) AS comment_count " +
				"FROM issue " +
				"JOIN user_account ON issue.user_account_id = user_account.id AND user_account.is_deleted = FALSE " +
				"WHERE issue.id = :issueId";

		return Optional.ofNullable(DataAccessUtils.singleResult(
			jdbcTemplate.query(sql, Map.of("issueId", issueId), (rs, rowNum) -> new IssueDetailResponse(
				rs.getInt("id"),
				rs.getString("title"),
				rs.getBoolean("is_open"),
				rs.getTimestamp("created_at").toLocalDateTime(),
				rs.getString("content"),
				new IssueDetailResponse.Author(rs.getString("login_id"), rs.getString("profile_url")),
				rs.getInt("comment_count")
			))));
	}

	public Integer findMilestoneIdById(Integer issueId) {
		String sql = "SELECT milestone_id FROM issue WHERE id = :issueId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("issueId", issueId);

		Integer milestoneId = DataAccessUtils.singleResult( // rs.getInt 메서드는 값이 null 이면 0을 반환
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

	public void updateAllIssue(OpenState openState, List<Integer> issueIds) {
		String sql = "UPDATE issue SET is_open = :openState WHERE id = :issueId";
		List<MapSqlParameterSource> params = new ArrayList<>();
		for (Integer issueId : issueIds) {
			MapSqlParameterSource param = new MapSqlParameterSource();
			param
				.addValue("openState", openState.isOpen())
				.addValue("issueId", issueId);
			params.add(param);
		}
		jdbcTemplate.batchUpdate(sql, params.toArray(MapSqlParameterSource[]::new));
	}

	public void delete(Integer issueId) {
		String sql = "UPDATE issue SET is_deleted = 1 WHERE id = :issueId";

		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("issueId", issueId);

		jdbcTemplate.update(sql, param);
	}
}
