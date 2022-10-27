package codenames;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Isaaclo
 */
@Entity
public class Card implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "word")
    private String word;

    public Long getId() {
        return id;
    }

    public Card(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}