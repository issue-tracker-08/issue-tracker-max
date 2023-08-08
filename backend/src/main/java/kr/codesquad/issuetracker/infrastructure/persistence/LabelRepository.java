package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesquad.issuetracker.domain.Label;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class LabelRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

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
}

