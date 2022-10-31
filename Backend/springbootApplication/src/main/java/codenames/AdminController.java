package codenames;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Benjamin Kelly
 */

@RestController
public class AdminController {

	@SuppressWarnings("unused")
	private String success = "{\"message\":\"success\"}";
    @SuppressWarnings("unused")
	private String failure = "{\"message\":\"failure\"}";
	
	public AdminController() {
		
	}
	
	@GetMapping(path = "/admin/cards/all")
	public @ResponseBody List<Card> getAllCards(){
        return Main.cardRepo.findAll();
    }
	
	@GetMapping(path = "/admin/get/{username}")
	public @ResponseBody String isAdmin(@PathVariable String username) {
		if(Main.userRepo.findByusername(username).isAdmin()) {
			return success;
		}
		return failure;
	}
	
	@PostMapping(path = "/admin/set/{username}")
	public @ResponseBody String setAdmin(@PathVariable String username) {
		Main.userRepo.findByusername(username).setAdmin(true);
		return success;
	}
	
	@PutMapping(path = "/admin/cards/add")
	public String addCard(@RequestBody Card card) {
		if(Main.cardRepo.findByword(card.getWord()) == null) {
			Main.cardRepo.save(card);
			return success;
		}
		return failure;
	}
	
	@PutMapping(path = "/admin/cards/addBulk")
	public String addCard(@RequestBody Card[] card) {
		for (Card c : card) {
			if(Main.cardRepo.findByword(c.getWord()) == null) {
				Main.cardRepo.save(c);
			}
		}
		return success;
	}
	
	@DeleteMapping(path = "/admin/cards/remove")
	public String removeCard(@RequestBody Card card) {
		Card delete;
		if((delete = Main.cardRepo.findByword(card.getWord())) != null) {
			Main.cardRepo.deleteById(delete.getId());
			return success;
		}
		return failure;
	}
	
	@DeleteMapping(path = "/admin/cards/removeall/98765")
	public String removeCards() {
		Main.cardRepo.deleteAll();
		return success;
	}
	
	@GetMapping(path="/admin/paths")
	public String getAllEndpoints() {
		
		return "<style>.tab {tab-size: 16;}</style><pre class = \"tab\">"
				+ "PATH                         : Method : Description <br>"
				+ "/admin/paths                 : GET    : get this list" + "<br>"
				+ "/admin/cards/removeall/98765 : DELETE : remove all cards from the database" + "<br>"
				+ "/admin/cards/remove          : DELETE : remove a single card" + "<br>"
				+ "/admin/cards/add             : PUT    : add a card" + "<br>"
				+ "/admin/cards/addBulk         : PUT    : add array of cards" + "<br>"
				+ "/admin/cards/all             : GET    : list all cards" + "<br>"
				+ "/admin/get/{username}        : GET    : checks if the given user is an admin<br>"
				+ "/admin/set/{username}        : POST   : sets an account as an admin<br><br>"
				
				+ "/games/lobbyinfo             : GET    : return all names and ids of lobbies" + "<br>"
				+ "/games/{id}/words            : GET    : get all words for a given game id" + "<br>"
				+ "/games/delete/{id}           : DELETE : delete the given game" + "<br>"
				+ "/games/{id}                  : GET    : get a single game's data" + "<br>"
				+ "/games                       : GET    : list all data for all games" + "<br>"
				+ "/games/{id}/removePlayer     : DELETE : remove a player from a game" + "<br>"
				+ "/games/{id}/addPlayer        : POST   : add a player to a game" + "<br>"
				+ "/games/{id}/numPlayers       : GET    : get the number of players in a game" + "<br>"
				+ "/games/{id}/generatewords    : GET    : generate the wordset for a game" + "<br>"
				+ "/games/{id}/generateStates   : GET    : generate the card states for a game" + "<br>"
				+ "/games/add                   : POST   : create a game" + "<br>" + "<br>"
				
				+ "/players/{id}                : DELETE : delete a player" + "<br>"
				+ "/players/{id}                :        : broken, don't use" + "<br>"
				+ "/players                     :        : some of these paths should be cleaned up" + "<br>"
				+ "/players/{id}                :        : " + "<br>"
				+ "/players                     :        : no really" + "<br>" + "<br>"
				
				+ "/users/clearUsers/75362      : DELETE : remove all users from the database" + "<br>"
				+ "/users/getallusers           : GET    : get all userdata for all users" + "<br>"
				+ "/users/{username}            : GET    : get all data for a single user" + "<br>"
				+ "/users/login                 : POST   : validate login info for a user" + "<br>"
				+ "/users/register              : POST   : register a user's data"
				+ "</pre>";
	}
	
}