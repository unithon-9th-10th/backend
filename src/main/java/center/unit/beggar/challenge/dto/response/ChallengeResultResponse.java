package center.unit.beggar.challenge.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChallengeResultResponse {
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer challengeDays;
	private BigDecimal amount;
	private List<ChallengeMemberInfoVo> rankingList;

	@Builder
	public ChallengeResultResponse(LocalDate startDate, LocalDate endDate, Integer challengeDays, BigDecimal amount,
		List<ChallengeMemberInfoVo> rankingList) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.challengeDays = challengeDays;
		this.amount = amount;
		this.rankingList = rankingList;
	}
}
