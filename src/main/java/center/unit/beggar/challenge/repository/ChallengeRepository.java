package center.unit.beggar.challenge.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import center.unit.beggar.challenge.model.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByChallengeIdInAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            List<Long> challengeIds,
            LocalDate startDate,
            LocalDate endDate
    );
}
