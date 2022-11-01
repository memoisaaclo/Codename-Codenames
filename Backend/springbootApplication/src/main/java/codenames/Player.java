package codenames;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Player implements Serializable {
	
	private Role role;
	private Color team;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToOne(mappedBy = "attachedPlayer") //, orphanRemoval = true  <<< add this later?
    private User user;

    public Player() {
    }

    public void attachUser(User user) {
    	this.user = user;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeam() {
    	return team.toString();
    }
    
    public void setTeam(Color team) {
    	this.team = team;
    }
    
    public String getRole() {
    	return role.toString();
    }
    
    public String getUsername() {
    	return user.getUsername();
    }
    
}
