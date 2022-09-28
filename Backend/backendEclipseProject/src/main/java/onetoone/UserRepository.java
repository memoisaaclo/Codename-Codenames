package onetoone;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

	User findById(int id);
	
	@Transactional
	void deleteById(int id);
	
	User findByusername(String username);
	
}
