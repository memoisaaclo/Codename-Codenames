package codenames;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    private Integer id;

    @Column(name = "clues")
    private String clues = "";

    @Column(name = "current_clue")
    private String currentClue = "";

    @Column(name = "guesses_available")
    private Integer guessesAvailable = 0;

    @Column(name = "turn_color")
    private Color turnColor = RED;

    @Column(name = "turn_role")
    private Role turnRole = Role.SPYMASTER;

    @Column(name = "moves")
    private String moves = "";

    @Column(name = "game_lobby_name", unique = true)
    private String gameLobbyName;

    @OneToMany(fetch = FetchType.EAGER)
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

    @Column(name = "red_points")
    private Integer redPoints = 0;

    @Column(name = "blue_points")
    private Integer bluePoints = 0;

    private static final int RED_POINTS_TO_WIN = 9;
    private static final int BLUE_POINTS_TO_WIN = 8;

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
    public Set<Player> getPlayers() { 
    	return players; 
	}
    public void setPlayers(Set<Player> players) { this.players = players; }
    public String getClues() { return clues; }
    public void setClues(String clues) { this.clues = clues; }
    public Lobby getLobby() { return new Lobby(); }
    public String getTurnColor() { return turnColor.toString(); }
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
    public Role getTurnRole() { return turnRole; }
    public void setTurnRole(Role turnRole) { this.turnRole = turnRole; }
    public Integer getRedPoints() { return redPoints; }
    public Integer getBluePoints() { return bluePoints; }


    /* Special methods */
    /**
     * Add clue to list of clues
     * Clue should be in the specified form:
     * {one word clue}{#of cards that it applies to}
     * @param clue clue to be added
     */
    public void addClue(String clue) {
        this.clues += "," +  clue.strip();
        currentClue = clue;
    }

    /**
     * generates the word list for a game
     */
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

    /**
     * generates the card statuses for a game
     */
    public void generateGameCards() {
        GameCard card;

        // Clear GameCard arrayList
        Main.gameCardRepo.deleteAll(gameCards);
        gameCards.clear();

        // Array of card colors that will need to be applied

        ArrayList<Color> colors = new ArrayList<>(Arrays.asList(
            // ONE BLACK CARD
            BLACK,
            // EIGHT BLUE CARDS
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

    /**
     * guesses a card given
     * @param card_position 0-24
     */
    public void getGuess(int card_position, User user) {
        // Assume card position is Valid
        List<GameCard> cards = getGameCards();
        GameCard card = cards.get(card_position);

        // Check if the card is revealed; if it is, do nothing.
        if (card.isRevealed())
            return;
        else
            card.setRevealed(true);

        // If the card is the correct team color, maintain turn.
        if (card.getColor() == turnColor) {
            guessesAvailable--;
            user.incrementCorrectGuessesMade();

            // Adjust team points
            switch(turnColor) {
                case RED:
                    redPoints++;
                    break;
                case BLUE:
                    bluePoints++;
                    break;
            }

            // Check if game is won
            checkWin();
        } else
            setGuessesAvailable(0);

        // If there are no more guesses available, switch teams.
        if (getGuessesAvailable() == 0) {
            swapTeam();
        }

        user.incrementGuessesMade();
        Main.userRepo.save(user);

        // TODO: Make sure: Does this actually change the GameCard?
    }

    /**
     * creates a list of clues for this game
     * @return String array of clues
     */
    public String[] generateClueList() { return clues.split(","); }

    /**
     * changes current teams turn    
     */
    public void swapTeam() {
        switch (turnColor) {
            case RED:
                setTurnColor(BLUE);
                setTurnRole(Role.SPYMASTER);
                break;
            case BLUE:
                setTurnColor(RED);
                setTurnRole(Role.SPYMASTER);
                break;
        }
    }

    /**
     * Check if the game is in winning status
     */
    public void checkWin() {
        if (redPoints == RED_POINTS_TO_WIN) {
            GameUpdateWebsocketController.broadcastWinToLobby("win red", id);

            for(Player player : players)
                if (player.getTeam().equalsIgnoreCase("RED")) player.getUser().incrementWins();
        } else if (bluePoints == BLUE_POINTS_TO_WIN) {
            GameUpdateWebsocketController.broadcastWinToLobby("win blue", id);

            for(Player player : players)
                if (player.getTeam().equalsIgnoreCase("BLUE")) player.getUser().incrementWins();
        }
    }

    /**
     * 
     * inner class to represent a paired down version of the game class 
     *
     */
    class Lobby {
        private String lobbyName;
        private Integer numPlayers;
        private Integer identity;
        
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

    /**
     * remove a player from the game
     * @param attachedPlayer the player to be removed
     */
	public void removePlayer(Player attachedPlayer) {
		players.remove(attachedPlayer);
	}
}