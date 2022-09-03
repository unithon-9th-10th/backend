package center.unit.beggar.expense.dto.request;

import center.unit.beggar.expense.model.ExpenseType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseAddRequest {
    private ExpenseType expenseType;
    private BigDecimal amount;
    private String content;
    private LocalDate referenceDate;
}
