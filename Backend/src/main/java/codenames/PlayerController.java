package codenames;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Isaac Lo
 *
 */

@RestController
public class PlayerController {

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    public PlayerController(PlayerRepository playerRepository) {
        Main.playerRepo = playerRepository;
    }

    @GetMapping(path = "/players")
    List<Player> getAllPlayers(){
        return Main.playerRepo.findAll();
    }

    @GetMapping(path = "/players/{id}")
    Player getPlayerById( @PathVariable int id){
        return Main.playerRepo.findById(id);
    }

    @PostMapping(path = "/players/create")
    String createPlayer(@RequestBody Player player){
        if (player == null)
            return failure;
        Main.playerRepo.save(player);
        return success;
    }
    
    @PostMapping(path = "/players/{username}/setteam/{team}")
    String setTeam(@PathVariable String username, @PathVariable String team) {
    	User usr = Main.userRepo.findByusername(username);
    	if(usr==null)return "{\"message\":\"could not find player\"}";
    	if(!(team.toLowerCase().equals("red") || team.toLowerCase().equals("blue")))return "{\"message\":\"invalid team color\"}";
    	usr.getAttachedPlayer().setTeam(team.toLowerCase().equals("red") ? Color.RED : Color.BLUE);
    	Main.userRepo.save(usr);
    	Main.playerRepo.save(usr.getAttachedPlayer());
    	return success;
    }
    
    @PostMapping(path = "/players/{username}/setrole/{role}")
    String setRole(@PathVariable String username, @PathVariable String role) {
    	User usr = Main.userRepo.findByusername(username);
    	if(usr==null)return "{\"message\":\"could not find player\"}";
    	if(!(role.toLowerCase().equals("spymaster") || role.toLowerCase().equals("operative")))return "{\"message\":\"invalid role name\"}";
    	usr.getAttachedPlayer().setRole(role.toLowerCase().equals("spymaster") ? Role.SPYMASTER : Role.OPERATIVE);
    	Main.userRepo.save(usr);
    	Main.playerRepo.save(usr.getAttachedPlayer());
    	return success;
    }
    
    @GetMapping(path = "/players/{username}/getteam")
    String getTeam(@PathVariable String username) {
    	return "{\"team\":\"" + Main.userRepo.findByusername(username).getAttachedPlayer().getTeam() + "\"}";
    }
    
    @GetMapping(path = "/players/{username}/getrole")
    String getRole(@PathVariable String username) {
    	return "{\"role\":\"" + Main.userRepo.findByusername(username).getAttachedPlayer().getRole() + "\"}";
    }

    @DeleteMapping(path = "/players/{id}")
    String deletePlayer(@PathVariable int id){
    	Main.playerRepo.deleteById(id);
        return success;
    }
    
    @RequestMapping(method = RequestMethod.DELETE, path = "/players/clearplayers/75362")
    public void clearUsers() {	// removes all objects
        Main.userRepo.deleteAllInBatch();
	}
}