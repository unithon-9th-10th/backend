package center.unit.beggar.comment.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import center.unit.beggar.expense.model.Expense;
import center.unit.beggar.member.model.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@Enumerated(EnumType.STRING)
	private BeggarType beggarType;

	@OneToOne
	@JoinColumn(name = "memberId")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "expenseId")
	private Expense expense;

	private String content;
	private Integer beggarPoint;

	@Builder
	public Comment(BeggarType beggarType, Member member, Expense expense, String content, Integer beggarPoint) {
		this.beggarType = beggarType;
		this.member = member;
		this.expense = expense;
		this.content = content;
		this.beggarPoint = beggarPoint;
	}
}
