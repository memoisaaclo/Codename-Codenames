package codenames;

import java.io.Serializable;

/**
 * Isaaclo
 */
public class Card implements Serializable {
    private String word;

    private boolean revealed;

    public Card() {
    }

    public Card(String word, boolean revealed) {
        this.word = word;
        this.revealed = revealed;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}