package kr.codesquad.issuetracker.infrastructure.persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.codesquad.issuetracker.domain.UserAccount;

@Repository
public class UserAccountRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	public UserAccountRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("user_account")
			.usingColumns("login_id", "password", "profile_url", "email")
			.usingGeneratedKeyColumns("id");
	}

	public int save(UserAccount userAccount) {
		// return user_account PK
		return jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(userAccount)).intValue();
	}

	public Boolean existsByLoginId(String loginId) {
		return jdbcTemplate.queryForObject("SELECT EXISTS (SELECT 1 FROM user_account WHERE login_id = :loginId"
				+ " AND is_deleted = FALSE)",
			Map.of("loginId", loginId), Boolean.class);
	}

	public Optional<UserAccount> findByLoginId(String loginId) {
		return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(
			"SELECT id, login_id, password, profile_url "
				+ "FROM user_account "
				+ "WHERE login_id = :loginId AND is_deleted = FALSE",
			Map.of("loginId", loginId), (rs, rowNum) -> new UserAccount(rs.getInt("id"),
				rs.getString("login_id"),
				rs.getString("password"),
				rs.getString("profile_url")))));
	}

	public List<UserAccount> findAll() {
		String sql = "SELECT id, login_id, profile_url FROM user_account WHERE is_deleted = FALSE";

		return jdbcTemplate.query(sql, (rs, rowNum) -> UserAccount.createUserProfile(rs.getInt("id"),
			rs.getString("login_id"),
			rs.getString("profile_url")));
	}

	public Optional<UserAccount> findByEmail(String email) {
		String sql = "SELECT id, login_id, profile_url FROM user_account WHERE email = :email AND is_deleted = FALSE";

		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("email", email);
		return Optional.ofNullable(
			DataAccessUtils.singleResult(jdbcTemplate.query(sql, params, (rs, rowNum) -> UserAccount.createUserProfile(
				rs.getInt("id"),
				rs.getString("login_id"),
				rs.getString("profile_url")
			))));
	}

	public void delete(Integer userId) {
		String sql = "UPDATE user_account SET is_deleted = 1 WHERE id = :userId";

		final MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("userId", userId);

		jdbcTemplate.update(sql, param);
	}
}
