package kr.codesquad.issuetracker.utils;

import java.util.Optional;

import kr.codesquad.issuetracker.domain.IssueSearch;
import kr.codesquad.issuetracker.domain.IssueSearchFilter;

public class IssueSearchParser {

	private static final String EMPTY = "";
	private static final String SEARCH_SEPARATOR = " ";
	private static final String DOUBLE_QUOTE = "\"";
	private static final String MY_LOGIN_ID = "@me";

	public static IssueSearch parse(final String loginId, String searchBar) {
		final IssueSearch issueSearch = new IssueSearch();
		Optional<IssueSearchFilter> findSearchFilter = IssueSearchFilter.findStartsWith(searchBar);
		searchBar += SEARCH_SEPARATOR;

		while (findSearchFilter.isPresent()) {
			IssueSearchFilter searchFilter = findSearchFilter.get();
			searchBar = searchBar.replaceFirst(searchFilter.getKey(), EMPTY);
			String value = extractValue(searchBar);
			if (MY_LOGIN_ID.equals(value)) {
				searchBar = searchBar.replaceFirst(MY_LOGIN_ID, loginId);
				value = loginId;
			}

			searchFilter.register(issueSearch, value.replaceAll(DOUBLE_QUOTE, EMPTY));
			searchBar = searchBar.replaceFirst(value + SEARCH_SEPARATOR, EMPTY);

			findSearchFilter = IssueSearchFilter.findStartsWith(searchBar);
		}

		return issueSearch;
	}

	private static String extractValue(String search) {
		int valueEndIndex = search.indexOf(SEARCH_SEPARATOR);
		if (valueEndIndex == -1) {
			return search;
		}
		if (search.startsWith(DOUBLE_QUOTE)) {
			valueEndIndex = search.indexOf(DOUBLE_QUOTE, 1) + 1;
		}

		return search.substring(0, valueEndIndex);
	}
}
