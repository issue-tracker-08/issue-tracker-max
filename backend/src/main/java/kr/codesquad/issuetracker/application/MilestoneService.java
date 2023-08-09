package kr.codesquad.issuetracker.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.domain.Milestone;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import kr.codesquad.issuetracker.infrastructure.persistence.MilestoneRepository;
import kr.codesquad.issuetracker.presentation.response.MilestoneResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MilestoneService {

	private final MilestoneRepository milestoneRepository;

	@Transactional(readOnly = true)
	public List<MilestoneResponse> findAll() {
		return milestoneRepository.findAll();
	}

	@Transactional
	public void register(String name, String description, LocalDateTime dueDate) {
		Milestone milestone = new Milestone(name, description, dueDate);
		milestoneRepository.save(milestone);
	}

	@Transactional
	public void modify(Integer milestoneId, String milestoneName, String description, LocalDateTime dueDate) {
		Milestone milestone = milestoneRepository.findById(milestoneId)
			.orElseThrow(() -> new ApplicationException(ErrorCode.MILESTONE_NOT_FOUND));

		milestone.modify(milestoneName, description, dueDate);
		milestoneRepository.update(milestone);
	}

	@Transactional
	public void remove(Integer milestoneId) {
		milestoneRepository.deleteById(milestoneId);
	}
}

