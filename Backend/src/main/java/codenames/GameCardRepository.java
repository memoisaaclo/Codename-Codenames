package codenames;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface GameCardRepository extends JpaRepository<GameCard, Long> {
    GameCard findById(int id);

    @Transactional
    void deleteById(int id);
}