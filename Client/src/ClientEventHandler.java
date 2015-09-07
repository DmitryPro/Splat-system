import org.apache.log4j.Logger;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;


/**
 * This is client endpoint. Contains implementation of interaction between server and client in client side
 * 
 * @see Client
 * @see ServerWebSocket(in Server)
 * 
 * @author Pavel Gordon
 *
 */
@ClientEndpoint
public class ClientEventHandler
{
    private Logger logger = Logger.getLogger(ClientEventHandler.class);

    /**
     * In other words, queue. Contains data, which must be shown in table/graph. When object is just got from server, it
     * adds here. When object is shown in client, it removes from here.
     */
    public static List<ExpandedDataObject> data = Collections.synchronizedList(new ArrayList<ExpandedDataObject>());


    /**
     * Handles server-socket connection
     * 
     * @param sess session with server
     */
    @OnOpen
    public void onWebSocketConnect(Session sess)
    {
        logger.info("ServerSocket Connected: " + sess);

    }


    /**
     * Handles text messages, received from server
     * 
     * @param message JSON-encoded string representation of ExpandedDataObject
     */
    @OnMessage
    public void onWebSocketText(String message)
    {
        logger.info("Received TEXT message: " + message);
        logger.info("Parsed " + parseJSON(message));
        data.add(parseJSON(message));
    }


    /**
     * Handles closing connection between server and client
     * 
     * @param reason reason of closing connection
     */

    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
        logger.info("Socket Closed: " + reason.getReasonPhrase() + "/" + reason);
    }


    @OnError
    public void onWebSocketError(Throwable cause)
    {
        cause.printStackTrace(System.err);
        logger.error("Socket Error: " + cause);
    }


    /**
     * 
     * @param encodedMessage String JSON-encoded message, which is got from server
     * @return ExpandedDataObject
     */
    ExpandedDataObject parseJSON(String encodedMessage)
    {
        ExpandedDataObject result = new Gson().fromJson(encodedMessage, ExpandedDataObject.class);
        return result;
    }

}