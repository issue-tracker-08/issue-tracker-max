package kr.codesquad.issuetracker.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

