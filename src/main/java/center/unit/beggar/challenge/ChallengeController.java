package center.unit.beggar.challenge;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import center.unit.beggar.challenge.dto.ChallengeResponse;
import center.unit.beggar.challenge.dto.request.ChallengePostDto;
import center.unit.beggar.challenge.model.Challenge;
import center.unit.beggar.challenge.service.ChallengeService;
import center.unit.beggar.dto.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/challenges")
@RequiredArgsConstructor
@RestController
public class ChallengeController {

	private final ChallengeService challengeService;

	@PostMapping
	public ApiResponse<ChallengeResponse> createChallenge(
		@RequestBody ChallengePostDto requestDto
	) {
		Challenge challenge = challengeService.saveChallengeByRequestDto(requestDto);

		ChallengeResponse response = ChallengeResponse.builder()
			.challengeId(challenge.getChallengeId())
			.build();

		return ApiResponse.success(response);
	}

}
