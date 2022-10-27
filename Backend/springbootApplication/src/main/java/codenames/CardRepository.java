package codenames;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Benjamin Kelly
 *
 */

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findById(int id);

    @Transactional
    void deleteById(int id);
    
    Card findByword(String word);
}