package center.unit.beggar.member;

import center.unit.beggar.challenge.service.ChallengeService;
import center.unit.beggar.member.dto.response.MemberDetailResponse;
import center.unit.beggar.member.model.MemberStatus;
import org.springframework.web.bind.annotation.*;

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
	private final ChallengeService challengeService;

	@PostMapping("/signup")
	public ApiResponse<MemberResponse> signUp() {
		Member member = memberService.joinMember();

		MemberResponse response = MemberResponse.builder()
			.memberId(member.getMemberId())
			.build();
		return ApiResponse.success(response);
	}

	@GetMapping("/{memberId}")
	public ApiResponse<MemberDetailResponse> getMemberDetail(
			@PathVariable Long memberId
	) {
		MemberStatus memberStatus = challengeService.resolveMemberStatus(memberId);
		return ApiResponse.success(
				MemberDetailResponse.builder()
						.memberId(memberId)
						.status(memberStatus)
						.build()
		);
	}


}
