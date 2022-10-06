package coms309.roundTrip.test.controller;

import coms309.roundTrip.test.model.Trivia;
import coms309.roundTrip.test.repository.TriviaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TriviaController {


    @Autowired
    TriviaRepository triviaRepository;


    @GetMapping("trivia/all")
    List<Trivia> GetAllTrivia(){
        return triviaRepository.findAll();
    }

    @PostMapping("trivia/post/{q}/{a}")
    Trivia PostTriviaByPath(@PathVariable String q, @PathVariable String a){
        Trivia newTrivia = new Trivia();
        newTrivia.setQuestion(q);
        newTrivia.setAnswer(a);
        triviaRepository.save(newTrivia);
        return newTrivia;
    }


    @PostMapping("trivia/post")
    Trivia PostTriviaByPath(@RequestBody Trivia newTrivia){
        triviaRepository.save(newTrivia);
        return newTrivia;
    }


}
