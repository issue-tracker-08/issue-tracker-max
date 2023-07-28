package kr.codesquad.issuetracker.acceptance;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseInitializer {

	private static final String TRUNCATE_QUERY = "TRUNCATE TABLE %s";
	private static final String AUTO_INCREMENT_INIT_QUERY = "ALTER TABLE %s AUTO_INCREMENT = 1";

	@Autowired
	private DataSource dataSource;
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	private final List<String> tableNames = new ArrayList<>();

	@PostConstruct
	public void afterConstruct() {
		try {
			DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
			ResultSet tables = metaData.getTables(null, null, null, new String[] {"TABLE"});

			while (tables.next()) {
				String tableName = tables.getString("TABLE_NAME");
				tableNames.add(tableName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional
	public void initTables() {
		jdbcTemplate.update(
			"INSERT INTO user_account(login_id, password, profile_url) VALUES('iambruni', '312433c28349f63c4f387953ff337046e794bea0f9b9ebfcb08e90046ded9c76', 'default_url')",
			Map.of());

		jdbcTemplate.update(
			"INSERT INTO user_account(login_id, password, profile_url) VALUES ('bean1234', 'bean1234', 'test-url'), "
				+ "('tommy1234', 'tommy1234', 'test-url'), "
				+ "('pie1234', 'pie1234', 'test-url')", Map.of());

		jdbcTemplate.update(
			"INSERT INTO milestone(name, description, due_date) VALUES ('milestone1', 'test milestone1', CURRENT_TIMESTAMP), "
				+ "('milestone2', 'test milestone2', CURRENT_TIMESTAMP), "
				+ "('milestone3', 'test milestone3', CURRENT_TIMESTAMP)", Map.of());

		jdbcTemplate.update("INSERT INTO label(name, description, font_color, background_color) "
			+ "VALUES ('label1', 'test label1', 'black', 'white'), "
			+ "('label2', 'test label2', 'green', 'red'), "
			+ "('label3', 'test label3', 'yellow', 'brown')", Map.of());

		jdbcTemplate.update("INSERT INTO issue_label(issue_id, label_id) VALUES (1, 1), (1, 2), (1, 3)", Map.of());

		jdbcTemplate.update("INSERT INTO issue(title, is_open, created_at, content, user_account_id, milestone_id) "
			+ "VALUES ('issue1', true, CURRENT_TIMESTAMP, 'content1', 1, 1), "
			+ "('issue2', true, CURRENT_TIMESTAMP, 'content2', 1, 2), "
			+ "('issue3', true, CURRENT_TIMESTAMP, 'content3', 1, 3)", Map.of());

		jdbcTemplate.update("INSERT INTO issue_assignee(issue_id, user_account_id) "
			+ "VALUES (1, 1), (1, 2), (1, 3), "
			+ "(2, 2), (2, 3), "
			+ "(3, 1), (3, 3)", Map.of());
	}

	@Transactional
	public void truncateTables() {
		for (String tableName : tableNames) {
			truncateTable(tableName);
		}
	}

	private void truncateTable(final String tableName) {
		jdbcTemplate.update(String.format(TRUNCATE_QUERY, tableName), Map.of());
		jdbcTemplate.update(String.format(AUTO_INCREMENT_INIT_QUERY, tableName), Map.of());
	}
}
