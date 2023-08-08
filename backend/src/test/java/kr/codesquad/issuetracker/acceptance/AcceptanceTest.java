package kr.codesquad.issuetracker.acceptance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import kr.codesquad.issuetracker.application.S3Service;
import kr.codesquad.issuetracker.infrastructure.security.jwt.JwtProvider;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AcceptanceTest {

	@MockBean
	private S3Service s3Service;

	@Autowired
	protected JwtProvider jwtProvider;

	@Autowired
	private DatabaseInitializer databaseInitializer;

	@BeforeEach
	void setUp() {
		databaseInitializer.initTables();
	}

	@AfterEach
	void clean() {
		databaseInitializer.truncateTables();
	}
}
