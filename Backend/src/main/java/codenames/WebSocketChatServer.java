package codenames;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketChatServer {

    // Store all socket sessions and their corresponding username
    private static final Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static final Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(WebSocketChatServer.class);

    /**
     * Called when session is opened
     * @param session session to open
     * @param username of user
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        logger.info("Entered into Open");

        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        String message = "USER: " + username + "has JOINED the Chat";
        broadcast(message);
    }

    /**
     * Called when message is sent
     * @param session session
     * @param message to be sent
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        logger.info("Entered into Message: Got Message:" + message);
        String username = sessionUsernameMap.get(session);

        if (message.startsWith("@")) // It is a direct message in the format "@username <message>"
        {
            String destUsername = message.split(" ")[0].substring(1); // Supposedly don't do this
            sendMessageToAParticularUser(destUsername, "[DM] " + username + ": " + message);
            sendMessageToAParticularUser(username, "[DM] " + username + ": " + message);
        }
        else // Everyone gets it
        {
            broadcast(username + ": " + message);
        }
    }

    /**
     * Called when session is closed
     * @param session that is closing
     */
    @OnClose
    public void onClose(Session session) {
        logger.info("Entered into Close");

        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        String message = "USER: " + username + "has LEFT the Chat";
        broadcast(message);
    }

    /**
     * Called on error
     * @param session session :>
     * @param throwable to be thrown
     */
    @OnError
    public void OnError(Session session, Throwable throwable) {
        //TODO: Activate error handling stuff here
        logger.info("Entered into Error");
    }

    /**
     * Used to DM a user
     * @param username to receive message
     * @param message to be sent
     */
    private void sendMessageToAParticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info(("Exception: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * Used to broadcast a message to all users in session.
     * @param message to be sent to everyone
     */
    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("Exception: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
