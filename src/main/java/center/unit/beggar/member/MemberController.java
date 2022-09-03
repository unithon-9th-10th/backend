package center.unit.beggar.member;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import center.unit.beggar.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@RestController
public class MemberController {

	private final MemberService memberService;


	//TODO : Response 타입 변경
	@PostMapping("/signup")
	public void signUp() {
		Long memberId = memberService.joinMember();
	}


}
