package codenames;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Isaac Lo
 *
 */

public interface PlayerRepository extends JpaRepository<Player, Long> {
	
	/**
	 * get player by ID
	 * @param id
	 * @return
	 */
    Player findById(int id);

    /**
     * delete player by ID
     * @param id
     */
    @Transactional
    void deleteById(int id);
}