package codenames;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class GameController {

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";
    private String invalidGame ="{\"message\":\"Invalid lobby ID\"}";

    /**
     * default constructor
     */
    public GameController() {
    }

    /**
     * constructor
     * @param gameRepository
     */
    public GameController(GameRepository gameRepository) {
        Main.gameRepo = gameRepository;
    }

    /**
     * create a game
     * @param game
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
     * @param id
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
     * @param id
     * @param username
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
     * @param id
     * @param username
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
     * @param id
     * @return list of all players
     */
    @GetMapping(path = "/games/{id}/players")
    Set<Player> getPlayers(@PathVariable int id){
    	Game check = Main.gameRepo.findById(id);
    	if(check ==null) return new HashSet<Player>();
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
     * @param id
     * @return game data as JSON
     */
    @GetMapping(path = "/games/{id}")
    Game getGameById( @PathVariable int id){
        return Main.gameRepo.findById(id);
    }
    
    /**
     * removes all games
     */
    @DeleteMapping(path = "/games/deleteall")
    void deleteAllGames() {
    	Main.gameRepo.deleteAll();
    }

        /* Special Methods */

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
    
    /**
     * deletes all games
     */
    @DeleteMapping(path = "/games/removeall/98765")
    public void removeall() {
    	Main.gameRepo.deleteAll();
    }
}