package kr.codesquad.issuetracker.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.infrastructure.persistence.IssueRepository;
import kr.codesquad.issuetracker.infrastructure.persistence.mapper.IssueSimpleMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {

	private final IssueRepository issueRepository;

	@Transactional(readOnly = true)
	public List<IssueSimpleMapper> findAll() {
		return issueRepository.findAll();
	}
}