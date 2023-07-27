package kr.codesquad.issuetracker.application;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.infrastructure.persistence.IssueRepository;
import kr.codesquad.issuetracker.presentation.response.IssueSimpleResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {

	private final IssueRepository issueRepository;

	@Transactional(readOnly = true)
	public List<IssueSimpleResponse> findAll() {
		return issueRepository.findAll().stream()
			.map(IssueSimpleResponse::from)
			.collect(Collectors.toList());
	}
}