package center.unit.beggar.challenge.dto.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChallengeMemberInfoVo implements Comparable<ChallengeMemberInfoVo> {
	private Long memberId;
	private String memberNickname;
	private int rank;
	private BigDecimal usedAmount;
	private BigDecimal remainAmount;
	private int beggarPoint;
	private BigDecimal limitAmount;
	@Builder
	public ChallengeMemberInfoVo(Long memberId, String memberNickname, int rank, BigDecimal usedAmount,
		BigDecimal remainAmount, int beggarPoint) {
		this.memberId = memberId;
		this.memberNickname = memberNickname;
		this.usedAmount = usedAmount;
		this.remainAmount = remainAmount;
		this.beggarPoint = beggarPoint;
		this.limitAmount = remainAmount.add(usedAmount);
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public int compareTo(ChallengeMemberInfoVo o) {
		return usedAmount.compareTo(o.usedAmount);
	}
}
