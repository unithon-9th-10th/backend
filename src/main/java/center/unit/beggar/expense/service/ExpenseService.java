package center.unit.beggar.expense.service;

import center.unit.beggar.challenge.model.Challenge;
import center.unit.beggar.challenge.service.ChallengeService;
import center.unit.beggar.exception.ChallengeNotFoundException;
import center.unit.beggar.exception.ExpenseNotFoundException;
import center.unit.beggar.exception.MemberNotFoundException;
import center.unit.beggar.exception.UnauthorizedRequestException;
import center.unit.beggar.expense.dto.request.ExpenseAddRequest;
import center.unit.beggar.expense.dto.request.ExpenseEditRequest;
import center.unit.beggar.expense.model.Expense;
import center.unit.beggar.expense.model.ExpenseType;
import center.unit.beggar.expense.repository.ExpenseRepository;
import center.unit.beggar.member.model.Member;
import center.unit.beggar.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final MemberRepository memberRepository;
    private final ChallengeService challengeService;

    @Transactional
    public Expense add(Long memberId, ExpenseAddRequest expenseAddRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Challenge challenge = challengeService.getRunningChallenge(memberId).orElseThrow(ChallengeNotFoundException::new);

        ExpenseType expenseType = expenseAddRequest.getExpenseType();
        if (expenseType == null) {
            expenseType = ExpenseType.FOOD;
        }
        Expense expense = Expense.builder()
                .member(member)
                .challenge(challenge)
                .content(expenseAddRequest.getContent())
                .expenseType(expenseType)
                .amount(expenseAddRequest.getAmount())
                .build();
        return expenseRepository.save(expense);
    }

    @Transactional
    public Expense edit(Long memberId, Long expenseId, ExpenseEditRequest requestDto) {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(ExpenseNotFoundException::new);

        // 수정 권한이 없는 경우
        if (!expense.getMember().getMemberId().equals(memberId)) {
            throw new UnauthorizedRequestException();
        }

        expense.edit(
            requestDto.getContent(),
            requestDto.getAmount(),
            requestDto.getExpenseType()
        );

        return expense;
    }
}
