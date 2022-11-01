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


//    @PutMapping("/games/{id}")
//    Game updateGame(@PathVariable int id, @RequestBody Game request){
//        Game game = gameRepository.findById(id);
//        if(game == null)
//            return null;
//        gameRepository.save(request);
//        return gameRepository.findById(id);
//    }

    @DeleteMapping(path = "/games/{id}/delete")
    String deleteGame(@PathVariable int id){
        gameRepository.deleteById(id);
        return success;
    }

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

        if(g != null) {
            String rstring = "{";
            int i = 0;

            for (GameCard c : g.getGameCards()) {
                rstring += "\"" + i + "\": \"" + c.getWord() + "\", ";
                i++;
            }

            if (g.getCards() == null)
                return "{\"message\":\"Invalid Game State\"}";

            return rstring.substring(0, rstring.length()-2)+ "}";
        } else {
            return invalid;
        }
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

        if(g != null) {
            String rstring = "{";
            int i = 0;

            for (GameCard c : g.getGameCards()) {
                rstring += "\"" + i + "\": \"" + c.getColor().name() + "\", ";
                i++;
            }

            if (g.getGameCards() == null)
                return "{\"message\":\"Invalid Game State\"}";

            return rstring.substring(0, rstring.length()-2)+ "}";
        } else {
            return invalid;
        }
    }

    /**
     * Get list of revealed status (25) of a certain game
     * Used to refresh view colors
     * @param id
     * @return
     */
    @GetMapping(path = "/games/{id}/isRevealed")
    String getRevealed(@PathVariable int id) {
        Game g = gameRepository.findById(id);

        if(g != null) {
            String rstring = "{";
            int i = 0;

            for (GameCard c : g.getGameCards()) {
                rstring += "\"" + i + "\": \"" + c.isRevealed() + "\", ";
                i++;
            }

            if (g.getGameCards() == null)
                return "{\"message\":\"Invalid Game State\"}";

            return rstring.substring(0, rstring.length()-2)+ "}";
        } else {
            return invalid;
        }
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
}