package onetoone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/login/register/")
    public @ResponseBody String createNewAccountRegister(@RequestParam(value = "newUsername") String newUsername, @RequestParam(value = "newPassword") String newPassword) {
		if(Main.userRepo.findByusername(newUsername) != null){
			return "username already exists";	
		}
	
		Main.userRepo.save(new User(newUsername, newPassword));
		return "success: " + newUsername + " " + newPassword + " ";
    }
	
	@GetMapping("/login/")
    public @ResponseBody String loginToAccount(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
		if(Main.userRepo.findByusername(username) != null && Main.userRepo.findByusername(username).getPassword().equals(password)){
			return "success";
		}
		
        return "login failure";
    }
	
	
    @GetMapping("/errorMessage")
    public @ResponseBody String errorMessage() {
        return "This does not work.";
    }

}
