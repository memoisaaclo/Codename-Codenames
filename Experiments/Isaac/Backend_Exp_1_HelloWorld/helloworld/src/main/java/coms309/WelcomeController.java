package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }
    
    @GetMapping("/add/{toAdd}")
    public String welcome(@PathVariable List<Integer> toAdd) {
    	int total = 0;
	for (Integer num : toAdd) {
 	    total += num;
	}
	return total.toString();
    }
}
