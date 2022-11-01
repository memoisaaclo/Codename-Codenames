package codenames;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import static codenames.Color.*;

/**
 * Game object that represents one instance of a code names board game.
 * Each individual game can be seen as a "Lobby" in certain screens.
 */
@Entity
public class Game implements Serializable {
        /* Field variables */
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
    private Set<Player> players = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "game_cards",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private Set<Card> cards = new LinkedHashSet<>();

    @OneToMany(mappedBy = "game", orphanRemoval = true)
    private List<GameCard> gameCards = new ArrayList<>();

        /* Constructors */
    public Game() { }

    public Game(String gameLobbyName) { this.gameLobbyName = gameLobbyName; }

        /* Getters and setters */
    public void addPlayer(Player player) { this.players.add(player); }
    public String getMoves() { return moves; }
    public void setMoves(String moves) { this.moves = moves; }
    public Set<Card> getCards() { return cards; }
    public void setCards(Set<Card> cards) { this.cards = cards; }
    public String getGameLobbyName() { return gameLobbyName; }
    public void setGameLobbyName(String gameLobbyName) { this.gameLobbyName = gameLobbyName; }
    public Set<Player> getPlayers() { return players; }
    public void setPlayers(Set<Player> players) { this.players = players; }
    public String getClues() { return clues; }
    public void setClues(String clues) { this.clues = clues; }
    public Lobby getLobby() { return new Lobby(); }
    public List<GameCard> getGameCards() {
        Collections.sort(gameCards);

        return gameCards;
    }
    public void setGameCards(List<GameCard> GameCards) { this.gameCards = GameCards; }


        /* Special methods */
    /**
     * Add clue to list of clues
     * Clue should be in the specified form:
     * {one word clue}{#of cards that it applies to}
     * @param clue
     */
    public void addClue(String clue) { this.clues += "," +  clue.strip(); }

    public void generateWordList() {
        List<Card> allCards = Main.cardRepo.findAll();
        Random rand = new Random();
        Card add;

        // Logic test, must be enough cards in card repo
        if(allCards.size() < 25)
            return;

        // Get rid of existing cards tied to game
        cards.clear();

        for(int i = 0; i < 25; i++) {
            add = allCards.get(rand.nextInt(allCards.size()));
            allCards.remove(add);
            cards.add(add);

            try {
                if (gameCards != null)
                    gameCards.get(i).setWord(add.getWord());
            } catch (IndexOutOfBoundsException e) {
                // Do nothing
            }
        }

        Main.gameRepo.save(this);
    }

    public void generateGameCards() {
        GameCard card;
        // Clear GameCard arrayList
        if (gameCards != null)
            gameCards.clear();

        // Array of card colors that will need to be applied

        ArrayList<Color> colors = new ArrayList<>(Arrays.asList(
            // ONE BLACK CARD
            BLACK,
            // EIGHT YELLOW CARDS
            BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE,
            // NINE RED CARDS
            RED, RED, RED, RED, RED, RED, RED, RED, RED,
            // SEVEN GREY CARDS
            GREY, GREY, GREY, GREY, GREY, GREY, GREY
        ));

        // Shuffle
        Collections.shuffle(colors);

        // Go through colors and apply them to GameCard objects
        for(int i = 0; i < 25; i++) {
            card = new GameCard(i, colors.remove(0), this);

            Main.gameCardRepo.save(card);
            gameCards.add(card);
        }

        // Go through the list of words allotted for the game and apply to Cards
        int i = 0;
        for (Card c : getCards())
            gameCards.get(i++).setWord(c.getWord());


        // Save to main repo
        Main.gameRepo.save(this);
    }


        /* Baby classes (inner classes) */
    class Lobby {
        private String lobbyName;
        private int numPlayers;
        private int id;

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
        
        public int getIdentity() {
        	return id;
        }
    }

	public void removePlayer(Player attachedPlayer) {
		players.remove(attachedPlayer);
	}
}