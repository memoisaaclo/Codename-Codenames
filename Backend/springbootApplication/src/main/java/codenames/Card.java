package codenames;

import java.io.Serializable;

/**
 * Isaaclo
 */
public class Card implements Serializable {
    private String word;

    public Card() {
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