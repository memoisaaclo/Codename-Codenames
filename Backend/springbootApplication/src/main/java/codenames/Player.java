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

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToOne(mappedBy = "attachedPlayer") //, orphanRemoval = true  <<< add this later?
    private User user;

    private Long userId;

    private boolean active;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Player() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {	// attach game to this object, and increment statistics
        this.game = game;
        User u = Main.userRepo.findById(userId).get();
        u.startGame();
        Main.userRepo.save(u);
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

	public void setUserId(Long UserId) {
		userId = UserId;
	}
}
