package center.unit.beggar.member;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import center.unit.beggar.dto.ApiResponse;
import center.unit.beggar.member.dto.response.MemberResponse;
import center.unit.beggar.member.model.Member;
import center.unit.beggar.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@RestController
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	public ApiResponse<MemberResponse> signUp() {
		Member member = memberService.joinMember();

		MemberResponse response = MemberResponse.builder()
			.memberId(member.getMemberId())
			.build();
		return ApiResponse.success(response);
	}


}
