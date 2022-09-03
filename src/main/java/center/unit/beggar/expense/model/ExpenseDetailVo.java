package center.unit.beggar.expense.model;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class ExpenseDetailVo {
    /**
     * 금액
     */
    BigDecimal amount;
    /**
     * 종류
     */
    ExpenseType expenseType;
    /**
     * 메모
     */
    String content;
    /**
     * 상거지 개수
     */
    Integer higherCount;
    /**
     * 하거지 개수
     */
    Integer lowerCount;
    /**
     * 지출 일자
     */
    LocalDate referenceDate;
}
