package codenames;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface GameCardRepository extends JpaRepository<GameCard, Long> {
	
	/**
	 * get cardstate by id
	 * @param id
	 * @return
	 */
    GameCard findById(int id);

    /**
     * delete a cardstate by id
     * @param id
     */
    @Transactional
    void deleteById(int id);
}