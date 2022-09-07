package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
    	System.out.println("test");
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
    	System.out.println("test /name");
        return "Hello and welcome to COMS 309: " + name;
    }
    
    @GetMapping("/error")
    public String errorpage() {
    	System.out.println("test error");
        return "there was an error";
    }
}
