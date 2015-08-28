

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class Client {
    private static Logger logger = Logger.getLogger(Client.class);


    static String serverIp = "localhost";
    static int serverPort = 8080;

    public static void main(String[] args) {

        //URI uri = URI.create("ws://" + serverIp + ":" + serverPort + "/events/");
        URI uri = URI.create("ws://localhost:8080/events/");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        Session session = null;
        // Attempt Connect
        System.out.println("trying connectToServer...");
        logger.info("trying connectToServer...");
        try {
            session = container.connectToServer(ClientWebSocket.class, uri);
        } catch (ConnectException e) {
            System.out.println("ConnectException " + e);
            logger.error(e.getMessage());
            System.exit(1);
        } catch (DeploymentException | IOException e) {
            logger.error(e.getMessage());
        }
        System.out.println("connection established");
        logger.info("connection established");
        // Send a message
        //session.getBasicRemote().sendText("Hello");


        while (true) {

            //Long live the client!
        }

        /*if (container instanceof LifeCycle) {
            ((LifeCycle) container).stop();
        }*/
    }

}