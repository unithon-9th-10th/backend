package center.unit.beggar.member.repository;

import center.unit.beggar.member.model.MemberChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberChallengeRepository extends JpaRepository<MemberChallenge, Long> {
    List<MemberChallenge> findByMember_memberId(Long memberId);
}
