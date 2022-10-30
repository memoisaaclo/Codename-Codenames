package codenames;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CardStateRepository extends JpaRepository<CardState, Long> {
    CardState findById(int id);

    @Transactional
    void deleteById(int id);
}