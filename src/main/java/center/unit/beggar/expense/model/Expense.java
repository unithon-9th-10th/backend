package center.unit.beggar.expense.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import center.unit.beggar.common.BaseTimeEntity;
import center.unit.beggar.member.model.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Expense extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long expenseId;

	@ManyToOne
	@JoinColumn(name = "memberId")
	private Member member;

	private BigDecimal amount;
	private String content;

	@Enumerated(EnumType.STRING)
	private ExpenseType expenseType;



}
