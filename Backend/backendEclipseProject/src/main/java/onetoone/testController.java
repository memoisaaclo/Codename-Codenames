package onetoone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @GetMapping("/")
    public String homeMessage() {
        return "This works really well.";
    }

    @GetMapping("/errorMessage")
    public String errorMessage() {
        return "This does not work.";
    }
}
