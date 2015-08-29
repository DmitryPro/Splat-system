
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * This class represents provider entity.
 * Works in a single thread
 *
 * @author Pavel Gordon
 */
public class Provider implements Runnable {
    private Logger logger = Logger.getLogger(Provider.class);

    /**
     * Type of this provider data representation
     */
    public static final String JSON = "JSON";
    /**
     * Type of this provider data representation
     */
    public static final String XML = "XML";

    /**
     * Provider fields
     */
    private String dataType;
    private String ip;
    private int port;
    private int providerId;
    private Socket socket;

    /**
     * Used to generate provider id
     */
    private static int uid = 1;
    private BufferedReader dataReader;

    //libraries to parse
    private XStream xStream;
    private Gson gson;


    /**
     * Initialises fields with  values given
     * and tries to establish connection via Socket.
     * In case of exception, shuts down(for now).
     * Generates provider id.
     *
     * @param dataType JSON or XML
     * @param ip       ip address of provider
     * @param port     port of provider
     */
    public Provider(String dataType, String ip, int port) {
        this.dataType = dataType;
        this.ip = ip;
        this.port = port;
        providerId = uid++;
        try {
            socket = new Socket(InetAddress.getByName(ip), port);
            InputStream sin = socket.getInputStream();
            dataReader = new BufferedReader(new InputStreamReader(sin));
        } catch (IOException e) {
            logger.error("cannot connect to provider " + e);
            // this.wait();
            e.printStackTrace();
            System.exit(1);//TODO change to reconnect after time

        }
    }


    /**
     * Initialises fields with default values
     * and tries to establish connection via Socket.
     * In case of exception, shuts down(for now).
     * Generates provider id.
     * <p>
     * (@see java.net.Socket)
     */
    public Provider() {
        dataType = XML;
        ip = "198.199.73.244";
        port = 6969;
        providerId = uid++;
        try {
            socket = new Socket(InetAddress.getByName(ip), port);
            logger.info("created provider with id[" + providerId + "]");
            dataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            logger.error("cannot connect to provider " + e);
            e.printStackTrace();
            System.exit(1);//TODO change to reconnect after time
        }

    }

    /**
     * Initiates libraries.
     * Provider lifecycle
     *
     * @see ServerWebSocket#sendData(DataObject)
     */
    public void run() {
        xStream = new XStream();
        gson = new Gson();
        while (true) {
            DataObject message = dataType.equals(JSON) ? parseJSON() : parseXML();
            if (message == null) continue;
            logger.info("parsed from provider" + message);
            ServerWebSocket.sendData(new ExpandedDataObject(message, providerId));
            logger.info("sent to client " + message);
        }


    }

    /**
     * Reads XML-encoded DataObject
     * and parses it using xStream
     *
     * @return result - parsed DataObject
     * or null - if there are problems with either
     * reading from InputStream
     * or parsing with xStream
     */

    DataObject parseXML() {
        DataObject result = null;
        String line = "";
        try {
            while (!line.contains("</DataObject>"))
                line += dataReader.readLine();
            // logger.info("received " + line);
        } catch (IOException e) {
            logger.error("Cannot read from socket " + e);
            return null;
        }

        try {
            result = (DataObject) xStream.fromXML(line);
        } catch (Exception e) {
            logger.error("ERROR when parsing xml " + e);
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * Reads JSON-encoded DataObject
     * and parses it using Gson
     *
     * @return result - parsed DataObject or null
     * - if there are problems with either
     * reading from InputStream
     * or parsing with Gson
     */
    DataObject parseJSON() {
        DataObject result = null;
        String line = "";
        try {
            line = dataReader.readLine();
            // logger.info("received " + line);
        } catch (IOException e) {
            logger.error("Cannot read from socket " + e);
            return null;
        }

        result = gson.fromJson(line, DataObject.class);
        return result;
    }

    /*Getters and setters*/
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getType() {
        return dataType;
    }

    public void setType(String type) {
        this.dataType = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }
}
