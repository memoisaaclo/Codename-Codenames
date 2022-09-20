package models;

import javax.persistence.GeneratedValue;

import org.hibernate.annotations.Entity;
import org.springframework.data.annotation.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    // standard constructors

    // standard getters and setters
}