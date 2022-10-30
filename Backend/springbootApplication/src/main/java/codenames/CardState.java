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
    private int id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)

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

    /*
        Getters and Setters
     */
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
        return id != null && Objects.equals(id, cardState.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
