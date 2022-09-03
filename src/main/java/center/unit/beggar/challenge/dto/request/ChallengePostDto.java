package center.unit.beggar.challenge.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengePostDto {
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer challengeDays;
	private BigDecimal amount;
}
