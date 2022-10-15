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
    // Statistics
    private Integer logins;
    private Integer gamesPlayed;
	private Integer gamesWon;

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
    
    public void attachPlayer(Player p) {
    	this.attachedPlayer = p;
    	p.setUserId(id);
    }
    
    public void startGame() {
    	gamesPlayed++;
    }
    
	public boolean validateCredentials(String username, String bs) {
    	return this.username.equals(username) && password.equals(bs);
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
	
	public String getPassword() {
		return password;
	}
	
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
}