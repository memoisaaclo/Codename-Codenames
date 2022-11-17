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
public class WebSocketServer {

    // Store all socket sessions and their corresponding username
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

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

        String message = "USER: " + username + "has JOINED the Chat";
        broadcast(message);
    }

    /**
     * Called when message is sent
     * @param session
     * @param message
     * @throws IOException
     */
    @OnMessage
    public void onMessage(Session session, String message)
        throws IOException {
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

        String message = "USER: " + username + "has LEFT the Chat";
        broadcast(message);
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
     * Used to DM a user
     * @param username
     * @param message
     */
    private void sendMessageToAParticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info(("Exception: " + e.getMessage().toString()));
            e.printStackTrace();
        }
    }

    /**
     * Used to broadcast a message to all users in session.
     * @param message
     */
    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }
        });
    }
}
