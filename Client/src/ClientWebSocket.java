import org.apache.log4j.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class ClientWebSocket {
    private  Logger logger = Logger.getLogger(ClientWebSocket.class);
    @OnOpen
    public void onWebSocketConnect(Session sess) {
        //System.out.println("Socket Connected: " + sess);
        logger.info("ClientSocket Connected: " + sess);
       /* try {
            sess.getBasicRemote().sendText("Hi there, i am Client");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @OnMessage
    public void onWebSocketText(String message) {
         System.out.println("Received TEXT message: " + message);
        logger.info("Received TEXT message: " + message);
        //draw graphic
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason) {
        System.out.println("Socket Closed: " + reason.getReasonPhrase() + "/" + reason);
    }

    @OnError
    public void onWebSocketError(Throwable cause) {
        cause.printStackTrace(System.err);
    }
}