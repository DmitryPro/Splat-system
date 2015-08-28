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


/**
 * Server-side endpoint
 *
 * @author PavelGordon
 */
@ServerEndpoint(value = "/events/")
public class ServerWebSocket {
    private static Logger logger = Logger.getLogger(ServerWebSocket.class);

    /**
     * Contains all connections to the clents
     */
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());


    /**
     * Sends either DataObject or ExpandedDataObject
     * to all currently connected clients
     *
     * @param data DataObject or its instances to send
     * @see DataObject
     * @see ExpandedDataObject
     */
    public static void sendData(DataObject data) {
        try {
            for (Session sc : sessions) {
                sc.getBasicRemote().sendText(data.toString());
            }
        } catch (Exception e) {
            logger.info("Errors while send data" + e);
        }

    }

    /**
     * Sends String message
     * to all currently connected clients
     * Used for testing
     */
    public static void sendData(String data) {

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

    /**
     * Handles client connection.
     * Adds into sessions
     *
     * @param sess - connection to client
     */
    @OnOpen
    public void onWebSocketConnect(Session sess) {
        logger.info("Client Connected: " + sess);
        sessions.add(sess);
    }

    /**
     * Handles receiving text from client
     *
     * @param message message, which is got by server
     */

    @OnMessage
    public void onWebSocketText(String message) {
        System.out.println("Received TEXT message from client: " + message);


    }

    /**
     * Handles closing client-side websocket
     *
     * @param session connection to client
     */

    @OnClose
    public void onWebSocketClose(Session session) {
        System.out.println("Socket Closed: " + session);
        sessions.remove(session);
    }

    /**
     * Handles error in websocket work
     *
     * @param cause reason of the error
     */

    @OnError
    public void onWebSocketError(Throwable cause) {
        cause.printStackTrace(System.err);
    }
}
