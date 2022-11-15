package codenames;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Ben Kelly
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * get a user by id
	 * @param id
	 * @return
	 */
	User findById(int id);
	
	/**
	 * delete a user by ID
	 * @param id
	 */
	@Transactional
	void deleteById(int id);
	
	/**
	 * get a user by username
	 * @param username
	 * @return
	 */
	User findByusername(String username);
	
}
