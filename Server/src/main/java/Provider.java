
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Freemahn on 27.08.2015.
 */
class Provider implements Runnable {
    private Logger logger = Logger.getLogger(Provider.class);
    public static final String JSON = "JSON";
    public static final String XML = "XML";
    private String type;
    private String ip;
    private int port;
    private int providerId;
    private Socket socket;
    private static int uid = 1;
    private BufferedReader dataReader;
    InputStream inputStream;
    private static XStream xStream = new XStream();

    /*public Provider(String type, String ip, int port) throws IOException {
        this.type = type;
        this.ip = ip;
        this.port = port;
        providerId = uid++;
        socket = new Socket(InetAddress.getByName(ip), port);
        InputStream sin = socket.getInputStream();
        dataReader = new BufferedReader(new InputStreamReader(sin));
    }*/


    /**
     * Default Provider
     */
    public Provider() {
        type = XML;
        ip = "198.199.73.244";
        port = 6969;
        providerId = uid++;
        try {
            socket = new Socket(InetAddress.getByName(ip), port);
            logger.info("created provider with id[" + providerId + "]");
            inputStream = socket.getInputStream();
            dataReader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            logger.error("cannot connect to provider " + e);
            e.printStackTrace();
        }

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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


    @Override
    public void run() {
        while (true) {
            DataObject message = type.equals(JSON) ? parseJSON() : parseXML();
            if (message == null) continue;
            logger.info("parsed from provider" + message);
            ServerWebSocket.sendData(new ExpandedDataObject(message, providerId));
            logger.info("sent to client " + message);
        }


    }

    DataObject parseXML() {
        DataObject result = null;
        String line = "";
        try {
            while (!line.contains("</DataObject>"))
                line += dataReader.readLine();
        } catch (IOException e) {
            logger.error("Cannot read from socket " + e);
            return null;
        }
        logger.info("received " + line);
        try {
            result = (DataObject) xStream.fromXML(line);
        } catch (Exception e) {
            logger.error("ERROR when parsing xml " + e);
            e.printStackTrace();
        }
        return result;
    }

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

        result = new Gson().fromJson(line, DataObject.class);
        return result;
    }

}
