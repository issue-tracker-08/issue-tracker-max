package kr.codesquad.issuetracker.application;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.domain.Issue;
import kr.codesquad.issuetracker.domain.IssueAssignee;
import kr.codesquad.issuetracker.domain.IssueLabel;
import kr.codesquad.issuetracker.infrastructure.persistence.IssueAssigneeRepository;
import kr.codesquad.issuetracker.infrastructure.persistence.IssueLabelRepository;
import kr.codesquad.issuetracker.infrastructure.persistence.IssueRepository;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;
import kr.codesquad.issuetracker.presentation.request.IssueRegisterRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {

	private final IssueRepository issueRepository;
	private final IssueAssigneeRepository assigneeRepository;
	private final IssueLabelRepository issueLabelRepository;

	@Transactional
	public Integer register(Integer authorId, IssueRegisterRequest request) {
		Integer issueId = issueRepository.save(new Issue(request.getTitle(),
			request.getContent(),
			Boolean.TRUE,
			authorId,
			request.getMilestone().orElse(null)));

		request.getAssignees().ifPresent(assigneeIds ->
			assigneeRepository.saveAll(toEntityList(assigneeIds, id -> new IssueAssignee(issueId, id))));

		request.getLabels().ifPresent(labelIds ->
			issueLabelRepository.saveAll(toEntityList(labelIds, id -> new IssueLabel(issueId, id))));

		return issueId;
	}

	private <T> List<T> toEntityList(List<Integer> ids, Function<Integer, T> mapper) {
		return ids.stream()
			.map(mapper)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<IssueSimpleMapper> findAll() {
		return issueRepository.findAll();
	}
}
