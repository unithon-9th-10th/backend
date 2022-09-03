package center.unit.beggar.challenge.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import center.unit.beggar.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Challenge extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long challengeId;

	private String title;

	private LocalDate startDate;
	private LocalDate endDate;
	private Integer challengeDays;

	private BigDecimal amount;

	@Builder
	public Challenge(String title, LocalDate startDate, LocalDate endDate, Integer challengeDays, BigDecimal amount) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.challengeDays = challengeDays;
		this.amount = amount;
	}
}
