package codenames;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Isaac Lo
 *
 */

public interface GameRepository extends JpaRepository<Game, Long> {
	/**
	 * get game by ID
	 * @param id of game
	 * @return game
	 */
    Game findById(int id);

    /**
     * delete game by ID
     * @param id of game
     */
    @Transactional
    void deleteById(int id);

    /**
     * get by game by lobby name
     * @param gameLobbyName of lobby
     * @return game
     */
    Game findBygameLobbyName(String gameLobbyName);
}
