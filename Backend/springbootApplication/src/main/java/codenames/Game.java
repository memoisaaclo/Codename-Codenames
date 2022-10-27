package codenames;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Game object that represents one instance of a code names board game.
 * Each individual game can be seen as a "Lobby" in certain screens.
 */
@Entity
public class Game implements Serializable {
    /*
     * Field variables *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "clues")
    private String clues = "";

    @Column(name = "moves")
    private String moves;

    @Column(name = "gameLobbyName", unique = true)
    private String gameLobbyName;

    @OneToMany(orphanRemoval = false, fetch = FetchType.EAGER)
    private List<Player> players = new ArrayList<Player>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "game_cards",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private Set<Card> cards = new LinkedHashSet<>();

    /*
     * Constructors *
     */
    public Game() {
    }

    public Game(String gameLobbyName) {
        this.gameLobbyName = gameLobbyName;
        
        generateWordList();
        
    }
    
    private void generateWordList() {
    	
    	List<Card> allCards = Main.cardRepo.findAll();
    	Random rand = new Random();
    	Card add;
    	
    	for(int i = 0; i < 25; i++) {
    		add = allCards.get(rand.nextInt(0, allCards.size()));
    		allCards.remove(add);
    		cards.add(add);
    	}
    	Main.gameRepo.save(this);
    }

    /*
     * Special methods *
     */
    /**
     * Add clue to list of clues
     * Clue should be in the specified form:
     * {one word clue}{#of cards that it applies to}
     * @param clue
     */
    public void addClue(String clue) {
        this.clues += " " +  clue.strip();
    }

    public void addPlayer(Player player) { this.players.add(player); }

    /*
     * Getters and setters *
     */
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMoves() { return moves; }
    public void setMoves(String moves) { this.moves = moves; }
    public Set<Card> getCards() { return cards; }
    public void setCards(Set<Card> cards) { this.cards = cards; }
    public String getGameLobbyName() { return gameLobbyName; }
    public void setGameLobbyName(String gameLobbyName) { this.gameLobbyName = gameLobbyName; }
    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }
    public String getClues() { return clues; }
    public void setClues(String clues) { this.clues = clues; }
    public Lobby getLobby() {
    	return new Lobby();
    }
    
    class Lobby {
        private String lobbyName;
        private int numPlayers;

        Lobby() {
            this.numPlayers = players.size();
            this.lobbyName = gameLobbyName;
        }

        public String getLobbyName() {
            return lobbyName;
        }

        public int getNumPlayers() {
            return numPlayers;
        }
    }
}