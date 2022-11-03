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
	@PutMapping(path = "/admin/cards/add/{card}")
	public String addCardpath(@RequestBody String card) {
		Card c = new Card(card);
		if(Main.cardRepo.findByword(card) == null) {
			Main.cardRepo.save(c);
			return success;
		}
		return failure;
	}
	
	@PutMapping(path = "/admin/cards/addbulk")
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
	
	@DeleteMapping(path = "/admin/cards/remove/{card}")
	public String removeCardPath(@PathVariable String card) {
		Card delete;
		if((delete = Main.cardRepo.findByword(card)) != null) {
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
		
		return  "<style>.tab {tab-size: 16;}</style><pre class = \"tab\">"
				+ "PATH                                : Method : Description <br>"
				+ "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~<br>"
				+ "/admin/paths                        : GET    : get this list<br>"
				+ "/admin/cards/removeall/98765        : DELETE : remove all cards from the database<br>"
				+ "/admin/get/{username}               : GET    : checks if the given user is an admin<br>"
				+ "/admin/set/{username}               : POST   : sets an account as an admin<br><br>"
				+ "/admin/cards/remove                 : DELETE : remove a single card<br>"
				+ "/admin/cards/add/{card}             : PUT    : add a card<br>"
				+ "/admin/cards/addBulk                : PUT    : add array of cards<br>"
				+ "<a href = http://10.90.75.56:8080/admin/cards/all>/cards/all</a>"
				+ "                          : GET    : list all cards<br>"
				+ "/admin/cards/remove/{card}          : DELETE : deletes the given card<br>"
				+ "/admin/cards/addBulk                : POST   : creates a json list of cards<br><br>"
				
				
				+ "<a href = http://10.90.75.56:8080/games>/games</a>"
				+ "                              : GET    : list all data for all games<br>"
				+ "/games/add                          : POST   : create a game<br>"
				+ "<a href = http://10.90.75.56:8080/games/lobbyinfo>/games/lobbyinfo</a>"
				+ "                    : GET    : return all names and ids of lobbies<br>"
				+ "/games/removeall/98765              : DELETE : delete all lobbies<br>"
				+ "/games/{id}                         : GET    : get a single game's data<br>"
				+ "/games/{id}/words                   : GET    : get all words for a given game id<br>"
				+ "/games/{id}/delete                  : DELETE : delete the given game<br>"
				+ "/games/{id}/colors                  : GET    : get all colors for a given game id<br>"
				+ "/games/{id}/clue                    : GET    : get current clue for a given game id" + "<br>"
				+ "/games/{id}/turncolor               : GET    : return current turn color for a given game id" + "<br>"
				+ "/games/{id}/clue/{clue}/{numGuesses}: PUT    : Send current clue and clue num for a given game id" + "<br>"
				+ "/games/{id}/guess/{card_position}   : PUT    : Send a new guess to a given game id" + "<br>"
				+ "/games/{id}/isrevealed              : GET    : get all revealed stats for a given game id<br>"
				+ "/games/{id}/removeplayer/{username} : DELETE : remove a player from a game<br>"
				+ "/games/{id}/addplayer/{username}    : POST   : add a player to a game<br>"
				+ "/games/{id}/numplayers              : GET    : get the number of players in a game<br>"
				+ "/games/{id}/generatewords           : GET    : generate the wordset for a game<br>"
				+ "/games/{id}/generateStates          : GET    : generate the card states for a game<br>"
				+ "/games/{id}/players                 : GET    : get all players and info for the game<br><br>"
				
				+ "<a href = http://10.90.75.56:8080/players>/players</a>"
				+ "                            : GET    : Get all players<br>"
				+ "/players/create                     : POST   : create a given player from JSON<br>"
				+ "/players/clearplayers/75362         : DELETE : clears all players<br>"
				+ "/players/{id}                       : GET    : get all data about a specific player<br>"
				+ "/players/{id}/delete                : DELETE : deletes a player<br>"
				+ "/players/{username}/setteam/{team}  : POST   : sets a players team<br>"
				+ "/players/{username}/setrole/{role}  : POST   : sets a players role<br>"
				+ "/players/{username}/getteam         : GET    : returns the players current team<br>"
				+ "/players/{username}/getrole         : GET    : returns the players current role<br><br>"
				
				
				
				+ "/users/clearusers/75362             : DELETE : remove all users from the database<br>"
				+ "<a href = http://10.90.75.56:8080/users/getallusers>/users/getallusers<a>"
				+ "                  : GET    : get all userdata for all users<br>"
				+ "/users/{username}                   : GET    : get all data for a single user<br>"
				+ "/users/login                        : POST   : validate login info for a user<br>"
				+ "/users/register                     : POST   : register a user's data<br><br><br><br><br>"
				+ "</pre>";
	}
	
}