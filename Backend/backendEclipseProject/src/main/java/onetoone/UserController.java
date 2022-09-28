package onetoone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/login/register/")
    public String createNewAccountRegister(@RequestBody String newUsername, @RequestBody String newPassword) {
		User newUser = new User(newUsername, newPassword);
		System.out.println(newUsername + " " + newPassword);
        return "success";
    }
	
	@GetMapping("/login/")
    public String loginToAccount(@RequestBody String newUsername, @RequestBody String newPassword) {
		
        return "success";
    }
	
	
    @GetMapping("/errorMessage")
    public String errorMessage() {
        return "This does not work.";
    }

}
