package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
		String sql =
			"SELECT id, name, description, font_color, background_color "
				+ "FROM label "
				+ "WHERE is_deleted = false "
				+ "ORDER BY name";

		return jdbcTemplate.query(sql, (rs, rowNum) -> new Label(
			rs.getInt("id"),
			rs.getString("name"),
			rs.getString("description"),
			rs.getString("font_color"),
			rs.getString("background_color")
		));
	}

	public void save(Label label) {
		jdbcInsert.execute(new BeanPropertySqlParameterSource(label));
	}

	public void update(Label label) {
		String sql = "UPDATE label SET name = :name, description = :description, font_color = :fontColor, "
			+ "background_color = :backgroundColor WHERE id = :id";

		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("id", label.getId())
			.addValue("name", label.getName())
			.addValue("description", label.getDescription())
			.addValue("fontColor", label.getFontColor())
			.addValue("backgroundColor", label.getBackgroundColor());
		jdbcTemplate.update(sql, param);
	}

	public void deleteById(Integer labelId) {
		String sql = "UPDATE label "
			+ "SET label.is_deleted = TRUE "
			+ "WHERE id = :labelId";

		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("labelId", labelId);
		jdbcTemplate.update(sql, param);
	}

	public int countAll() {
		String sql = "SELECT COUNT(id) FROM label WHERE is_deleted = FALSE";

		return jdbcTemplate.queryForObject(sql, Map.of(), Integer.class);
	}
}

