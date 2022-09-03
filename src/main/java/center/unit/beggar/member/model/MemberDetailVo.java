package center.unit.beggar.member.model;

import center.unit.beggar.expense.model.DailyExpenseVo;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class MemberDetailVo {
    /**
     * 순위 (1,2,3)
     */
    Integer ranking;
    /**
     * 목표금액
     */
    BigDecimal limitAmount;
    /**
     * 사용금액
     */
    BigDecimal usedAmount;
    /**
     * 남은금액
     */
    BigDecimal remainAmount;
    /**
     * 거지력 (0~100)
     */
    Integer memberPoint;
    /**
     * 일별 합계, 시간 내림차순 정렬
     */
    List<DailyExpenseVo> dailyExpenseVoList;
}
