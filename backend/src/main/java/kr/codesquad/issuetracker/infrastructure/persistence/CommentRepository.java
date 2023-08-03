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

import kr.codesquad.issuetracker.domain.Comment;
import kr.codesquad.issuetracker.presentation.response.CommentsResponse;

@Repository
public class CommentRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	public CommentRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("comment")
			.usingColumns("content", "user_account_id", "issue_id")
			.usingGeneratedKeyColumns("id");
	}

	public void save(Comment comment) {
		jdbcInsert.execute(new BeanPropertySqlParameterSource(comment));
	}

	public Optional<Comment> findById(Integer commentId) {
		String sql = "SELECT id, content, user_account_id FROM comment WHERE id = :commentId AND is_deleted = false";
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("commentId", commentId);

		return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, params, (rs, rowNum) ->
			new Comment(rs.getInt("id"), rs.getString("content"), rs.getInt("user_account_id")))));
	}

	public void update(Comment comment) {
		String sql = "UPDATE comment SET content = :content WHERE id = :commentId";
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("content", comment.getContent())
			.addValue("commentId", comment.getId());
		jdbcTemplate.update(sql, param);
	}

	public List<CommentsResponse> findAll(Integer issueId, Integer cursor) {
		String sql = "SELECT c.id, u.login_id, u.profile_url, c.content, c.created_at "
			+ "FROM comment c "
			+ "JOIN user_account u ON c.user_account_id = u.id "
			+ "WHERE c.issue_id = :issueId AND c.is_deleted = false AND c.id > :cursor LIMIT 10 ";
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("issueId", issueId)
			.addValue("cursor", cursor);
		return jdbcTemplate.query(sql, param, (rs, rownum) -> new CommentsResponse(
			rs.getInt("id"),
			rs.getString("login_id"),
			rs.getString("profile_url"),
			rs.getString("content"),
			rs.getTimestamp("created_at").toLocalDateTime())
		);
	}

	public boolean isExistCommentByIssueId(Integer issueId) {
		String sql = "SELECT EXISTS (SELECT 1 FROM comment c JOIN issue i ON c.issue_id = i.id "
			+ "WHERE c.issue_id = :issueId)";
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("issueId", issueId);
		return jdbcTemplate.queryForObject(sql, param, boolean.class);
	}

	public boolean hasMoreComment(Integer issueId, Integer cursor) {
		String sql = "SELECT EXISTS (SELECT 1 FROM comment WHERE issue_id = :issueId AND id > :cursor)";
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("issueId", issueId)
			.addValue("cursor", cursor);
		return jdbcTemplate.queryForObject(sql, param, boolean.class);
	}
}
