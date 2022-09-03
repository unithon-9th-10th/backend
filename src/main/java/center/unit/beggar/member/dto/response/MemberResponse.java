package center.unit.beggar.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {
	private Long memberId;
	@Builder
	public MemberResponse(Long memberId) {
		this.memberId = memberId;
	}
}
