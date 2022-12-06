package codenames;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface GameCardRepository extends JpaRepository<GameCard, Long> {
	
	/**
	 * get cardState by id
	 * @param id of cardState
	 * @return
	 */
    GameCard findById(int id);

    /**
     * delete a cardState by id
     * @param id of cardState
     */
    @Transactional
    void deleteById(int id);
}