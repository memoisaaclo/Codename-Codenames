package onetoone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    GameRepository gameRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/games/add")
    public @ResponseBody String createNewGame(@RequestBody Game game) {
        if(gameRepository.findBygamelobbyname(game.getGameLobbyName()) != null){
            return "{\"message\":\"Lobby name already in use\"}";
        }

        gameRepository.save(game);
        return "{\"message\":\"success\"}";
    }

    @GetMapping(path = "/games/{id}/numPlayers")
    String getGamePlayerNumberById( @PathVariable int id) { return gameRepository.findById(id).getPlayers().size() + ""; }

    @PostMapping(path = "/games/{id}/addPlayer")
    String addPlayerToGame(@PathVariable int id, @RequestBody Player player){
        if (player == null)
            return failure;
        gameRepository.findById(id).addPlayer(player);
        return success;
    }

    @GetMapping(path = "/games")
    List<Game> getAllPlayers(){
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