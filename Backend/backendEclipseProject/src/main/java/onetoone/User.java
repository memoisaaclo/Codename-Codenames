package onetoone;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    
    //login information:
    private String username;
    private String passwordHash;	// side note, are we going to bother actually hashing passwords?
    
    //statistics:
    private Integer logins;
    private Integer gamesWon;
    private Integer gamesPlayed;
    private Integer guessesMade;
    private Integer correctGuesses;
    private Integer cluesGiven;

    //@OneToMany(cascade = CascadeType.ALL)
    //@JoinColumn(name = "gameID")
    private Game gameHistory;


    public User(String username, String password) {
    	this.username = username;
    	this.passwordHash = password;
    }
    
    public boolean validateCredentials(String username, String password) {
    	return this.username.equals(username) && passwordHash.equals(password);
    }
    
}