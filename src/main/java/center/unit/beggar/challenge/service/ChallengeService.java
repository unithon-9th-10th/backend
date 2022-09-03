package center.unit.beggar.challenge.service;

import center.unit.beggar.challenge.model.Challenge;
import center.unit.beggar.challenge.repository.ChallengeRepository;
import center.unit.beggar.member.model.MemberChallenge;
import center.unit.beggar.member.model.MemberStatus;
import center.unit.beggar.member.repository.MemberChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
}
