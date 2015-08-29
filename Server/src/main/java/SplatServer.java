import javax.websocket.server.ServerContainer;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;


/**
 * This class is an entry point to server.
 * When started, it creates a WebSocket endpoint,
 * which provides connection to server (@see ServerWebSocket)
 * and creates ProvidersHolder instance in another thread (@see ProvidersHolder)
 *
 * @author Pavel Gordon
 *
 */

public class SplatServer {
    private static Logger logger = Logger.getLogger(SplatServer.class);
    /*TODO  move to properties*/
    private static int DEFAULT_PORT = 8080;
    private static int DEFAULT_TIMEOUT = 10_000;
    public static void main(String[] args) {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(DEFAULT_PORT);
        server.addConnector(connector);
        connector.setIdleTimeout(DEFAULT_TIMEOUT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        try {
            // Initialize javax.websocket layer
            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(context);
            // Add WebSocket endpoint to javax.websocket layer
            wscontainer.addEndpoint(ServerWebSocket.class);

            //starts connection to providers in new thread
            new Thread(new ProvidersHolder()).start();
            server.start();
            server.dump(System.err, "tag");
            server.join();

        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }


}