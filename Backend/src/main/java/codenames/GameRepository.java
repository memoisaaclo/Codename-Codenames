package codenames;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Isaac Lo
 *
 */

public interface GameRepository extends JpaRepository<Game, Long> {
    Game findById(int id);

    @Transactional
    void deleteById(int id);

    Game findBygameLobbyName(String gameLobbyName);
}