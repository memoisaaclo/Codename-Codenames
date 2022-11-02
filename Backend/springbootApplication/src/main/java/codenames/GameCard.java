package codenames;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * codenames
 * 10/29/22
 * isaaclo
 **/

@Entity
public class GameCard implements Comparable<GameCard>, Serializable {
        /* Field Variables */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    private int gamePosition;

    private String word;

    private boolean revealed;

    private Color color;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

        /* Constructors */
    public GameCard() { color = Color.GREY; }

    public GameCard(int gamePosition, Color color) {
        this.gamePosition = gamePosition;
        this.color = color;
    }

    public GameCard(int gamePosition, Color color, Game game) {
        this.gamePosition = gamePosition;
        this.color = color;
        this.game = game;
    }

    public GameCard(int gamePosition, String word, Color color, Game game) {
        this.gamePosition = gamePosition;
        this.word = word;
        this.color = color;
        this.game = game;
    }

    /* Getters and Setters */
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getGamePosition() { return gamePosition; }

    public void setGamePosition(int gamePosition) { this.gamePosition = gamePosition; }

    public String getWord() { return word; }

    public void setWord(String word) { this.word = word; }

    public GameCard(Color color) { this.color = color; }

    public boolean isRevealed() { return revealed; }

    public void setRevealed(boolean revealed) { this.revealed = revealed; }

    public Color getColor() { return color; }

    public void setColor(Color color) { this.color = color; }

    public Game getGame() { return game; }

    public void setGame(Game game) { this.game = game; }

    public String displayInfo() {
        return "{\"position\": \"" + gamePosition + "\", " +
                "\"color\": \"" + color + "\", " +
                "\"revealed\": \"" + revealed + "\"}";
    }

        /* Other Methods */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GameCard GameCard = (GameCard) o;
        return Objects.equals(id, GameCard.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public int compareTo(GameCard other) { return this.gamePosition - other.gamePosition; }
}
