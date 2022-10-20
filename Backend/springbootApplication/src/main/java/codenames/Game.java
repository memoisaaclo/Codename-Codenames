package codenames;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "id")
    @JoinTable(
            name = "game_cards",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private ArrayList<Card> cards = new ArrayList<>();

    @Column(name = "moves")
    private String moves;

    @OneToMany(orphanRemoval = false, fetch = FetchType.EAGER)
    private List<Player> players = new ArrayList<Player>();

    public Game(int id, String gameLobbyName) {
        this.id = id;
        this.gameLobbyName = gameLobbyName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoves() {
        return moves;
    }

    public void setMoves(String moves) {
        this.moves = moves;
    }

    public Game() {
    }

    @Column(name = "gameLobbyName", unique = true)
    private String gameLobbyName;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public String getGameLobbyName() {
        return gameLobbyName;
    }

    public void setGameLobbyName(String gameLobbyName) {
        this.gameLobbyName = gameLobbyName;
    }
}