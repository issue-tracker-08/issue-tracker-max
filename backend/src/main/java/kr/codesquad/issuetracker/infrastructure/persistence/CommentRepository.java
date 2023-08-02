package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesquad.issuetracker.domain.Comment;

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

	public Optional<Comment> findById(Integer commentId, Integer issueId) {
		String sql = "SELECT content FROM comment WHERE issue_id = :issueId AND id = :commentId AND is_deleted = false";
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("issueId", issueId)
			.addValue("commentId", commentId);

		return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, params, (rs, rowNum) ->
			new Comment(rs.getString("content")))));
	}

	public void update(Comment comment, Integer commentId) {
		String sql = "UPDATE comment SET content = :content WHERE id = :commentId";
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("content", comment.getContent())
			.addValue("commentId", commentId);
		jdbcTemplate.update(sql, param);
	}
}
