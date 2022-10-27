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
		if(Main.cardRepo.findById(card.getId()) == null) {
			Main.cardRepo.save(card);
			return success;
		}
		return failure;
	}
	
	@DeleteMapping(path = "/admin/cards/remove")
	public String removeCard(@RequestBody Card card) {
		if(Main.cardRepo.findById(card.getId()) != null) {
			Main.cardRepo.deleteByword(card.getWord());
			return success;
		}
		return failure;
	}
	
}