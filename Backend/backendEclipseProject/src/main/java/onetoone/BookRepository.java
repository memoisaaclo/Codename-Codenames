package onetoone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends JpaRepository<Book, Long> {

	Book findById(int id);
	
	@Transactional
	void deleteById(int id);
	
	
}
