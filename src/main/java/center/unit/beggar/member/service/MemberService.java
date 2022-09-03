package center.unit.beggar.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import center.unit.beggar.member.model.Member;
import center.unit.beggar.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public Member joinMember() {
		Member member = new Member();
		return memberRepository.save(member);
	}

}
