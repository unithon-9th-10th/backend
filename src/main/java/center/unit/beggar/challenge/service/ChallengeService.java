package center.unit.beggar.challenge.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import center.unit.beggar.challenge.dto.request.ChallengePostDto;
import center.unit.beggar.challenge.model.Challenge;
import center.unit.beggar.challenge.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ChallengeService {

	private final ChallengeRepository challengeRepository;

	public Challenge saveChallengeByRequestDto(ChallengePostDto requestDto) {
		Challenge challenge = Challenge.builder()
			.title(requestDto.getTitle())
			.startDate(requestDto.getStartDate())
			.endDate(requestDto.getEndDate())
			.challengeDays(requestDto.getChallengeDays())
			.amount(requestDto.getAmount())
			.build();

		challengeRepository.save(challenge);
		return challenge;
	}

}
