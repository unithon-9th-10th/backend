package center.unit.beggar.challenge.dto;

import center.unit.beggar.challenge.model.Challenge;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChallengeResponse {
	private Long challengeId;

	@Builder
	public ChallengeResponse(Long challengeId) {
		this.challengeId = challengeId;
	}

	public static ChallengeResponse from(Challenge challenge) {
		return ChallengeResponse.builder()
				.challengeId(challenge.getChallengeId())
				.build();
	}
}
