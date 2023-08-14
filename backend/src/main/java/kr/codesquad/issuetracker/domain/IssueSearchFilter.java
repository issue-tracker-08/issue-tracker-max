package kr.codesquad.issuetracker.domain;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IssueSearchFilter {

	STATUS("is:", IssueSearch::registerIssueStatus),
	AUTHOR("author:", IssueSearch::registerAuthor),
	COMMENTER("commenter:", IssueSearch::registerCommenter),
	ASSIGNEE("assignee:", IssueSearch::addAssignee),
	LABEL("label:", IssueSearch::addLabel),
	MILESTONE("milestone:", IssueSearch::registerMilestone),
	NO("no:", IssueSearch::registerNone);

	private final String key;
	private final BiConsumer<IssueSearch, String> register;

	public void register(IssueSearch issueSearch, String value) {
		register.accept(issueSearch, value);
	}

	public static Optional<IssueSearchFilter> findStartsWith(String key) {
		if (!StringUtils.hasText(key)) {
			return Optional.empty();
		}

		return Arrays.stream(values())
			.filter(searchFilter -> key.startsWith(searchFilter.key))
			.findFirst();
	}
}
