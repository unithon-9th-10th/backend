package center.unit.beggar.comment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import center.unit.beggar.comment.dto.request.CommentDeleteDto;
import center.unit.beggar.comment.dto.request.CommentPostDto;
import center.unit.beggar.comment.model.Comment;
import center.unit.beggar.comment.repository.CommentRepository;
import center.unit.beggar.exception.CommentNotFoundException;
import center.unit.beggar.expense.model.Expense;
import center.unit.beggar.expense.repository.ExpenseRepository;
import center.unit.beggar.member.model.Member;
import center.unit.beggar.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final MemberRepository memberRepository;

	private final ExpenseRepository expenseRepository;

	@Transactional
	public void deleteComment(Long memberId, CommentDeleteDto requestDto) {
		Comment comment = commentRepository.findByMember_MemberIdAndExpense_ExpenseId(
				memberId,
				requestDto.getExpenseId()
			).orElseThrow(CommentNotFoundException::new);

		commentRepository.delete(comment);
	}

	@Transactional
	//TODO
	public Comment saveComment(Long memberId, CommentPostDto requestDto) {
		Optional<Comment> memberComment = commentRepository.findByMember_MemberIdAndExpense_ExpenseId(
			memberId, requestDto.getExpenseId()
		);

		if (memberComment.isPresent()) {
			commentRepository.delete(memberComment.get());
		}

		Optional<Member> member = memberRepository.findById(memberId);
		Optional<Expense> expense = expenseRepository.findById(requestDto.getExpenseId());

		Comment comment = Comment.builder()
			.beggarType(requestDto.getBeggarType())
			.member(member.get())
			.expense(expense.get())
			.build();

		return commentRepository.save(comment);
	}

	public List<Comment> getComments(Long expenseId) {
		return commentRepository.findByExpense_expenseId(expenseId);
	}
}
