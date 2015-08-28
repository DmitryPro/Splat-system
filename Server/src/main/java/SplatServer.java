import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import org.apache.log4j.Logger;
import com.google.gson.Gson;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;


/**
 * Created by Freemahn on 24.08.2015.
 */
class SplatServer {
    private static Logger logger = Logger.getLogger(SplatServer.class.getName());

    public static void main(String[] ar) throws Exception {
        Gson gson = new Gson();
        Server server = new Server(8080);
        server.setHandler(new DefaultHandler());

        server.start();
        server.join();
        logger.info("Init");
        int providerPort = 6969;
        String providerAddress = "127.0.0.1";
        //address = "46.101.58.183";//server address
        providerAddress = "198.199.73.244";//provider address

        InetAddress ipAddress = InetAddress.getByName(providerAddress);
        System.out.println("Trying to connect with address " + providerAddress + " and port " + providerPort + "");
        Socket providerSocket = new Socket(ipAddress, providerPort);
        System.out.println("Connected:" + providerSocket.isConnected());
        logger.info("Trying to connect with address " + providerAddress + " and port " + providerPort + "");
        InputStream sin = providerSocket.getInputStream();
        OutputStream sout = providerSocket.getOutputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(sin));
        String line = null;
        sout.write("hello\n\n".getBytes());
        sout.flush();
        System.out.println("hello sent");
        logger.info("hello sent");

        line = br.readLine();
        System.out.println("answer received " + line);
        logger.info("answer received " + line);
        while (true) {
            line = br.readLine();
            if (line == null) continue;
            System.out.println("Server answer is: " + line);
            logger.info("answer received " + line);
           /*
            Gson gson = new Gson();
            DataObject dataObject = new DataObject(1, 2);
            String mess = gson.toJson(dataObject);
            System.out.println(mess);
            DataObject res = gson.fromJson(mess, DataObject.class);*/
            DataObject result = gson.fromJson(line, DataObject.class);
            System.out.println(result);
            writeIntoDB(line);
        }

    }

    private static void writeIntoDB(String line) {
    }


}
