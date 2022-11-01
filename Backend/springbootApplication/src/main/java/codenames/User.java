package codenames;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.persistence.*;

/**
 * 
 * @author Ben Kelly
 *
 */

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    
    //login information:
    private String username;
	private String password;	
	private boolean isAdmin;
    // Statistics
    private Integer logins = 0;
    private Integer gamesPlayed = 0;
	private Integer gamesWon = 0;
	private Integer guessesMade = 0;
	private Integer cluesGiven = 0;
	private Integer correctGuesses = 0;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "playerID")
    private Player attachedPlayer;
    
    
    public User(String username, String password) {
    	this.username = username;
		setPassword(password);
    	
    	logins = 0;
    }
    
    public User() {
    	super();
    }

    public void addLogin() {
    	if(logins != null) {
    		logins++;
    	} else {
    		logins = 1;
    	}
    }
    
    public void addToGame(int id) {
    	Game g = Main.gameRepo.findById(id);
    	
    	Player p = new Player();
    	this.attachedPlayer = p;
    	p.attachUser(this);
    	g.addPlayer(p);
    	
    	Main.userRepo.save(this);
    	Main.playerRepo.save(p);
    	Main.gameRepo.save(g);
    }
    
    public void removeFromGame(int id) {
    	Game g = Main.gameRepo.findById(id);
    	g.removePlayer(this.attachedPlayer);	// remove player from game
    	
    	Main.playerRepo.delete(attachedPlayer);
    	Main.gameRepo.save(g);
    	attachedPlayer = null;	// remove reference from this class
    	Main.userRepo.save(this);	// save everything
    	// at this point, Player should be orphaned and can be ignored
    }
    
    public void startGame() {
    	gamesPlayed++;
    }
    
	public boolean validateCredentials(User usr) {
    	return this.username.equals(usr.username) && password.equals(usr.password);
    }
    
    public void setId(Long id) {
		this.id = id;
	}
    
    public int getLoginCount() {
    	return logins;
    }

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
    	return username;
    }
	
	//public String getPassword() {
//		return password;
//	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(Integer gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public Integer getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(Integer gamesWon) {
		this.gamesWon = gamesWon;
	}
	
	public Integer getGuessesMade() {
		return guessesMade;
	}

	public void setGuessesMade(Integer guessesMade) {
		this.guessesMade = guessesMade;
	}

	public Integer getCluesGiven() {
		return cluesGiven;
	}

	public void setCluesGiven(Integer cluesGiven) {
		this.cluesGiven = cluesGiven;
	}

	public Integer getCorrectGuesses() {
		return correctGuesses;
	}

	public void setCorrectGuesses(Integer correctGuesses) {
		this.correctGuesses = correctGuesses;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean b) {
		isAdmin = b;
	}
}