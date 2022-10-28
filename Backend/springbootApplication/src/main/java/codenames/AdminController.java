package codenames;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@PutMapping(path = "/admin/cards/add")
	public String addCard(@RequestBody Card card) {
		if(Main.cardRepo.findByword(card.getWord()) == null) {
			Main.cardRepo.save(card);
			return success;
		}
		return failure;
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
				+ "/admin/paths                 : get this list" + "<br>" 
				+ "/admin/cards/removeall/98765 : remove all cards from the database" + "<br>"
				+ "/admin/cards/remove          : remove a single card" + "<br>"
				+ "/admin/cards/add             : add a card" + "<br>"
				+ "/admin/cards/all             : list all cards" + "<br>" + "<br>"
				
				+ "/games/lobbyinfo             : return all names and ids of lobbies" + "<br>"
				+ "/games/{id}/words            : get all words for a given game id" + "<br>"
				+ "/games/delete/{id}           : delete the given game" + "<br>"
				+ "/games/{id}                  : get a single game's data" + "<br>"
				+ "/games                       : list all data for all games" + "<br>"
				+ "/games/{id}/removePlayer     : remove a player from a game" + "<br>"
				+ "/games/{id}/addPlayer        : add a player to a game" + "<br>"
				+ "/games/{id}/numPlayers       : get the number of players in a game" + "<br>"
				+ "/games/{id}/generatewords    : generate the wordset for a game" + "<br>"
				+ "/games/add                   : create a game" + "<br>" + "<br>"
				
				+ "/players/{id}                : delete a player" + "<br>"
				+ "/players/{id}                : broken, don't use" + "<br>"
				+ "/players                     : some of these paths should be cleaned up" + "<br>"
				+ "/players/{id}                : " + "<br>"
				+ "/players                     : no really" + "<br>" + "<br>"
				
				+ "/users/clearUsers/75362      : remove all users from the database" + "<br>"
				+ "/users/getallusers           : get all userdata for all users" + "<br>"
				+ "/users/{username}            : get all data for a single user" + "<br>"
				+ "/users/login                 : validate login info for a user" + "<br>"
				+ "/users/register              : register a user's data"
				+ "</pre>";
	}
	
}