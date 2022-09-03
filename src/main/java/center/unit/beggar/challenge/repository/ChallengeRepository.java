package center.unit.beggar.challenge.repository;

import center.unit.beggar.challenge.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByChallengeIdInAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
            List<Long> challengeIds,
            LocalDate startDate,
            LocalDate endDate
    );
}
