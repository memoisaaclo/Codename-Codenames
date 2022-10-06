package coms309.roundTrip.test.repository;

import coms309.roundTrip.test.model.Trivia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TriviaRepository extends JpaRepository<Trivia, Long> {
}
