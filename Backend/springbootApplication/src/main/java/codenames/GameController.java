package codenames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController {

    @Autowired
    GameRepository gameRepository = Main.gameRepo;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

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
    		return "{\"message\":\"Invalid lobby id\"}";
    	}
    }
    
    @GetMapping(path = "/games/{id}/generatewords")
    void genWords(@PathVariable int id) {
    	Game g = Main.gameRepo.findById(id);
    	g.generateWordList();
    }

    /**
     * Generate the cardState array for 25 cards in a parallel array to a game's cards.
     * @param id
     */
    @GetMapping(path = "games/{id}/generateStates")
    void generateCardStates(@PathVariable int id) {
        Main.gameRepo.findById(id).generateCardStates();
    }
    
    @PostMapping(path = "/games/{id}/addPlayer/{username}")
    String addPlayerToGame(@PathVariable int id, @PathVariable String username) {
        
    	User add = Main.userRepo.findByusername(username);
    	if(add ==null) return "{\"message\":\"player does not exist\"}";
    	add.addToGame(id); 
    	
        return success;
    } 
    
    @DeleteMapping(path = "/games/{id}/removePlayer")
    String removePlayerFromGame(@PathVariable int id, @RequestParam int player_id){
        PlayerRepository playerRepo = Main.playerRepo;

        if (playerRepo.findById(player_id) == null || gameRepository.findById(id) == null)
            return failure;

        Game g = gameRepository.findById(id);

        if(g.getPlayers().contains(playerRepo.findById(player_id))) {
        	g.getPlayers().remove(playerRepo.findById(player_id));
        	gameRepository.save(g);
        	return success;
        }

        return "{\"message\":\"could not find player\"}";
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

    @DeleteMapping(path = "/games/delete/{id}")
    String deleteGame(@PathVariable int id){
        gameRepository.deleteById(id);
        return success;
    }

    /**
     * Method to get status of board
     * Used by frontend to refresh game
     */
//    @GetMapping(path = "/games/{id}/status")
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

            for (Card c : g.getCards()) {
                rstring += "\"" + i + "\": \"" + c.getWord() + "\", ";
                i++;
            }

            return rstring.substring(0, rstring.length()-2)+ "}";
        } else {
            return "{\"message\":\"Invalid Lobby ID\"}";
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