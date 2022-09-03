package center.unit.beggar.challenge.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;

@Getter
public class ChallengePostDto {
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer challengeDays;
	private BigDecimal amount;
}
