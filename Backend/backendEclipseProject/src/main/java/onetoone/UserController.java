package onetoone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@RequestMapping(method = RequestMethod.POST, path = "/login/register")
    public @ResponseBody String createNewAccountRegister(@RequestBody User usr) {	// creates user object off of json body
		if(Main.userRepo.findByusername(usr.getUsername()) != null){
			return "username already exists";	
		}
	
		Main.userRepo.save(usr);	// if username does not already exist, save the account to the database
		return "success";
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/login")
    public @ResponseBody String loginToAccount(@RequestBody User usr) {	// creates user object off of json body
		if(Main.userRepo.findByusername(usr.getUsername()) != null && Main.userRepo.findByusername(usr.getUsername()).getPassword().equals(usr.getPassword())){
			return "success";	// checks if account exists and password is correct
		}
		
        return "login failure";
    }
	
	
    @GetMapping("/errorMessage")
    public @ResponseBody String errorMessage() {
        return "This does not work.";
    }

}
