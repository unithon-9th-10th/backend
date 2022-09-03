package center.unit.beggar.challenge.service;

import center.unit.beggar.challenge.dto.response.ChallengeMemberInfoVo;
import center.unit.beggar.challenge.model.Challenge;
import center.unit.beggar.challenge.model.ChallengeDetailVo;
import center.unit.beggar.exception.ChallengeNotFoundException;
import center.unit.beggar.expense.model.DailyExpenseVo;
import center.unit.beggar.expense.model.Expense;
import center.unit.beggar.expense.model.ExpenseDetailVo;
import center.unit.beggar.expense.service.ExpenseService;
import center.unit.beggar.member.model.Member;
import center.unit.beggar.member.model.MemberChallenge;
import center.unit.beggar.member.model.MemberDetailVo;
import center.unit.beggar.member.repository.MemberChallengeRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeDetailService {
	private final ChallengeService challengeService;
	private final MemberChallengeRepository memberChallengeRepository;
	private final ExpenseService expenseService;

	public ChallengeDetailVo getChallengeDetailInfo(Long memberId) {
		Challenge challenge = challengeService.getRunningChallenge(memberId)
			.orElseThrow(ChallengeNotFoundException::new);

		// 멤버 목록 조회
		List<MemberChallenge> memberChallenges
			= memberChallengeRepository.findByChallenge_challengeId(challenge.getChallengeId());
		List<Member> members = memberChallenges.stream().map(MemberChallenge::getMember).collect(Collectors.toList());

		Map<Long, ChallengeMemberInfoVo> memberInfoVoMap = challengeService.getChallengeMemberInfoVos(
				challenge.getChallengeId()).stream()
			.collect(Collectors.toMap(
				ChallengeMemberInfoVo::getMemberId,
				it -> it
			));

		List<MemberDetailVo> memberDetailVoList = new ArrayList<>();

		// 지출 목록 조회 (코멘트까지 조회)
		for (Member member : members) {
			List<Expense> expenses = expenseService.getExpenses(member.getMemberId(), challenge.getChallengeId());

            // startDate ~ min(now, endDate)
            LocalDate from = challenge.getStartDate();
            LocalDate to = LocalDate.now();
            if (to.isAfter(challenge.getEndDate())) {
                to = challenge.getEndDate();
            }
            Map<LocalDate, List<Expense>> map = new HashMap<>();
            LocalDate current = from;
            while (!current.isAfter(to)) {
                // 날짜 키 생성
                LocalDate referenceDate = current;
                List<Expense> dailyExpenses = expenses.stream()
                        .filter(it -> it.getReferenceDate().equals(referenceDate))
                        .collect(Collectors.toList());
                map.put(current, dailyExpenses);
                current = current.plusDays(1L);
            }

			List<DailyExpenseVo> dailyExpenseVoList = new ArrayList<>();
			map.forEach((key, value) -> {
				// comment 조회
				List<ExpenseDetailVo> expenseDetailVoList = value.stream()
					.map(it -> expenseService.getExpenseDetail(it.getExpenseId()))
					.collect(Collectors.toList());

				BigDecimal sumAmount = expenseDetailVoList.stream()
					.map(ExpenseDetailVo::getAmount)
					.reduce(BigDecimal.ZERO, BigDecimal::add);

				DailyExpenseVo dailyExpenseVo = new DailyExpenseVo(
					key,
					sumAmount,
					expenseDetailVoList
				);

				dailyExpenseVoList.add(dailyExpenseVo);
			});
			// 날짜 역순 정렬
			dailyExpenseVoList.sort(Comparator.comparing(
				DailyExpenseVo::getReferenceDate,
				Comparator.nullsLast(Comparator.reverseOrder()))
			);

			ChallengeMemberInfoVo challengeMemberInfoVo = memberInfoVoMap.get(member.getMemberId());

			// FIXME: ranking
			MemberDetailVo memberDetailVo = new MemberDetailVo(
				challengeMemberInfoVo.getRank(),
				challengeMemberInfoVo.getLimitAmount(),
				challengeMemberInfoVo.getUsedAmount(),
				challengeMemberInfoVo.getRemainAmount(),
				challengeMemberInfoVo.getBeggarPoint(),
				challengeMemberInfoVo.getMemberNickname(),
				dailyExpenseVoList
			);

			memberDetailVoList.add(memberDetailVo);
		}

		// 지출 목록 집계
		return new ChallengeDetailVo(memberDetailVoList);
	}
}
