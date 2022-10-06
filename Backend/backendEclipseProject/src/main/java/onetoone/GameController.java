package onetoone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    
    @PostMapping(path = "/games/{id}/addPlayer")
    String addPlayerToGame(@PathVariable int id, @RequestParam int player_id){
        PlayerRepository playerRepo = Main.playerRepo;
        if (playerRepo.findById(player_id) == null || gameRepository.findById(id) == null)
            return failure;
        Game g = gameRepository.findById(id);
        if(g.getPlayers().contains(playerRepo.findById(player_id))) {
        	return "{\"message\":\"error: duplicate player id\"}";
        }
        g.addPlayer(playerRepo.findById(player_id));
        gameRepository.save(g);
        return success;
    }
    
    @PostMapping(path = "/games/{id}/removePlayer")
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

    @PostMapping(path = "/games")
    String createGame(@RequestBody Game game){
        if (game == null)
            return failure;
        gameRepository.save(game);
        return success;
    }

    @PutMapping("/games/{id}")
    Game updateGame(@PathVariable int id, @RequestBody Game request){
        Game game = gameRepository.findById(id);
        if(game == null)
            return null;
        gameRepository.save(request);
        return gameRepository.findById(id);
    }

    @DeleteMapping(path = "/games/{id}")
    String deleteGame(@PathVariable int id){
        gameRepository.deleteById(id);
        return success;
    }
}