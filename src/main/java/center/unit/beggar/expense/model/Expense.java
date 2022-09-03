package center.unit.beggar.expense.model;

import center.unit.beggar.challenge.model.Challenge;
import center.unit.beggar.common.BaseTimeEntity;
import center.unit.beggar.member.model.Member;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Expense extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "challengeId")
    private Challenge challenge;

    private BigDecimal amount;
    private String content;

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    public void edit(String content, BigDecimal amount, ExpenseType expenseType) {
        this.content = content;
        this.amount = amount;
        this.expenseType = expenseType;
    }

}
