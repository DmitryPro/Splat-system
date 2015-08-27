import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by snitch on 19.08.15.
 */
public class Sender {

    private int port;
    private Logger logger= Logger.getLogger(Sender.class.getName());
    public static Generator generator = new Generator();
    private static Gson gson = new Gson();
    private static XStream xStream = new XStream();


    /**
     * @param port1
     */
    public void setPort(int port1) {
        this.port = port1;
    }

    public void run() throws Throwable{
        ServerSocket serverSocket = new ServerSocket(port);
        logger.info("Started at " + port + " port");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println(socket.getInetAddress().toString());
            logger.info("New connection from" + socket.getInetAddress().toString());
            new Thread(new SocketProcessor(socket,
                    PropertiesLoader.getInstance().getValue("type").toUpperCase().equals("XML")))
                    .start();
        }

    }

    private static class SocketProcessor implements Runnable {

        private Socket socket;
        private OutputStream outputStream;
        private boolean type;
        private int eps = 2 * Integer.parseInt(PropertiesLoader.getInstance().getValue("timeEps"));
        private Random random = new Random();

        /**
         * @param socket
         * @param type
         * @throws Throwable
         */
        private SocketProcessor(Socket socket, boolean type) throws Throwable{

            this.socket = socket;
            this.outputStream = socket.getOutputStream();
            this.type = type;
            System.out.println(PropertiesLoader.getInstance().getValue("type"));
        }

        public void run() {
            try {
                while (true) {
                    DataObject dataObject = generator.next();
                    if(type) {
                        dataObject = (DataObject) xStream.fromXML(xStream.toXML(dataObject));
                        outputStream.write((xStream.toXML(dataObject)+"\n").getBytes());
                    } else {
                        outputStream.write((gson.toJson(dataObject) + "\n").getBytes());
                    }
                    outputStream.flush();
                    Thread.sleep(1000L + random.nextInt(eps));
                }
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            System.err.println("Client processing finished");
        }

    }

}
