package codenames;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Player implements Serializable {
    public Player(boolean active) {
        this.active = active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;
    
    @OneToOne
    private User user;
    
    private boolean active;

    public Player() {
    }

    public Game getGame() {
        return game;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
