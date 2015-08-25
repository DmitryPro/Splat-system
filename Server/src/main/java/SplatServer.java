import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;


/**
 * Created by Freemahn on 24.08.2015.
 */
class SplatServer {
    public static void main(String[] ar) throws Exception {
        Server server = new Server(8080);
       /* server.setHandler(new DefaultHandler());

        server.start();
        server.join();*/

        int providerPort = 6969;
        String providerAddress = "127.0.0.1";
        //address = "46.101.58.183";//server address
        providerAddress = "198.199.73.244";//provider address

        InetAddress ipAddress = InetAddress.getByName(providerAddress);
        System.out.println("Trying to connect with address " + providerAddress + " and port " + providerPort + "");
        Socket providerSocket = new Socket(ipAddress, providerPort);
        System.out.println("Connected:" + providerSocket.isConnected());

        InputStream sin = providerSocket.getInputStream();
        OutputStream sout = providerSocket.getOutputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(sin));
        DataOutputStream out = new DataOutputStream(sout);

        String line = null;
        sout.write("hello\n".getBytes());
        sout.flush();
        System.out.println("hello sent");

        line = br.readLine();
        System.out.println("answer received" + line);
        while (true) {
            line = br.readLine();
            writeIntoDB(line);
            if (line.equals("exit")) System.exit(0);
            System.out.println("Server answer is: " + line);
            //Sender.addMessage(line);

        }

    }

    private static void writeIntoDB(String line) {
    }


}
