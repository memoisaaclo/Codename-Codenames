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
		return "/admin/paths 					: get this list" + "\n" 
				+ "/admin/cards/removeall/98765 : remove all cards from the database" + "\n"
				+ "/admin/cards/remove 			: remove a single card" + "\n"
				+ "/admin/cards/add				: add a card" + "\n"
				+ "/admin/cards/all				: list all cards" + "\n" + "\n"
				
				+ "/games/lobbyinfo				: return all names and ids of lobbies" + "\n"
				+ "/games/{id}/words			: get all words for a given game id" + "\n"
				+ "/games/delete/{id}			: delete the given game" + "\n"
				+ "/games/{id}					: get a single game's data" + "\n"
				+ "/games						: list all data for all games" + "\n"
				+ "/games/{id}/removePlayer		: remove a player from a game" + "\n"
				+ "/games/{id}/addPlayer		: add a player to a game" + "\n"
				+ "/games/{id}/numPlayers		: get the number of players in a game" + "\n"
				+ "/games/add					: create a game" + "\n" + "\n"
				
				+ "/players/{id}				: delete a player" + "\n"
				+ "/players/{id}				: broken, don't use" + "\n"
				+ "/players						: some of these paths should be cleaned up" + "\n"
				+ "/players/{id}				: " + "\n"
				+ "/players						: no really" + "\n" + "\n"
				
				+ "/users/clearUsers/75362		: remove all users from the database" + "\n"
				+ "/users/getallusers			: get all userdata for all users" + "\n"
				+ "/users/{username}			: get all data for a single user" + "\n"
				+ "/users/login						: validate login info for a user" + "\n"
				+ "/users/register				: register a user's data";
				
	}
	
}