package codenames;

import org.springframework.web.bind.annotation.*;

/**
 * Controller that interacts with Entity Game
 * Only interacts with active games and their game play functions.
 */
@RestController
public class BoardController {
    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";
    private final String invalid ="{\"message\":\"invalid argument\"}";

    public BoardController() { }

    public BoardController(GameRepository gameRepository) { Main.gameRepo = gameRepository; }


        /*  Clue and Guess Methods */

    /**
     * Send a clue to a game board
     * @param id game id
     * @param clue string of single line clue
     * @param numGuesses the number of cards this applies to
     * @param player player that is sending request
     * @return success or error message
     */
    @PutMapping(path = "/games/{id}/clue/{clue}/{numGuesses}")
    @ResponseBody
    String receiveCurrentClue(@PathVariable int id, @PathVariable String clue, @PathVariable int numGuesses, @RequestBody Player player) {
        Game g = Main.gameRepo.findById(id);
        if(g == null)
            return invalid;

        // Player Validation
        if (!player.getRole().equals(Role.SPYMASTER.toString())
                || !player.getTeam().equals(g.getTurnColor()))
            return "{\"message\":\"incorrect clue giver\"}";

        // Data Validation
        if(clue.strip().equals("")) // (clue validation)
            return failure;
        if (numGuesses < 0)
            return failure;
        else if (numGuesses > 25)
            numGuesses = 25;
        else
            numGuesses += 1;
        if (clue.strip().trim().contains(" ")) // (clue must be a single word)
            return failure;
        if (g.getGuessesAvailable() != 0) // (game state validation)
            return failure;

        // Send clue to game
        g.setGuessesAvailable(numGuesses);
        g.addClue(clue);

        // Save and return
        Main.gameRepo.save(g);
        return success;
    }

    /**
     * Send a guess to reveal a card on a board
     * @param id game id
     * @param card_position position of card on board (0-24)
     * @param player player sending request
     * @return success or fail message
     */
    @PutMapping(path = "/games/{id}/guess/{card_position}")
    @ResponseBody String receiveGuess(@PathVariable int id, @PathVariable int card_position, @RequestBody Player player) {
        Game g = Main.gameRepo.findById(id);
        if(g == null)
            return invalid;

        // Player validation
        if (!g.getTurnColor().equals(player.getTeam())
            || !player.getRole().equals(Role.OPERATIVE.toString()))
            return "{\"message\":\"incorrect guesser\"}";

        // Data Validation
        else if (card_position < 0 || card_position > 24)
            return failure;

        g.getGuess(card_position);

        Main.gameRepo.save(g);
        return success;
    }


        /*  Board Generation Methods */
    /**
     * Adds 25 words from word list to game object
     * @param id ID of game in repository
     * @return success or error message
     */
    @GetMapping(path = "/games/{id}/generatewords")
    String genWords(@PathVariable int id) {
        Game g = Main.gameRepo.findById(id);

        if (g == null)
            return invalid;

        g.generateWordList();
        return success;
    }

    /**
     * Generate the GameCard array for 25 cards in an array in reference to word list.
     * @param id game id
     * @return success or error message
     */
    @GetMapping(path = "/games/{id}/generatestates")
    String generateGameCards(@PathVariable int id) {
        Game g = Main.gameRepo.findById(id);

        if (g == null)
            return invalid;

        g.generateGameCards();
        return success;
    }

    /**
     * Generate both words and game card states in one method
     * @param id game id
     * @return result message (ex. success)
     */
    @GetMapping(path = "games/{id}/generateboard")
    String generateWordsAndStates(@PathVariable int id) {
        Game g = Main.gameRepo.findById(id);
        if(g == null)
            return invalid;

        g.generateWordList();
        g.generateGameCards();
        return success;
    }


        /*  Getter Methods */
    /**
     * Method to get status of board
     * Used by frontend to refresh game
     */
    @GetMapping(path = "/games/{id}/getboard")
    String getGameStatus(@PathVariable int id) {
        Game g = Main.gameRepo.findById(id);
        if (g == null)
            return invalid;

        StringBuilder rstring = new StringBuilder("[");

        for (GameCard c : g.getGameCards())
            rstring.append(c.displayInfo()).append(", ");

        if (g.getCards() == null)
            return "{\"message\":\"Invalid Game State\"}";

        return rstring.substring(0, rstring.length()-2)+ "]";
    }

    /**
     * Get list of words (25) of a certain game
     * Used to initially get words
     * @param id game id
     * @return success or error message
     */
    @GetMapping(path = "/games/{id}/words")
    String getWords(@PathVariable int id) {
        Game g = Main.gameRepo.findById(id);
        if(g == null)
            return invalid;

        StringBuilder rstring = new StringBuilder("{");
        int i = 0;

        for (GameCard c : g.getGameCards())
            rstring.append("\"").append(i++).append("\": \"").append(c.getWord()).append("\", ");

        if (g.getCards() == null)
            return "{\"message\":\"Invalid Game State\"}";

        return rstring.substring(0, rstring.length()-2)+ "}";
    }

    /**
     * Get list of colors (25) of a certain game
     * Used to refresh view colors
     * @param id game id
     * @return list of each card's color
     */
    @GetMapping(path = "/games/{id}/colors")
    String getColors(@PathVariable int id) {
        Game g = Main.gameRepo.findById(id);
        if(g == null)
            return invalid;

        StringBuilder rstring = new StringBuilder("{");
        int i = 0;

        for (GameCard c : g.getGameCards())
            rstring.append("\"").append(i++).append("\": \"").append(c.getColor().name()).append("\", ");

        if (g.getGameCards() == null)
            return "{\"message\":\"Invalid Game State\"}";

        return rstring.substring(0, rstring.length()-2)+ "}";
    }

    /**
     * Get list of revealed statuses of a certain game
     * Used to refresh view
     * @param id game id
     * @return list of cards' revealed status
     */
    @GetMapping(path = "/games/{id}/isrevealed")
    String getRevealed(@PathVariable int id) {
        Game g = Main.gameRepo.findById(id);
        if(g == null)
            return invalid;

        StringBuilder rstring = new StringBuilder("{");
        int i = 0;

        for (GameCard c : g.getGameCards())
            rstring.append("\"").append(i++).append("\": \"").append(c.isRevealed()).append("\", ");

        if (g.getGameCards() == null)
            return "{\"message\":\"Invalid Game State\"}";

        return rstring.substring(0, rstring.length()-2)+ "}";
    }

    /**
     * Get list of live clues of a certain game
     * Used to refresh view
     * @param id game id
     * @return list of live clues
     */
    @GetMapping(path = "/games/{id}/cluelist")
    String getClueList(@PathVariable int id) {
        Game g = Main.gameRepo.findById(id);
        if(g == null)
            return invalid;

        StringBuilder rstring = new StringBuilder("{");
        int i = 0;

        for (String clue : g.generateClueList()) {
            rstring.append("\"").append(i++).append("\": \"").append(clue).append("\", ");
        }

        return rstring.substring(0, rstring.length()-2)+ "}";
    }

    /**
     * Get current (most recent) clue of a certain game
     * @param id game id
     * @return current clue
     */
    @GetMapping(path = "/games/{id}/clue")
    String getCurrentClue(@PathVariable int id) {
        Game g = Main.gameRepo.findById(id);

        if(g == null)
            return invalid;

        return "{\"clue\": \"" + g.getCurrentClue() + "\"}";
    }

    /**
     * Get current team color of game
     * @param id game id
     * @return turn color
     */
    @GetMapping(path = "/games/{id}/turncolor")
    String getCurrentTeamColor(@PathVariable int id) {
        Game g = Main.gameRepo.findById(id);
        if(g == null)
            return invalid;

        return "{\"turnColor\": \"" + g.getTurnColor() + "\"}";
    }
}