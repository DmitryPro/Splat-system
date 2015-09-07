package com.splat.server;


import com.google.gson.Gson;
import com.splat.provider.DataObject;
import com.splat.provider.ExpandedDataObject;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


/**
 * This class represents provider entity. Works in a single thread
 *
 * @author Pavel Gordon
 */
public class Provider implements Runnable
{
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
     * Type of data which provider sends. Can be "JSON" or "XML:
     * 
     * @see Provider#JSON
     * @see Provider#XML
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

    /**
     * XStream library for parsing XML
     */
    private XStream xStream;

    /**
     * Gson library for parsing JSON
     */
    private Gson gson;


    /**
     * Initializes fields with values given and tries to establish connection via Socket. In case of exception, shuts
     * down(for now). Generates provider id. Initiates libraries.
     *
     * @param dataType JSON or XML
     * @param ip ip address of provider
     * @param port port of provider
     */
    public Provider(String dataType, String ip, int port)
    {
        this.dataType = dataType;
        this.ip = ip;
        this.port = port;
        providerId = uid++;
        xStream = new XStream();
        gson = new Gson();
        try
        {
            socket = new Socket(InetAddress.getByName(ip), port);
            InputStream sin = socket.getInputStream();
            dataReader = new BufferedReader(new InputStreamReader(sin));
        }
        catch (IOException e)
        {
            logger.error("cannot connect to provider " + e);

            e.printStackTrace();
            System.exit(1);

        }
    }


    /**
     * Initializes fields with default values and tries to establish connection via Socket. In case of exception, shuts
     * down(for now). Generates provider id. Initiates libraries.
     * <p>
     * (@see java.net.Socket)
     */
    public Provider()
    {
        dataType = XML;
        ip = "198.199.73.244";
        port = 6969;
        providerId = uid++;
        xStream = new XStream();
        gson = new Gson();
        try
        {
            socket = new Socket(InetAddress.getByName(ip), port);
            logger.info("created provider with id[" + providerId + "]");
            dataReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e)
        {
            logger.error("cannot connect to provider " + e);
            e.printStackTrace();
            System.exit(1);// TODO change to reconnect after time
        }

    }


    /**
     * Provider lifecycle
     *
     * @see ServerEventHandler#sendData(DataObject)
     */
    public void run()
    {

        while (true)
        {
            DataObject message = dataType.equals(JSON) ? parseJSON() : parseXML();
            if (message == null)
                continue;
            // logger.info("parsed from provider" + message);
            ServerEventHandler.sendData(encodeJSON(new ExpandedDataObject(message, providerId)));
            logger.info("sent to client " + message);
        }

    }


    /**
     * Reads XML-encoded com.splat.provider.DataObject and parses it using xStream
     *
     * @return result - parsed com.splat.provider.DataObject or null - if there are problems with either reading from
     *         InputStream or parsing with xStream
     */

    DataObject parseXML()
    {
        DataObject result = null;
        String line = "";
        try
        {
            while (!line.contains("</com.splat.provider.DataObject>"))
                line += dataReader.readLine();
            // logger.info("received " + line);
        }
        catch (IOException e)
        {
            logger.error("Cannot read from socket " + e);
            e.printStackTrace();
            return null;
        }

        try
        {
            result = (DataObject) xStream.fromXML(line);
        }
        catch (Exception e)
        {
            logger.error("ERROR when parsing xml " + e);
            e.printStackTrace();
            return null;
        }
        return result;
    }


    /**
     * Reads JSON-encoded com.splat.provider.DataObject and parses it using Gson
     *
     * @return result - parsed com.splat.provider.DataObject or null - if there are problems with either reading from
     *         InputStream or parsing with Gson
     */
    DataObject parseJSON()
    {
        DataObject result = null;
        String line = "";
        try
        {
            line = dataReader.readLine();
            // logger.info("received " + line);
        }
        catch (IOException e)
        {
            logger.error("Cannot read from socket " + e);
            return null;
        }

        result = gson.fromJson(line, DataObject.class);
        return result;
    }


    /**
     * Encodes ExpandedDataObject to JSON
     * @param dataObject ExpandedDataObject to be encoded
     * @return JSON-encoded string representation of ExpandedDataObject
     */
    String encodeJSON(ExpandedDataObject dataObject)
    {
        return gson.toJson(dataObject);
    }

}
