package onetoone;
/***
 * Author: Isaac Lo
 */

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Hashtable;

enum Role {
    Spymaster,
    Operative
}

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    //private ArrayList<Card> cards;

    private String moves;

    private Hashtable<Long, Role> playerRoles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public ArrayList<Card> getCards() {
//        return cards;
//    }

//    public void setCards(ArrayList<Card> cards) {
//        this.cards = cards;
//    }

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