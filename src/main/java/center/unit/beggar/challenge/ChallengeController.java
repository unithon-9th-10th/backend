package center.unit.beggar.challenge;

import java.util.List;

import center.unit.beggar.challenge.dto.ChallengeResponse;
import center.unit.beggar.challenge.dto.request.AddMemberRequest;
import center.unit.beggar.challenge.dto.request.ChallengePostDto;
import center.unit.beggar.challenge.dto.response.ChallengeMemberInfoVo;
import center.unit.beggar.challenge.dto.response.ChallengeRankResponse;
import center.unit.beggar.challenge.dto.response.ChallengeResultResponse;
import center.unit.beggar.challenge.model.Challenge;
import center.unit.beggar.challenge.service.ChallengeService;
import center.unit.beggar.dto.ApiResponse;
import center.unit.beggar.exception.ChallengeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{challengeId}/members/{memberId}")
    public ApiResponse<ChallengeResponse> addMember(
        @PathVariable Long challengeId,
        @PathVariable Long memberId,
        @RequestBody AddMemberRequest addMemberRequest
    ) {
        challengeService.addMember(memberId, challengeId, addMemberRequest.getNickname());
        Challenge challenge = challengeService.getRunningChallenge(memberId)
            .orElseThrow(ChallengeNotFoundException::new);
        return ApiResponse.success(ChallengeResponse.from(challenge));
    }

    @GetMapping("/{challengeId}/ranking")
    public ApiResponse<ChallengeRankResponse> getRank(
        @PathVariable("challengeId") Long challengeId
    ) {
        ChallengeRankResponse response = challengeService.getRankList(challengeId);
        return ApiResponse.success(response);
    }

    @GetMapping("/{challengeId}/results")
    public ApiResponse<ChallengeResultResponse> getResult(
        @PathVariable("challengeId") Long challengeId
    ) {
        ChallengeResultResponse response = challengeService.getResultList(challengeId);
        return ApiResponse.success(response);
    }
}
