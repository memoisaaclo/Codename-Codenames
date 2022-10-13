package onetoone;

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

	@RequestMapping(method = RequestMethod.POST, path = "/login/register")
    public @ResponseBody String createNewAccountRegister(@RequestBody User usr) {	// creates user object off of json body
		if(Main.userRepo.findByusername(usr.getUsername()) != null){
			return "{\"message\":\"Username already in use\"}";	
		}
	
			// if username does not already exist, save the account to the database
		
		usr.addLogin();
		Main.userRepo.save(usr);
		return "{\"message\":\"success\"}";
    }

	@RequestMapping(method = RequestMethod.POST, path = "/login")
    public @ResponseBody String loginToAccountPost(@RequestBody User usr) {	// creates user object off of json body
		if(Main.userRepo.findByusername(usr.getUsername()) != null && Main.userRepo.findByusername(usr.getUsername()).getPassword().equals(usr.getPassword())){
			User usrObj = Main.userRepo.findByusername(usr.getUsername());
			usrObj.addLogin();
			Main.userRepo.save(usrObj);
			return "{\"message\":\"success\"}";	// checks if account exists and password is correct
		}
		
        return "{\"message\":\"Incorrect Credentials\"}";
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/users/{username}/playerID")
    public @ResponseBody String getAttachedPlayer(@PathVariable String username) {	
		
		Main.userRepo.findByusername(username);
		
        return "{\"message\":\"\"}";
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/users/{username}/{playerID}")
    public @ResponseBody String setAttachedPlayer(@PathVariable String username, @PathVariable int playerID) {	
		
		
        return "{\"message\":\"Not Implemented\"}";
    }

	@RequestMapping(method = RequestMethod.GET, path = "/getallusers")
    public @ResponseBody List<User> getallUsers() {	// creates user object off of json body
        return Main.userRepo.findAll();
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/clearUsers/75362")
    public void clearUsers() {	// creates user object off of json body
        Main.userRepo.deleteAllInBatch();
	}
    
}
