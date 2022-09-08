package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    private static String default_greeting = "Helloooo";
    private static final String template = "From your friendly neighborhood CS 309 Devs: %s, %s!";
    private final AtomicLong counter = new AtomicLong();

    private static final String template2 = "%s, %s!";

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value= "name", defaultValue = "Alex N (thanks for TA-ing :)") String name) {
	    return new Greeting(counter.incrementAndGet(), String.format(template, default_greeting, name));
    }

    @GetMapping("/changegreeting")
    public Greeting changeGreeting(@RequestParam(value= "greeting", defaultValue = "Hello")
        String greeting, @RequestParam(value = "name", defaultValue = "Alex N (hey dude)") String name)  {
	return new Greeting(counter.incrementAndGet(), String.format(template, greeting, name));
    }
}
