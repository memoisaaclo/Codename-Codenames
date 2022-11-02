package codenames;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.lang.Nullable;

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

    @Column(name = "currentClue")
    private String currentClue = "";

    @Column(name = "guessesAvailable")
    private Integer guessesAvailable = 0;

    @Column(name = "turnColor")
    private Color turnColor = RED;

    @Column(name = "moves")
    private String moves = "";

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "game", orphanRemoval = true)
    @JsonManagedReference
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
    public String getTurnColor() { return turnColor.name(); }
    public void setTurnColor(Color turnColor) { this.turnColor = turnColor; }
    public List<GameCard> getGameCards() {
        Collections.sort(gameCards);
        return gameCards;
    }
    public void setGameCards(List<GameCard> GameCards) { this.gameCards = GameCards; }
    public String getCurrentClue() { return currentClue; }
    public void setCurrentClue(String currentClue) { this.currentClue = currentClue; }
    public int getGuessesAvailable() { return guessesAvailable; }
    public void setGuessesAvailable(int guessesAvailable) { this.guessesAvailable = guessesAvailable; }


    /* Special methods */
    /**
     * Add clue to list of clues
     * Clue should be in the specified form:
     * {one word clue}{#of cards that it applies to}
     * @param clue
     */
    public void addClue(String clue) {
        this.clues += "," +  clue.strip();
        currentClue = clue;
    }

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
                if (gameCards.size() != 0)
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
        for (GameCard gc : gameCards)
            Main.gameCardRepo.delete(gc);
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

            gameCards.add(card);
            Main.gameCardRepo.save(card);
        }

        // Go through the list of words allotted for the game and apply to Cards
        int i = 0;
        for (Card c : getCards())
            gameCards.get(i++).setWord(c.getWord());


        // Save to main repo
        Main.gameRepo.save(this);
    }

    public void getGuess(int card_position) {
        // Assume Data is Valid
        List<GameCard> cards = getGameCards();
        GameCard card = cards.get(card_position);

        if (card.isRevealed())
            return;
        else
            card.setRevealed(true);

        if (card.getColor() == turnColor)
            guessesAvailable--;
        else
            setGuessesAvailable(0);

        if (getGuessesAvailable() == 0) {
            swapTeam();
        }
    }

    public String[] generateClueList() { return clues.split(","); }

    public void swapTeam() {
        switch (turnColor) {
            case RED:
                setTurnColor(BLUE);
                break;
            case BLUE:
                setTurnColor(RED);
                break;
        }
    }

        /* Baby classes (inner classes) */
    class Lobby {
        private String lobbyName;
        private int numPlayers;
        private int identity;
        
        Lobby() {
            this.numPlayers = players.size();
            this.lobbyName = gameLobbyName;
            this.identity = id;
        }

        public String getLobbyName() {
            return lobbyName;
        }

        public int getNumPlayers() {
            return numPlayers;
        }
        
        public int getIdentity() {
        	return identity;
        }
    }

	public void removePlayer(Player attachedPlayer) {
		players.remove(attachedPlayer);
	}
}