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
	 * @param id of user
	 * @return User
	 */
	User findById(int id);
	
	/**
	 * delete a user by ID
	 * @param id of user
	 */
	@Transactional
	void deleteById(int id);
	
	/**
	 * get a user by username
	 * @param username of user
	 * @return User
	 */
	User findByusername(String username);
	
}
