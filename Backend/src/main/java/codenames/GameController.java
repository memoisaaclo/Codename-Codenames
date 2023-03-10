package codenames;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class GameController {

    private final String success = "{\"message\":\"success\"}";
   //private final String failure = "{\"message\":\"failure\"}";
    private final String invalidGame ="{\"message\":\"Invalid lobby ID\"}";

    /**
     * default constructor
     */
    public GameController() {
    }

    /**
     * constructor
     * @param gameRepository of program? :>
     */
//    public GameController(GameRepository gameRepository) {	// removed for coverage. re-add if necessary
//        Main.gameRepo = gameRepository;
//    }

    /**
     * create a game
     * @param game to be created
     * @return success or failure
     */
    @RequestMapping(method = RequestMethod.POST, path = "/games/add")
    public @ResponseBody String createNewGame(@RequestBody Game game) {
        if(Main.gameRepo.findBygameLobbyName(game.getGameLobbyName()) != null){
            return "{\"message\":\"Lobby name already in use\"}";
        }

        Main.gameRepo.save(game);
        return "{\"message\":\"success\", \"id\":\"" + game.getLobby().getIdentity() + "\"}";
    }
    
    /** 
     * gets number of players in a game
     * @param id of game
     * @return number of players
     */
    @GetMapping(path = "/games/{id}/numplayers")
    String getGamePlayerNumberById( @PathVariable int id) {
    	if(Main.gameRepo.findById(id) != null) {
	        int playerNum = Main.gameRepo.findById(id).getPlayers().size();
	        return String.format("{\"playerNum\": \"%s\"}", playerNum);
    	} else {
    		return invalidGame;
    	}
    }
    
    /**
     * adds a player to the game
     * @param id of game
     * @param username of player to be added
     * @return success or failure
     */
    @PostMapping(path = "/games/{id}/addplayer/{username}")
    String addPlayerToGame(@PathVariable int id, @PathVariable String username) {
        
    	User add = Main.userRepo.findByusername(username);
    	Game check = Main.gameRepo.findById(id);
    	if(add ==null) return "{\"message\":\"could not find player\"}";
    	if(check ==null) return "{\"message\":\"could not find game\"}";
    	if(check.getPlayers().contains(add.getAttachedPlayer())) return "{\"message\":\"player is already in game\"}";
    	add.addToGame(id); 
        return success;
    } 
    
    /**
     * remove a player from a game
     * @param id of game
     * @param username of player
     * @return success or failure
     */
    @DeleteMapping(path = "/games/{id}/removeplayer/{username}")
    String removePlayerFromGame(@PathVariable int id, @PathVariable String username){
    	User remove = Main.userRepo.findByusername(username);
    	Game check = Main.gameRepo.findById(id);
    	if(remove ==null) return "{\"message\":\"could not find player\"}";
    	if(check ==null) return "{\"message\":\"could not find game\"}";
    	if(!check.getPlayers().contains(remove.getAttachedPlayer())) return "{\"message\":\"player is already not in game\"}";
    	
    	remove.removeFromGame(id);
    	
    	
        return success;
    }
    
    /**
     * get a list of all players
     * @param id of game
     * @return list of all players
     */
    @GetMapping(path = "/games/{id}/players")
    Set<Player> getPlayers(@PathVariable int id){
    	Game check = Main.gameRepo.findById(id);
    	if(check ==null) return new HashSet<>();
    	return Main.gameRepo.findById(id).getPlayers();
    }

    /**
     * gets a list of all games
     * @return list of all games
     */
    @GetMapping(path = "/games")
    List<Game> getAllGames(){
        return Main.gameRepo.findAll();
    }

    /**
     * gets all data of a game by id
     * @param id of game
     * @return game data as JSON
     */
    @GetMapping(path = "/games/{id}")
    Game getGameById( @PathVariable int id){
        return Main.gameRepo.findById(id);
    }

        /* Special Methods */
    /**
     * Get all active game information
     * Used to populate lobby with game names and player nums
     * @return JSON string of name and num players
     */
    @GetMapping(path = "/games/lobbyinfo")
    public List<Game.Lobby> getGameLobbyName() {
        List<Game.Lobby> l = new ArrayList<>();
    	for(Game g : getAllGames()) {
        	l.add(g.getLobby());
        }
        return l;
    }
    
    /**
     * deletes all games
     */
//    @DeleteMapping(path = "/games/removeall/98765")	// this never existed. 
//    public void removeall() {
//    	Main.gameRepo.deleteAll();
//    }
    
    /**
     * ends the program. uhh, security risk, but needed for coverage testing through eclipse
     */
    @GetMapping(path = "/end")
    public void end() {
    	System.exit(0);
    }
}