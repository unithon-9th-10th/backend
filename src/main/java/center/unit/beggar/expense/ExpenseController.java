package center.unit.beggar.expense;

import center.unit.beggar.dto.ApiResponse;
import center.unit.beggar.expense.dto.request.ExpenseAddRequest;
import center.unit.beggar.expense.dto.response.ExpenseResponse;
import center.unit.beggar.expense.model.Expense;
import center.unit.beggar.expense.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    public ApiResponse<ExpenseResponse> add(
            @RequestHeader("X-BEGGAR-MEMBER-ID") Long memberId,
            @RequestBody ExpenseAddRequest expenseAddRequest
    ) {
        Expense expense = expenseService.add(memberId, expenseAddRequest);
        ExpenseResponse expenseResponse = ExpenseResponse.builder()
                .expenseId(expense.getExpenseId())
                .challengeId(expense.getChallenge().getChallengeId())
                .memberId(expense.getMember().getMemberId())
                .expenseType(expense.getExpenseType())
                .content(expense.getContent())
                .build();
        return ApiResponse.success(expenseResponse);
    }


}
