package coms309;

/**
 * Controller used to showcase what happens when an exception is thrown
 *
 * @author Vivek Bengre
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ExceptionController {
    @GetMapping("error")
    @RequestMapping(method = RequestMethod.GET, path = "/oops")
    public String triggerException() {
        return("Robert_Was_HERE HELLO");
        //throw new RuntimeException("ROBERT_WAS_HERE");
    }

}
