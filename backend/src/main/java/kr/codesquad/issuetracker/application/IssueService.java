package kr.codesquad.issuetracker.application;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.domain.Issue;
import kr.codesquad.issuetracker.domain.IssueAssignee;
import kr.codesquad.issuetracker.domain.IssueLabel;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.persistence.IssueAssigneeRepository;
import kr.codesquad.issuetracker.infrastructure.persistence.IssueLabelRepository;
import kr.codesquad.issuetracker.infrastructure.persistence.IssueRepository;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;
import kr.codesquad.issuetracker.presentation.request.AssigneeRequest;
import kr.codesquad.issuetracker.presentation.request.IssueLabelRequest;
import kr.codesquad.issuetracker.presentation.request.IssueRegisterRequest;
import kr.codesquad.issuetracker.presentation.response.IssueDetailResponse;
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

		assigneeRepository.saveAll(toEntityList(request.getAssignees(),
			id -> new IssueAssignee(issueId, id)));
		issueLabelRepository.saveAll(toEntityList(request.getLabels(),
			id -> new IssueLabel(issueId, id)));

		return issueId;
	}

	private <T> List<T> toEntityList(List<Integer> ids, Function<Integer, T> mapper) {
		return ids.stream()
			.map(mapper)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public IssueDetailResponse getIssueDetails(Integer issueId) {
		if (!issueRepository.existsById(issueId)) {
			throw new ApplicationException(ErrorCode.ISSUE_NOT_FOUND);
		}
		return issueRepository.findIssueDetailResponseById(issueId);
	}

	@Transactional(readOnly = true)
	public List<IssueSimpleMapper> findAll() {
		return issueRepository.findAll();
	}

	@Transactional
	public void updateAssignees(Integer issueId, AssigneeRequest request) {
		if (!issueRepository.existsById(issueId)) {
			throw new ApplicationException(ErrorCode.ISSUE_NOT_FOUND);
		}

		assigneeRepository.saveAll(toEntityList(request.getAddUserAccountsId(),
			id -> new IssueAssignee(issueId, id)));
		assigneeRepository.deleteAll(toEntityList(request.getRemoveUserAccountsId(),
			id -> new IssueAssignee(issueId, id)));
	}

	@Transactional
	public void updateIssueLabels(Integer issueId, IssueLabelRequest request) {
		if (!issueRepository.existsById(issueId)) {
			throw new ApplicationException(ErrorCode.ISSUE_NOT_FOUND);
		}

		issueLabelRepository.saveAll(toEntityList(request.getAddLabelsId(),
			id -> new IssueLabel(issueId, id)));
		issueLabelRepository.deleteAll(toEntityList(request.getRemoveLabelsId(),
			id -> new IssueLabel(issueId, id)));
	}

	public void updateIssueMilestone(Integer issueId, Integer milestoneId) {
		if (!issueRepository.existsById(issueId)) {
			throw new ApplicationException(ErrorCode.ISSUE_NOT_FOUND);
		}

		issueRepository.updateIssueMilestone(issueId, milestoneId);
	}
}
