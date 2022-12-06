package codenames;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Ben Kelly
 *
 */
@RestController
public class UserController {

	@SuppressWarnings("unused")
	private final String success = "{\"message\":\"success\"}";
    @SuppressWarnings("unused")
	private final String failure = "{\"message\":\"failure\"}";
    
    /**
     * register a new player
     * @param usr to be registered
     * @return success or failure
     */
	@RequestMapping(method = RequestMethod.POST, path = "/users/register")
    public @ResponseBody String createNewAccountRegister(@RequestBody User usr) {	// creates user object off of json body
		if(Main.userRepo.findByusername(usr.getUsername()) != null){
			return "{\"message\":\"Username already in use\"}";	
		}
		
		usr.addLogin();
		Main.userRepo.save(usr);
		return success;
    }

	/**
	 * log in a player
	 * @param usr to login
	 * @return success or failure
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/users/login")
    public @ResponseBody String loginToAccountPost(@RequestBody User usr) {	// creates user object off of json body
		User usrObj = Main.userRepo.findByusername(usr.getUsername());
		if(usrObj != null && usrObj.validateCredentials(usr)){
			usrObj.addLogin();
			Main.userRepo.save(usrObj);
			return success;	// checks if account exists and password is correct
		}
        return "{\"message\":\"Incorrect Credentials\"}";
    }
	
	/**
	 * get user JSON representation
	 * @param username of user
	 * @return JSON of a user object
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/users/{username}")
    public @ResponseBody User getUserObject(@PathVariable String username) {	// creates user object off of json body
		return Main.userRepo.findByusername(username);
    }
	
	/**
	 * return JSON list of all users
	 * @return all users
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/users/getallusers")
    public @ResponseBody List<User> getallUsers() {	// creates user object off of json body
        return Main.userRepo.findAll();
    }
	
	/**
	 * delete all users from database
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/users/clearusers/75362")
    public void clearUsers() {	// removes all objects
        Main.userRepo.deleteAllInBatch();
	}
	
	/**
	 * delete a specific user
	 * @param username of user
	 * @return success or failure
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/users/removeuser/{username}")
    public String deleteUser(@PathVariable String username) {	// removes all objects
		if(Main.userRepo.findByusername(username) == null) return failure;
		User delete = Main.userRepo.findByusername(username);
		Main.userRepo.delete(delete);
		return success;
	}
	
}
