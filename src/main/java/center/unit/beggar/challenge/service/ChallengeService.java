package center.unit.beggar.challenge.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import center.unit.beggar.challenge.dto.request.ChallengePostDto;
import center.unit.beggar.challenge.model.Challenge;
import center.unit.beggar.challenge.repository.ChallengeRepository;
import center.unit.beggar.member.model.MemberChallenge;
import center.unit.beggar.member.model.MemberStatus;
import center.unit.beggar.member.repository.MemberChallengeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final MemberChallengeRepository memberChallengeRepository;

    public MemberStatus resolveMemberStatus(Long memberId) {
        List<Long> challengeIds = memberChallengeRepository.findByMember_memberId(memberId)
                .stream()
                .map(MemberChallenge::getChallenge)
                .map(Challenge::getChallengeId).collect(Collectors.toList());
        List<Challenge> challenges = challengeRepository.findAllById(challengeIds);

        if (CollectionUtils.isEmpty(challenges)) {
            return MemberStatus.READY;
        }
        LocalDate today = LocalDate.now();
        boolean hasRunningChallenge = challenges.stream().anyMatch(
                it -> !(it.getStartDate().isBefore(today) || it.getEndDate().isAfter(today))
        );
        return hasRunningChallenge ? MemberStatus.RUNNING : MemberStatus.FINISHED;
    }

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
