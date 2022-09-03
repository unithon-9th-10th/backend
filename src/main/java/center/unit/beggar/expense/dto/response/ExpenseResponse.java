package center.unit.beggar.expense.dto.response;

import center.unit.beggar.expense.model.ExpenseType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExpenseResponse {
    Long expenseId;
    Long challengeId;
    Long memberId;
    ExpenseType expenseType;
    String content;
}
