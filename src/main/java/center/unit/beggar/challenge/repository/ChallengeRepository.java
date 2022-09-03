package center.unit.beggar.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import center.unit.beggar.challenge.model.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

}
