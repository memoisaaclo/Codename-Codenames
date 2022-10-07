package onetoone;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "playerID")
    private Player playerTableForeignKey;
    
    
    
    
    public User(String username, String password) {
    	this.username = username;
    	this.password = password;
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
    
	public boolean validateCredentials(String username, String password) {
    	return this.username.equals(username) && password.equals(password);
    }
    
    public String getUsername() {
    	return username;
    }
    
    public String getPassword() {
    	return password;
    }
    public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}