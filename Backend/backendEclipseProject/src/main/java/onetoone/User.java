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

//    @OneToOne(cascade = CascadeType.ALL);
//    @JoinColumn(name = "playerID")
//    private Player playerTableForeignKey;
    

    public User(String username, String password) {
    	this.username = username;
    	this.passwordHash = password;
    }
    
    public boolean validateCredentials(String username, String password) {
    	return this.username.equals(username) && passwordHash.equals(password);
    }
    
    
    
}