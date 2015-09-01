import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;


/**
 * A simple client.
 * This class is responsible for connecting to server 
 * and getting data from it.
 * For displaying data, (@see FrameDisplayer)  
 *
 * @author Pavel Gordon
 */
public class Client implements Runnable{
   // private static Logger logger = Logger.getLogger(Client.class);

    
    /**
     * TODO move into config file
     */

    static String serverIp = "localhost";
    static int serverPort = 8080;

	@Override
	public void run() {
		 URI uri = URI.create("ws://" + serverIp + ":" + serverPort + "/events/");
	        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
	        Session session = null;
	        // Attempt Connect
	        System.out.println("trying connectToServer...");
	    //    logger.info("trying connectToServer...");
	        try {
	            session = container.connectToServer(ClientWebSocket.class, uri);
	        } catch (ConnectException e) {
	            System.out.println("ConnectException1");
	            //logger.error(e.getMessage());
	            System.exit(1);
	        } catch (DeploymentException | IOException e) {
	        	System.out.println("ConnectException2 " + e);
	            //logger.error(e.getMessage());
	        }
	        System.out.println("connection established");
	        //logger.info("connection established");


	        while (true) {

	            //Long live the client!
	        }

	        /*if (container instanceof LifeCycle) {
	            ((LifeCycle) container).stop();
	        }*/
		
	}

}