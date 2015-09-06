import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * Server-side endpoint
 *
 * @author PavelGordon
 */
@ServerEndpoint(value = "/events/")
public class ServerEventHandler
{
    private static Logger logger = Logger.getLogger(ServerEventHandler.class);

    /**
     * Contains all connections to the clients
     */
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());


    /**
     * Sends either DataObject or ExpandedDataObject to all currently connected clients
     * If sending  failes
     * @param data DataObject or its instances to send
     * @see DataObject
     * @see ExpandedDataObject
     */
    public static void sendData(DataObject data)
    {
        try
        {
            for (Session sc : sessions)
            {
                // sc.getBasicRemote().sendText(data.toString());
                Future result = sc.getAsyncRemote().sendText(data.toString());
                result.get();

            }

        }
        catch (InterruptedException | ExecutionException e)
        {
           // e.printStackTrace();
            logger.error("Errors while send data" + e);
        }

    }


    /**
     * Sends string message to all currently connected clients.
     * Used for testing
     */
    public static void sendData(String data)
    {

        try
        {
            for (Session sc : sessions)
            {
                // logger.info("trying to send data" + data);
                sc.getBasicRemote().sendText(data);
                // logger.info("sent data " + data);
            }
        }
        catch (Exception e)
        {
           // e.printStackTrace();
            logger.error(e);

        }

    }


    /**
     * Handles client connection.
     * Adds new connection in sessions set
     *
     * @param session - connection to client
     */
    @OnOpen
    public void onWebSocketConnect(Session session)
    {
       // System.out.println("Socket Opened: " + sess);
        logger.info("Client Connected: " + session);
        sessions.add(session);
    }


    /**
     * Handles receiving text from client
     *
     * @param message String message from client
     */

    @OnMessage
    public void onWebSocketText(String message)
    {
      //  System.out.println("Received TEXT message from client: " + message);
        logger.info("Received TEXT message from client: " + message);

    }


    /**
     * Handles closing client-side websocket
     *
     * @param session connection to client
     */

    @OnClose
    public void onWebSocketClose(Session session)
    {
        //System.out.println("Socket Closed: " + session);
        logger.info("Socket Closed: " + session);
        sessions.remove(session);
    }


    /**
     * Handles error in websocket work
     *
     * @param cause reason of the error
     */

    @OnError
    public void onWebSocketError(Throwable cause)
    {

        cause.printStackTrace(System.err);
        logger.error(cause);
    }
}
