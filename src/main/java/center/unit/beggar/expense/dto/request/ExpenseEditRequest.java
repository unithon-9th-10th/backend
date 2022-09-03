package center.unit.beggar.expense.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import center.unit.beggar.expense.model.ExpenseType;
import lombok.Data;

@Data
public class ExpenseEditRequest {
    private ExpenseType expenseType;
    private BigDecimal amount;
    private String content;
    private LocalDate referenceDate;
}
