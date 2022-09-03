package center.unit.beggar.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import center.unit.beggar.comment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	Optional<Comment> findByMember_MemberIdAndExpense_ExpenseId(Long memberId, Long expenseId);
	List<Comment> findByExpense_expenseIdIn(List<Long> expenseIds);
}
