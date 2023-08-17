package kr.codesquad.issuetracker.application;

import java.util.List;
import java.util.function.BiConsumer;
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
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueDAO;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;
import kr.codesquad.issuetracker.presentation.converter.OpenState;
import kr.codesquad.issuetracker.presentation.request.AssigneeRequest;
import kr.codesquad.issuetracker.presentation.request.IssueLabelRequest;
import kr.codesquad.issuetracker.presentation.request.IssueRegisterRequest;
import kr.codesquad.issuetracker.presentation.response.IssueDetailResponse;
import kr.codesquad.issuetracker.presentation.response.IssueDetailSidebarResponse;
import kr.codesquad.issuetracker.presentation.response.Page;
import kr.codesquad.issuetracker.utils.IssueSearchParser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {

	private final IssueRepository issueRepository;
	private final IssueAssigneeRepository assigneeRepository;
	private final IssueLabelRepository issueLabelRepository;
	private final IssueDAO issueMapper;

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
		return issueRepository.findIssueDetailResponseById(issueId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.ISSUE_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public IssueDetailSidebarResponse getIssueDetailsSidebar(Integer issueId) {
		List<Integer> assigneeIds = assigneeRepository.findIdsByIssueId(issueId);
		List<Integer> labelIds = issueLabelRepository.findIdsByIssueId(issueId);
		Integer milestoneId = issueRepository.findMilestoneIdById(issueId);

		return new IssueDetailSidebarResponse(assigneeIds, labelIds, milestoneId);
	}

	@Transactional(readOnly = true)
	public Page<IssueSimpleMapper> findAll(String loginId, String searchBar, int page, int size) {
		int offset = (page - 1) * size;
		int totalCounts = issueMapper.countAll(IssueSearchParser.parse(loginId, searchBar));
		List<IssueSimpleMapper> issues = issueMapper.findAll(IssueSearchParser.parse(loginId, searchBar), offset, size);
		return Page.of(issues, totalCounts, page, size);
	}

	@Transactional
	public void modifyIssueTitle(Integer userId, Integer issueId, String title) {
		modifyIssue(userId, issueId, title, Issue::modifyTitle);
	}

	@Transactional
	public void modifyIssueContent(Integer userId, Integer issueId, String content) {
		modifyIssue(userId, issueId, content, Issue::modifyContent);
	}

	@Transactional
	public void modifyIssueOpenStatus(Integer userId, Integer issueId, Boolean isOpen) {
		modifyIssue(userId, issueId, String.valueOf(isOpen), Issue::modifyOpenStatus);
	}

	private void modifyIssue(Integer userId, Integer issueId, String modifiedData,
		BiConsumer<Issue, String> modifyFunction) {
		Issue issue = issueRepository.findById(issueId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.ISSUE_NOT_FOUND));

		if (!issue.isAuthor(userId)) {
			throw new ApplicationException(ErrorCode.NO_AUTHORIZATION);
		}

		modifyFunction.accept(issue, modifiedData);

		issueRepository.updateIssue(issue);
	}

	@Transactional
	public void updateAssignees(Integer issueId, AssigneeRequest request) {
		if (!issueRepository.existsById(issueId)) {
			throw new ApplicationException(ErrorCode.ISSUE_NOT_FOUND);
		}

		assigneeRepository.saveAll(toEntityList(request.getAddUserAccountId(),
			id -> new IssueAssignee(issueId, id)));
		assigneeRepository.deleteAll(toEntityList(request.getRemoveUserAccountId(),
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

	@Transactional
	public void updateIssueMilestone(Integer issueId, Integer milestoneId) {
		if (!issueRepository.existsById(issueId)) {
			throw new ApplicationException(ErrorCode.ISSUE_NOT_FOUND);
		}

		issueRepository.updateIssueMilestone(issueId, milestoneId);
	}

	@Transactional
	public void modifyMultipleIssueState(OpenState openState, List<Integer> issueIds) {
		issueRepository.updateAllIssue(openState, issueIds);
	}
}
