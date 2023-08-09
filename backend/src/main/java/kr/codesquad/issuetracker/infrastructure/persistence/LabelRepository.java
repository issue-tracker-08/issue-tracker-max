package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesquad.issuetracker.domain.Label;

@Repository
public class LabelRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	public LabelRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("label")
			.usingColumns("name", "description", "font_color", "background_color")
			.usingGeneratedKeyColumns("id");
	}

	public List<Label> findAll() {
		String sql = "SELECT id, name, font_color, background_color "
			+ "FROM label "
			+ "WHERE is_deleted = false "
			+ "ORDER BY name";

		return jdbcTemplate.query(sql, (rs, rowNum) -> new Label(
			rs.getInt("id"),
			rs.getString("name"),
			rs.getString("font_color"),
			rs.getString("background_color")
		));
	}

	public void save(Label label) {
		jdbcInsert.execute(new BeanPropertySqlParameterSource(label));
	}
}

