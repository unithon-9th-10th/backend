package center.unit.beggar.expense.repository;

import center.unit.beggar.expense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByMember_memberIdAndChallenge_challengeId(Long memberId, Long challengeId);
}
