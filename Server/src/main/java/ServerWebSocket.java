import org.apache.log4j.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(value = "/events/")
/**
 * Created by Freemahn on 26.08.2015.
 */
public class ServerWebSocket {
    private static Logger logger = Logger.getLogger(ServerWebSocket.class);
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());


    /**
     * Sends either DataObject or ExpandedDataObject
     * to all currently connected clients
     */
    public static void sendData(DataObject data) {
        try {
            for (Session sc : sessions) {
                sc.getBasicRemote().sendText(data.toString());
            }
        } catch (Exception e) {
            // onWebSocketError(e);
        }

    }

    public static void sendData(String data) {
        //logger.info("into send data. sessions size" + sessions.size());

        try {
            for (Session sc : sessions) {
                //logger.info("trying to send data" + data);
                sc.getBasicRemote().sendText(data);
               // logger.info("sent data " + data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnOpen
    public void onWebSocketConnect(Session sess) {
        // System.out.println("Client Connected: " + sess);
        /*try {
            //sess.getBasicRemote().sendText("HELLO");
        } catch (IOException e) {
             logger.error(e);
           // e.printStackTrace();
        }*/
        logger.info("Client Connected: " + sess);
        sessions.add(sess);
    }


    @OnMessage
    public void onWebSocketText(String message) {
        System.out.println("Received TEXT message from client: " + message);


    }

    @OnClose
    public void onWebSocketClose(Session session) {
        System.out.println("Socket Closed: " + session);
        sessions.remove(session);
    }

    @OnError
    public void onWebSocketError(Throwable cause) {
        cause.printStackTrace(System.err);
    }
}
