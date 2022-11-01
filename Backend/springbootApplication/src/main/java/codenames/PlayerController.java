package codenames;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Isaac Lo
 *
 */

@RestController
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository = Main.playerRepo;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping(path = "/players")
    List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    @GetMapping(path = "/players/{id}")
    Player getPlayerById( @PathVariable int id){
        return playerRepository.findById(id);
    }

    @PostMapping(path = "/players/create")
    String createPlayer(@RequestBody Player player){
        if (player == null)
            return failure;
        playerRepository.save(player);
        return success;
    }
    
    @PostMapping(path = "/players/{username}/setTeam/{team}")
    String setTeam(@PathVariable String username, @PathVariable String team) {
    	User usr = Main.userRepo.findByusername(username);
    	if(usr==null)return "{\"message\":\"could not find player\"}";
    	if(!(team.toLowerCase().equals("red") || team.toLowerCase().equals("blue")))return "{\"message\":\"invalid team color\"}";
    	Main.userRepo.findByusername(username).getAttachedPlayer().setTeam(team.toLowerCase().equals("red") ? Color.RED : Color.BLUE);
    	return success;
    }
    
    @PostMapping(path = "/players/{username}/setTeam/{role}")
    String setRole(@PathVariable String username, @PathVariable String role) {
    	User usr = Main.userRepo.findByusername(username);
    	if(usr==null)return "{\"message\":\"could not find player\"}";
    	if(!(role.toLowerCase().equals("spymaster") || role.toLowerCase().equals("operative")))return "{\"message\":\"invalid role name\"}";
    	Main.userRepo.findByusername(username).getAttachedPlayer().setRole(role.toLowerCase().equals("spymaster") ? Role.SPYMASTER : Role.OPERATIVE);
    	return success;
    }

    @DeleteMapping(path = "/players/{id}")
    String deletePlayer(@PathVariable int id){
        playerRepository.deleteById(id);
        return success;
    }
}