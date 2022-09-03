package center.unit.beggar.challenge.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChallengeRankResponse {
	private int leftDays;
	private List<ChallengeMemberInfoVo> rankingList;

	@Builder
	public ChallengeRankResponse(int leftDays, List<ChallengeMemberInfoVo> rankingList) {
		this.leftDays = leftDays;
		this.rankingList = rankingList;
	}
}
