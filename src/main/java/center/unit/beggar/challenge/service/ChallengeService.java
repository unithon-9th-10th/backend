package center.unit.beggar.challenge.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import center.unit.beggar.challenge.dto.request.ChallengePostDto;
import center.unit.beggar.challenge.dto.response.ChallengeMemberInfoVo;
import center.unit.beggar.challenge.dto.response.ChallengeRankResponse;
import center.unit.beggar.challenge.dto.response.ChallengeResultResponse;
import center.unit.beggar.challenge.model.Challenge;
import center.unit.beggar.challenge.repository.ChallengeRepository;
import center.unit.beggar.comment.model.BeggarType;
import center.unit.beggar.comment.model.Comment;
import center.unit.beggar.comment.repository.CommentRepository;
import center.unit.beggar.exception.ChallengeNotFoundException;
import center.unit.beggar.exception.MemberNotFoundException;
import center.unit.beggar.expense.model.Expense;
import center.unit.beggar.expense.repository.ExpenseRepository;
import center.unit.beggar.member.model.Member;
import center.unit.beggar.member.model.MemberChallenge;
import center.unit.beggar.member.model.MemberStatus;
import center.unit.beggar.member.repository.MemberChallengeRepository;
import center.unit.beggar.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChallengeService {
	private final ChallengeRepository challengeRepository;
	private final MemberChallengeRepository memberChallengeRepository;
	private final MemberRepository memberRepository;

	private final ExpenseRepository expenseRepository;
	private final CommentRepository commentRepository;

	public MemberStatus resolveMemberStatus(Long memberId) {
		List<Long> challengeIds = memberChallengeRepository.findByMember_memberId(memberId)
			.stream()
			.map(MemberChallenge::getChallenge)
			.map(Challenge::getChallengeId).collect(Collectors.toList());
		List<Challenge> challenges = challengeRepository.findAllById(challengeIds);

		if (CollectionUtils.isEmpty(challenges)) {
			return MemberStatus.READY;
		}
		LocalDate today = LocalDate.now();
		boolean hasRunningChallenge = challenges.stream().anyMatch(
			it -> !(it.getStartDate().isBefore(today) || it.getEndDate().isAfter(today))
		);
		return hasRunningChallenge ? MemberStatus.RUNNING : MemberStatus.FINISHED;
	}

	public Challenge saveChallengeByRequestDto(ChallengePostDto requestDto) {
		Challenge challenge = Challenge.builder()
			.title(requestDto.getTitle())
			.startDate(requestDto.getStartDate())
			.endDate(requestDto.getEndDate())
			.challengeDays(requestDto.getChallengeDays())
			.amount(requestDto.getAmount())
			.build();

		challengeRepository.save(challenge);
		return challenge;
	}

	public Optional<Challenge> getRunningChallenge(Long memberId) {
		List<Long> challengeIds = memberChallengeRepository.findByMember_memberId(memberId)
			.stream()
			.map(MemberChallenge::getChallenge)
			.map(Challenge::getChallengeId).collect(Collectors.toList());
		LocalDate today = LocalDate.now();
		return challengeRepository.findByChallengeIdInAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
			challengeIds,
			today,
			today
		).stream().findFirst();
	}

	@Transactional
	public void addMember(Long memberId, Long challengeId, String nickname) {
		Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
		Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(ChallengeNotFoundException::new);
		Optional<MemberChallenge> memberChallengeOptional = memberChallengeRepository.findByMember_memberIdAndChallenge_challengeId(
			memberId, challengeId);
		if (memberChallengeOptional.isPresent()) {
			return;
		}
		MemberChallenge memberChallenge = MemberChallenge.builder()
			.member(member)
			.challenge(challenge)
			.memberNickname(nickname)
			.build();
		memberChallengeRepository.save(memberChallenge);
	}

	public ChallengeRankResponse getRankList(Long challengeId) {
		Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(ChallengeNotFoundException::new);
		int days = Period.between(LocalDate.now(), challenge.getEndDate()).getDays();

		List<ChallengeMemberInfoVo> challengeMemberInfoVos = getChallengeMemberInfoVos(challengeId);

		return ChallengeRankResponse.builder()
			.leftDays(days)
			.rankingList(challengeMemberInfoVos)
			.build();
	}

	public ChallengeResultResponse getResultList(Long challengeId) {
		Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(ChallengeNotFoundException::new);
		List<ChallengeMemberInfoVo> challengeMemberInfoVos = getChallengeMemberInfoVos(challengeId);
		return ChallengeResultResponse.builder()
			.startDate(challenge.getStartDate())
			.endDate(challenge.getEndDate())
			.challengeDays(challenge.getChallengeDays())
			.amount(challenge.getAmount())
			.rankingList(challengeMemberInfoVos)
			.build();
	}
	public List<ChallengeMemberInfoVo> getChallengeMemberInfoVos(Long challengeId) {
		List<MemberChallenge> memberChallenges = memberChallengeRepository.findByChallenge_challengeId(
			challengeId);

		List<ChallengeMemberInfoVo> infoVos = new ArrayList<>();
		memberChallenges.forEach(
			o -> infoVos.add(getChallengeMemberInfo(o))
		);

		Collections.sort(infoVos);

		int rank = 1;
		for (ChallengeMemberInfoVo infoVo : infoVos) {
			infoVo.setRank(rank);
			rank += 1;
		}

		return infoVos;
	}


	public ChallengeMemberInfoVo getChallengeMemberInfo(MemberChallenge memberChallenge) {
		int beggarPoint = 50;
		List<Expense> expenses = expenseRepository.findByMember_memberIdAndChallenge_challengeId(
			memberChallenge.getMember().getMemberId(),
			memberChallenge.getMemberChallengeId()
		);

		// 사용한 지출 합
		BigDecimal usedAmount = expenses.stream()
			.map(Expense::getAmount)
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		// 남은금액 = 목표 지출 - 사용한 지출
		BigDecimal remainAmount = memberChallenge.getChallenge().getAmount()
			.subtract(usedAmount);

		List<Comment> comments = commentRepository.findByExpense_expenseIdIn(
			expenses.stream()
				.map(Expense::getExpenseId)
				.collect(Collectors.toList())
		);

		int higherCommentCount = (int)comments.stream()
			.filter(c -> c.getBeggarType() == BeggarType.HIGHER)
			.count();

		int lowerCommentCount = comments.size() - higherCommentCount;

		// (긍정 코멘트 개수 - 부정 피드백 개수) * 가중치(10)
		beggarPoint += (higherCommentCount - lowerCommentCount) * 10;

		return ChallengeMemberInfoVo.builder()
			.challengeMemberId(memberChallenge.getMemberChallengeId())
			.memberNickname(memberChallenge.getMemberNickname())
			.usedAmount(usedAmount)
			.remainAmount(remainAmount)
			.beggarPoint(beggarPoint)
			.build();
	}
}
