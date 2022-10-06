package onetoone;

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
	
		Main.userRepo.save(usr);	// if username does not already exist, save the account to the database
		usr.addLogin();
		return "{\"message\":\"success\"}";
    }

	@RequestMapping(method = RequestMethod.POST, path = "/login")
    public @ResponseBody String loginToAccountPost(@RequestBody User usr) {	// creates user object off of json body
		if(Main.userRepo.findByusername(usr.getUsername()) != null && Main.userRepo.findByusername(usr.getUsername()).getPassword().equals(usr.getPassword())){
			usr.addLogin();
			return "{\"message\":\"success\"}";	// checks if account exists and password is correct
		}
		
        return "{\"message\":\"Incorrect Credentials\"}";
    }
	
	@RequestMapping(method = RequestMethod.POST, path = "/users/{username}/playerID")
    public @ResponseBody String getAttachedPlayer(@PathVariable String username) {	
		
        return "{\"message\":\"Not Implemented\"}";
    }
	
	@RequestMapping(method = RequestMethod.POST, path = "/users/{username}/{playerID}")
    public @ResponseBody String setAttachedPlayer(@PathVariable String username, @PathVariable int playerID) {	
		
		
        return "{\"message\":\"Not Implemented\"}";
    }

}
