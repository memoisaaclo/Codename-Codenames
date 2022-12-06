package codenames;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    /**
     * construct a controller
     */
    public PlayerController(PlayerRepository playerRepository) {
        Main.playerRepo = playerRepository;
    }

    /**
     * gets all players
     * @return all players
     */
    @GetMapping(path = "/players")
    List<Player> getAllPlayers(){
        return Main.playerRepo.findAll();
    }

    /**
     * finds a player by ID
     * @param id player id
     * @return the JSON representation of a given player
     */
    @GetMapping(path = "/players/{id}")
    Player getPlayerById( @PathVariable int id){
        return Main.playerRepo.findById(id);
    }

    /**
     * creates a new player given in a JSON object
     * @param player player
     * @return success or failure message
     */
    @PostMapping(path = "/players/create")
    String createPlayer(@RequestBody Player player){
        if (player == null)
            return failure;
        Main.playerRepo.save(player);
        return success;
    }
    
    /**
     * sets a player's team
     * 
     * @param username of user
     * @param team color of user
     * @return success or failure message
     */
    
    @PostMapping(path = "/players/{username}/setteam/{team}")
    String setTeam(@PathVariable String username, @PathVariable String team) {
    	User usr = Main.userRepo.findByusername(username);
    	if(usr==null)return "{\"message\":\"could not find player\"}";
    	if(!(team.equalsIgnoreCase("red") || team.equalsIgnoreCase("blue")))return "{\"message\":\"invalid team color\"}";
    	usr.getAttachedPlayer().setTeam(team.equalsIgnoreCase("red") ? Color.RED : Color.BLUE);
    	Main.userRepo.save(usr);
    	Main.playerRepo.save(usr.getAttachedPlayer());
    	return success;
    }
    
    /**
     * set a player's role
     * @param username of user
     * @param role of user
     * @return success or failure
     */
    @PostMapping(path = "/players/{username}/setrole/{role}")
    String setRole(@PathVariable String username, @PathVariable String role) {
    	User usr = Main.userRepo.findByusername(username);
        if (usr == null) return "{\"message\":\"could not find player\"}";
        Player player = usr.getAttachedPlayer();
        role = role.toLowerCase();

    	if (!(role.equals("spymaster") || role.equals("operative"))) return "{\"message\":\"invalid role name\"}";

        // Verify and set Player role
        if (role.equals("spymaster")) {

            // Add double Spymaster verification
            for (Player other : player.inGame().getPlayers()) {
                if (other.getRole().equalsIgnoreCase("spymaster") && (player.getTeam().equals(other.getTeam())))
                    return "{\"message\":\"invalid role, team spymaster already selected\"}";
            }

            player.setRole(Role.SPYMASTER);
        } else { // role.equals("operative")

            player.setRole(Role.OPERATIVE);
        }

    	Main.userRepo.save(usr);
    	Main.playerRepo.save(player);
    	return success;
    }
    
    /**
     * returns the team of a given player
     */
    @GetMapping(path = "/players/{username}/getteam")
    String getTeam(@PathVariable String username) {
    	return "{\"team\":\"" + Main.userRepo.findByusername(username).getAttachedPlayer().getTeam() + "\"}";
    }
    
    /**
     * get a player's role
     * @return the role of a given player
     */
    @GetMapping(path = "/players/{username}/getrole")
    String getRole(@PathVariable String username) {
    	return "{\"role\":\"" + Main.userRepo.findByusername(username).getAttachedPlayer().getRole() + "\"}";
    }

    /**
     * delete a player by id
     * @param id player id
     * @return success or failure message
     */
    @DeleteMapping(path = "/players/{id}")
    String deletePlayer(@PathVariable int id){
    	Main.playerRepo.deleteById(id);
        return success;
    }
    
    /**
     * deletes all players from the database
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/players/clearplayers/75362")
    public void clearUsers() {	// removes all objects
        Main.userRepo.deleteAllInBatch();
	}
}