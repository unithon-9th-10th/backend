package center.unit.beggar.challenge.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChallengeResponse {
	private Long challengeId;

	@Builder
	public ChallengeResponse(Long challengeId) {
		this.challengeId = challengeId;
	}
}
