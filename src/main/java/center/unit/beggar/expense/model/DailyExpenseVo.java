package center.unit.beggar.expense.model;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Value
public class DailyExpenseVo {
    /**
     * 날짜
     */
    LocalDate referenceDate;
    /**
     * 합계금액
     */
    BigDecimal totalAmount;
    /**
     * 지출 목록
     */
    List<ExpenseDetailVo> expenseDetailVoList;
}
