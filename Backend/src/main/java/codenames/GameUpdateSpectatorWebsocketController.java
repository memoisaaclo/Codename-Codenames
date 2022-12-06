package codenames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/games/spectate/{id}")
@Component
public class GameUpdateSpectatorWebsocketController {

    // Store all socket sessions and their corresponding username
    private static Map<Integer, List<Session>> idSessionMap = new Hashtable<>();
    private static Map<Session, Integer> sessionIdMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(WebSocketChatServer.class);

    /**
     * Called when session is opened
     * @param session
     * @param username
     * @throws IOException
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") int id)
        throws IOException {
        logger.info("Entered into Open");
        
        List<Session> l;
        
        if(idSessionMap.containsKey(id)) {
        	l = idSessionMap.get(id);
        } else {
        	l = new ArrayList<>();
        	
        }
        
    	l.add(session);
        idSessionMap.put(id, l);
    	sessionIdMap.put(session, id);

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

        Integer id = sessionIdMap.get(session);
        idSessionMap.remove(id);
        sessionIdMap.remove(session);
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

    public static List<Session> getSessionsFromLobbyId(Integer id) {
		return idSessionMap.get(id);
    	
    }
}
