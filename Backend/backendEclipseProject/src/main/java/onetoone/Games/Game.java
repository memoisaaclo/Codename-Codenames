package onetoone.Games;
/***
 * Author: Isaac Lo
 */

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Hashtable;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private ArrayList<Card> cards;

    private String moves;

    private Hashtable<Long, Role> playerRoles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public String getMoves() {
        return moves;
    }

    public void setMoves(String moves) {
        this.moves = moves;
    }

    public Hashtable<Long, Role> getPlayerRoles() {
        return playerRoles;
    }

    public void setPlayerRoles(Hashtable<Long, Role> playerRoles) {
        this.playerRoles = playerRoles;
    }
}