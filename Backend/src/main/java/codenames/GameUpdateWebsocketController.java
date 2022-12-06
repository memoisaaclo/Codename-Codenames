package codenames;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/games/update/{username}")
@Component
public class GameUpdateWebsocketController {

    // Store all socket sessions and their corresponding username
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(WebSocketChatServer.class);

    /**
     * Called when session is opened
     * @param session
     * @param username
     * @throws IOException
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)
        throws IOException {
        logger.info("Entered into Open");

        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

    }

    /**
     * checks for an "update" message
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, String message) {
    	logger.info(message);
		broadcastToLobby(message, sessionUsernameMap.get(session));
    }

    /**
     * Called when session is closed
     * @param session
     * @throws IOException
     */
    @OnClose
    public void onClose(Session session)
        throws IOException {
        logger.info("Entered into Close");

        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
    }

    /**
     * Called on error
     * @param session
     * @param throwable
     */
    @OnError
    public void OnError(Session session, Throwable throwable) {
        //TODO: Activate error handling stuff here
        logger.info("Entered into Error");
    }

    /**
     * Used to broadcast a message to all users in session.
     * @param message
     * @param usr
     */
    private void broadcastToLobby(String message, String usr) {
    	User user = Main.userRepo.findByusername(usr);
    	Player player = user.getAttachedPlayer();
    	Game game = player.inGame();
    	Set<Player> playerList = game.getPlayers();
    	
    	playerList.forEach((plyr)->{
    		try {
				usernameSessionMap.get(plyr.getUsername()).getBasicRemote().sendText(message);
			} catch (IOException e) {
				logger.info("Exception: " + e.getMessage().toString());
				e.printStackTrace();
			}
    	});
    	
    	List<Session> spectators = GameUpdateSpectatorWebsocketController.getSessionsFromLobbyId(game.getLobby().getIdentity());
    	
    	if(spectators != null) {
	    	spectators.forEach((session)->{
	    		try {
					session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	});
    	}
    }

    protected static void broadcastWinToLobby(String message, int lobbyID) {
        Set<Player> playerList = Main.gameRepo.findById(lobbyID).getPlayers();

        playerList.forEach((plyr)->{
            try {
                usernameSessionMap.get(plyr.getUsername()).getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        List<Session> spectators = GameUpdateSpectatorWebsocketController.getSessionsFromLobbyId(lobbyID);

        if(spectators != null) {
            spectators.forEach((session)->{
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}








