package codenames;

/**
 * Isaaclo
 */
public class Game implements Serializable {
    private String word;

    private boolean revealed;

    public Game() {
    }

    public Game(String word, boolean revealed) {
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