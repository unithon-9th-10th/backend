package center.unit.beggar.challenge.dto.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChallengeMemberInfoVo implements Comparable<ChallengeMemberInfoVo> {
	private Long challengeMemberId;
	private String memberNickname;
	private int rank;
	private BigDecimal usedAmount;
	private BigDecimal remainAmount;
	private int beggarPoint;

	@Builder
	public ChallengeMemberInfoVo(Long challengeMemberId, String memberNickname, int rank, BigDecimal usedAmount,
		BigDecimal remainAmount, int beggarPoint) {
		this.challengeMemberId = challengeMemberId;
		this.memberNickname = memberNickname;
		this.usedAmount = usedAmount;
		this.remainAmount = remainAmount;
		this.beggarPoint = beggarPoint;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public int compareTo(ChallengeMemberInfoVo o) {
		return usedAmount.compareTo(o.remainAmount);
	}
}
