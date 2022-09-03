package center.unit.beggar.challenge.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengePostDto {
    private String title;
    private Integer challengeDays;
    private BigDecimal amount;
}
