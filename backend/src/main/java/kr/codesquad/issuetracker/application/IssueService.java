package kr.codesquad.issuetracker.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.persistence.IssueRepository;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;
import kr.codesquad.issuetracker.presentation.request.AssigneeRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {

	private final IssueRepository issueRepository;

	@Transactional(readOnly = true)
	public List<IssueSimpleMapper> findAll() {
		return issueRepository.findAll();
	}

	@Transactional
	public void updateAssignees(AssigneeRequest assigneeRequest) {
		if (!issueRepository.existsById(assigneeRequest.getIssueId())) {
			throw new ApplicationException(ErrorCode.ISSUE_NOT_FOUND);
		}

		issueRepository.deleteAssignees(assigneeRequest.getRemoveIssueAssignees());
		issueRepository.saveAssignees(assigneeRequest.getAddIssueAssignees());
	}
}