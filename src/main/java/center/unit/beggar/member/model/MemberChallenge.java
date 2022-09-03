package center.unit.beggar.member.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import center.unit.beggar.challenge.model.Challenge;
import center.unit.beggar.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberChallenge extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberChallengeId;

	@ManyToOne
	@JoinColumn(name = "memberId")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "challengeId")
	private Challenge challenge;

	private String memberNickname;

	@Builder
	public MemberChallenge(Member member, Challenge challenge, String memberNickname) {
		this.member = member;
		this.challenge = challenge;
		this.memberNickname = memberNickname;
	}
}
