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
	 * @param id
	 * @return
	 */
    Game findById(int id);

    /**
     * delete game by ID
     * @param id
     */
    @Transactional
    void deleteById(int id);

    /**
     * get by game by ID
     * @param gameLobbyName
     * @return
     */
    Game findBygameLobbyName(String gameLobbyName);
}
