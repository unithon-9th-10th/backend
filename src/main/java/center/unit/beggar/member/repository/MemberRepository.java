package center.unit.beggar.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import center.unit.beggar.member.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
