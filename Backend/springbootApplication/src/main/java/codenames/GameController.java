package codenames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class GameController {

    @Autowired
    GameRepository gameRepository = Main.gameRepo;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";
    private String invalid ="{\"message\":\"Invalid lobby ID\"}";

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/games/add")
    public @ResponseBody String createNewGame(@RequestBody Game game) {
        if(gameRepository.findBygameLobbyName(game.getGameLobbyName()) != null){
            return "{\"message\":\"Lobby name already in use\"}";
        }

        gameRepository.save(game);
        return "{\"message\":\"success\"}";
    }

    @GetMapping(path = "/games/{id}/numPlayers")
    String getGamePlayerNumberById( @PathVariable int id) {
    	if(gameRepository.findById(id) != null) {
	        int playerNum = gameRepository.findById(id).getPlayers().size();
	        return String.format("{\"playerNum\": \"%s\"}", playerNum);
    	} else {
    		return invalid;
    	}
    }
    
    @GetMapping(path = "/games/{id}/generateWords")
    String genWords(@PathVariable int id) {
    	Game g = Main.gameRepo.findById(id);

        if (g == null)
            return invalid;

        g.generateWordList();
        return success;
    }

    /**
     * Generate the GameCard array for 25 cards in a parallel array to a game's cards.
     * @param id
     */
    @GetMapping(path = "games/{id}/generateStates")
    String generateGameCards(@PathVariable int id) {
        Game g = Main.gameRepo.findById(id);

        if (g == null)
            return invalid;

        g.generateGameCards();
        return success;
    }
    
    @PostMapping(path = "/games/{id}/addPlayer/{username}")
    String addPlayerToGame(@PathVariable int id, @PathVariable String username) {
        
    	User add = Main.userRepo.findByusername(username);
    	if(add ==null) return "{\"message\":\"could not find player\"}";
    	add.addToGame(id); 
    	
        return success;
    } 
    
    @DeleteMapping(path = "/games/{id}/removePlayer/{username}")
    String removePlayerFromGame(@PathVariable int id, @PathVariable String username){
    	User remove = Main.userRepo.findByusername(username);
    	if(remove ==null) return "{\"message\":\"could not find player\"}";
    	
    	remove.removeFromGame(id);
    	
    	
        return "{\"message\":\"could not find player\"}";
    }
    
    @GetMapping(path = "/games/{id}/players")
    Set<Player> getPlayers(@PathVariable int id){
    	return Main.gameRepo.findById(id).getPlayers();
    }

    @GetMapping(path = "/games")
    List<Game> getAllGames(){
        return gameRepository.findAll();
    }

    @GetMapping(path = "/games/{id}")
    Game getGameById( @PathVariable int id){
        return gameRepository.findById(id);
    }

    @DeleteMapping(path = "/games/{id}/delete")
    String deleteGame(@PathVariable int id){
        gameRepository.deleteById(id);
        return success;
    }


        /* Special Methods */
    /**
     * Method to get status of board
     * Used by frontend to refresh game
     */
//    @GetMapping(path = "/games/{id}/getBoard")
//    String getGameStatus(@PathVariable int id) {
//        return ;
//    }

    /**
     * Get list of words (25) of a certain game
     * Used to initially get words
     * @param id
     * @return
     */
    @GetMapping(path = "/games/{id}/words")
    String getWords(@PathVariable int id) {
        Game g = gameRepository.findById(id);

        if(g == null)
            return invalid;

        String rstring = "{";
        int i = 0;

        for (GameCard c : g.getGameCards())
            rstring += "\"" + i++ + "\": \"" + c.getWord() + "\", ";

        if (g.getCards() == null)
            return "{\"message\":\"Invalid Game State\"}";

        return rstring.substring(0, rstring.length()-2)+ "}";
    }

    /**
     * Get list of colors (25) of a certain game
     * Used to refresh view colors
     * @param id
     * @return
     */
    @GetMapping(path = "/games/{id}/colors")
    String getColors(@PathVariable int id) {
        Game g = gameRepository.findById(id);

        if(g == null)
            return invalid;

        String rstring = "{";
        int i = 0;

        for (GameCard c : g.getGameCards())
            rstring += "\"" + i++ + "\": \"" + c.getColor().name() + "\", ";

        if (g.getGameCards() == null)
            return "{\"message\":\"Invalid Game State\"}";

        return rstring.substring(0, rstring.length()-2)+ "}";
    }

    /**
     * Get list of revealed statuses of a certain game
     * Used to refresh view
     * @param id
     * @return
     */
    @GetMapping(path = "/games/{id}/isrevealed")
    String getRevealed(@PathVariable int id) {
        Game g = gameRepository.findById(id);

        if(g == null)
            return invalid;

        String rstring = "{";
        int i = 0;

        for (GameCard c : g.getGameCards())
            rstring += "\"" + i++ + "\": \"" + c.isRevealed() + "\", ";

        if (g.getGameCards() == null)
            return "{\"message\":\"Invalid Game State\"}";

        return rstring.substring(0, rstring.length()-2)+ "}";
    }

    /**
     * Get list of live clues of a certain game
     * Used to refresh view
     * @param id
     * @return
     */
    @GetMapping(path = "/games/{id}/clueList")
    String getClueList(@PathVariable int id) {
        Game g = gameRepository.findById(id);

        if(g == null)
            return invalid;

        String rstring = "{";
        int i = 0;

        for (String clue : g.generateClueList()) {
            rstring += "\"" + i++ + "\": \"" + clue + "\", ";
        }

        return rstring.substring(0, rstring.length()-2)+ "}";
    }

    /**
     * Get list of current clue of a certain game
     * @param id
     * @return
     */
    @GetMapping(path = "/games/{id}/clue")
    String getCurrentClue(@PathVariable int id) {
        Game g = gameRepository.findById(id);

        if(g == null)
            return invalid;

        return "{\"clue\": \"" + g.getCurrentClue() + "\"}";
    }

    @PutMapping(path = "/games/{id}/clue/{clue}/{numGuesses}")
    @ResponseBody String sendCurrentClue(@PathVariable int id, @PathVariable String clue, @PathVariable int numGuesses) {
        Game g = gameRepository.findById(id);

        if(g == null)
            return invalid;

        if(clue.strip().equals(""))
            return failure;

        // Data Validation
        if (numGuesses < 0)
            return failure;
        else if (numGuesses > 25)
            numGuesses = 25;
        else
            numGuesses += 1;
        if (clue.strip().trim().contains(" "))
            return failure;
        if (g.getGuessesAvailable() != 0)
            return failure; // Check if game state is valid

        g.setGuessesAvailable(numGuesses);
        g.addClue(clue);

        gameRepository.save(g);

        return success;
    }

    /**
     * Get list of current team color of a certain game
     * @param id
     * @return
     */
    @GetMapping(path = "/games/{id}/turncolor")
    String getCurrentTeamColor(@PathVariable int id) {
        Game g = gameRepository.findById(id);
        if(g == null)
            return invalid;

        return "{\"turnColor\": \"" + g.getTurnColor() + "\"}";
    }

    @PutMapping(path = "/games/{id}/guess/{card_position}")
    @ResponseBody String receiveGuess(@PathVariable int id, @PathVariable int card_position, @RequestBody Player player) {
        Game g = gameRepository.findById(id);
        if(g == null)
            return invalid;

        // Data Validation
        if (!g.getTurnColor().equals(player.getTeam()))
            return "{\"message\":\"incorrect team guess\"}";
        else if (card_position < 0 || card_position > 24)
            return failure;

        g.getGuess(card_position);

        gameRepository.save(g);
        return success;
    }

    /**
     * Get all active game information
     * Used to populate lobby with game names and player nums
     * @return JSON string of name and num players
     */
    @GetMapping(path = "/games/lobbyinfo")
    public List<Game.Lobby> getGameLobbyName() {
        List<Game.Lobby> l = new ArrayList<Game.Lobby>();
    	for(Game g : getAllGames()) {
        	l.add(g.getLobby());
        }
        return l;
    }

    // Smile :>
}