package codenames;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

/**
 * 
 * @author Ben Kelly
 *
 */

@Entity
public class User {

	/**
	 * ID value in database
	 */
    @Id
    @GeneratedValue
    private Long id;
    
    /**
     * username used for login and searching the object
     */
    //login information:
    public String username;
	/**
	 * password used only for internal comparison
	 */
    public String password;	
	/**
	 * flag if the account is an admin
	 */
    private boolean isAdmin;
    
    // Statistics 
    private Integer logins = 0;
    private Integer gamesPlayed = 0;
    private Integer guessesMade = 0; // Implemented
    private Integer cluesGiven = 0; // Implemented
    private Integer gamesWon = 0; // Implemented
    private Integer correctGuesses = 0; // Implemented

    /**
     * link between User, and player object used for tracking games
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "playerID")
	//@JsonManagedReference	// this breaks everything
    private Player attachedPlayer;
    
    /**
     * constructor for new User
     * @param username of new user
     * @param password of new user
     */
    public User(String username, String password) {
    	this.username = username;
		setPassword(password);
    	
    	logins = 0;
    }
    
    /**
     * default constructor
     */
    public User() {
    	super();
    }

    /**
     * increment statistics
     */
    public void addLogin() {
		logins++;
    }
    
    /**
     * attach a user to a game through a player object
     * @param id game id
     */
    public void addToGame(int id) {
    	Game g = Main.gameRepo.findById(id);
    	
    	Player p = new Player();		// the order of the lines in this method is very deliberate. don't change it
    	this.attachedPlayer = p;
    	p.attachUser(this);
    		
    	p.setGame(g);
    	Main.playerRepo.save(p);
    	g.addPlayer(p);
    	attachedPlayer = p;
    	Main.userRepo.save(this);
    	Main.gameRepo.save(g);
    	gamesPlayed++;
    }
    
    /**
     * remove a user from a game, through attached player
     * @param id of game
     */
    public void removeFromGame(int id) {
    	Game g = Main.gameRepo.findById(id);
    	g.removePlayer(this.attachedPlayer);	// remove player from game
    	
    	Main.playerRepo.delete(attachedPlayer);
    	Main.gameRepo.save(g);
    	attachedPlayer = null;	// remove reference from this class
    	Main.userRepo.save(this);	// save everything
    	// at this point, Player should be orphaned and can be ignored
    }
    
    /**
     * checks username and password
     * @param usr user
     * @return credential validity
     */
	public boolean validateCredentials(User usr) {
    	return this.username.equals(usr.username) && password.equals(usr.password);
    }
    
//    public void setId(Long id) {
//		this.id = id;
//	}
    
    public int getLoginCount() {
    	return logins;
    }

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
    	return username;
    }
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGamesPlayed() {
		return gamesPlayed;
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

//	public void setCluesGiven(Integer cluesGiven) {
//		this.cluesGiven = cluesGiven;
//	}

	public Integer getCorrectGuesses() {
		return correctGuesses;
	}

//	public void setCorrectGuesses(Integer correctGuesses) {
//		this.correctGuesses = correctGuesses;
//	}
	
	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean b) {
		isAdmin = b;
	}

	public Player getAttachedPlayer() {
		return attachedPlayer;
	}


	public void incrementCorrectGuessesMade() { correctGuesses++; }

	public void incrementGuessesMade() { guessesMade++; }

	public void incrementWins() { gamesWon++; }

	public void incrementCluesGiven() { cluesGiven++; }
}
