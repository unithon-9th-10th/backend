package center.unit.beggar.expense.repository;

import java.util.List;

import center.unit.beggar.expense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	List<Expense> findByMember_memberIdAndChallenge_challengeId(Long memberId, Long challengeId);
}
