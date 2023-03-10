package codenames;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Player implements Serializable {
	
	/**
	 * UNUSED
	 */
	public Boolean active = true; 	// literally just here as a test
	
	/**
	 * role of the player, default as operative
	 */
	private Role role = Role.OPERATIVE;
	/**
	 * team color, default as red
	 */ 
	private Color team = Color.RED;
	
	/**
	 * id of the object
	 */
    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * join to a game object
     */
    @ManyToOne
    @JoinColumn(name = "game_id")
//    @JsonBackReference
    private Game game;

	/**
     * mapping to a user object
     */
    @OneToOne(mappedBy = "attachedPlayer") //, orphanRemoval = true  <<< add this later?
    @JsonBackReference
    private User user;

    /**
     * default constructor
     */
    public Player() {
    }

    public void attachUser(User user) {
    	this.user = user;
    }

    public User getUser() {
        return user;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(int id) {	// removed for coverage
        this.id = id;
    }

    public String getTeam() {
    	return team.toString();
    }
    
    public void setTeam(Color team) {
    	this.team = team;
    }
    
    public void setRole(Role role) {
    	this.role = role;
    }
    
    public String getRole() {
    	return role.toString();
    }
    
    public String getUsername() {
    	return user.getUsername();
    }
    public Game inGame() {
		return game;
	}

	public void setGame(Game g) {
		game = g;
		
	}
    
}
