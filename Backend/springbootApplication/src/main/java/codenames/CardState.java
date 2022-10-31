package codenames;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * codenames
 * 10/29/22
 * isaaclo
 **/

@Entity
public class CardState {
    /*
        Field Variables
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    private int gamePosition;

    private boolean revealed;

    private CardColor color;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /*
        Constructors
     */
    public CardState() {
        color = CardColor.GREY;
    }

    public CardState(int gamePosition, CardColor color) {
        this.gamePosition = gamePosition;
        this.color = color;
    }

    public CardState(int gamePosition, CardColor color, Game game) {
        this.gamePosition = gamePosition;
        this.color = color;
        this.game = game;
    }

    /*
        Getters and Setters
     */

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getGamePosition() { return gamePosition; }

    public void setGamePosition(int gamePosition) { this.gamePosition = gamePosition; }

    public CardState(CardColor color) {
        this.color = color;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CardState cardState = (CardState) o;
        return Objects.equals(id, cardState.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
