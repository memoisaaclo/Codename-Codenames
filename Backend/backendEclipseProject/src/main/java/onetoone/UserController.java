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
    public @ResponseBody String createNewAccountRegister(@RequestBody User usr) {
		if(Main.userRepo.findByusername(usr.getUsername()) != null){
			return "username already exists";	
		}
	
		Main.userRepo.save(usr);
		return "success: " + usr.getUsername() + " " + usr.getPassword() + " ";
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/login")
    public @ResponseBody String loginToAccount(@RequestBody User usr) {
		if(Main.userRepo.findByusername(usr.getUsername()) != null && Main.userRepo.findByusername(usr.getUsername()).getPassword().equals(usr.getPassword())){
			return "success";
		}
		
        return "login failure";
    }
	
	
    @GetMapping("/errorMessage")
    public @ResponseBody String errorMessage() {
        return "This does not work.";
    }

}
