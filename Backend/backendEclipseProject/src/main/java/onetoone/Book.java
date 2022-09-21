package onetoone;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

}