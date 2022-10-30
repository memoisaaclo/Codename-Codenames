package codenames;

import javax.persistence.*;

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
}
