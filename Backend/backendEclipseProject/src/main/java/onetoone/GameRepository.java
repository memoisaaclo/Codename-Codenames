package onetoone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Isaac Lo
 *
 */

public interface GameRepository extends JpaRepository<Player, Long> {
    Player findById(int id);

    @Transactional
    void deleteById(int id);
}
