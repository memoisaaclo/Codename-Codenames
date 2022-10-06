package onetoone;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import static javax.persistence.CascadeType.REFRESH;

@Entity
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    //private Set<Card> cards;

    @Column(name = "moves")
    private String moves;

    @OneToMany(mappedBy = "game", cascade = REFRESH, orphanRemoval = false)
    private Set<Player> players = new LinkedHashSet<>();

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

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

    @Column(name = "game_lobby_name", unique = true)
    private String gameLobbyName;

    public String getGameLobbyName() {
        return gameLobbyName;
    }

    public void setGameLobbyName(String gameLobbyName) {
        this.gameLobbyName = gameLobbyName;
    }
}