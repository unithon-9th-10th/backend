package center.unit.beggar.challenge;

import center.unit.beggar.challenge.model.ChallengeDetailVo;
import center.unit.beggar.challenge.service.ChallengeDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/challenge")
@RequiredArgsConstructor
public class ChallengeDetailController {
    private final ChallengeDetailService challengeDetailService;
    @GetMapping
    public ChallengeDetailVo getChallengeDetail(
            @RequestHeader("X-BEGGAR-MEMBER-ID") Long memberId
    ) {
        return challengeDetailService.getChallengeDetailInfo(memberId);
    }
}
