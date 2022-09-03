package center.unit.beggar.member.repository;

import center.unit.beggar.member.model.MemberChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberChallengeRepository extends JpaRepository<MemberChallenge, Long> {
    List<MemberChallenge> findByMember_memberId(Long memberId);
    Optional<MemberChallenge> findByMember_memberIdAndChallenge_challengeId(Long memberId, Long challengeId);
    List<MemberChallenge> findByChallenge_challengeId(Long challengeId);
}
