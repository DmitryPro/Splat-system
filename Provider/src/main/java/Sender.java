import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by snitch on 19.08.15.
 */
public class Sender {

    public void run() throws Throwable{
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {

            Socket socket = serverSocket.accept();
            System.out.println("New client");
            new Thread(new SocketProcessor(socket)).start();
        }

    }

    private static class SocketProcessor implements Runnable {

        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        private SocketProcessor(Socket socket) throws Throwable{

            this.socket = socket;
            this.inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();
        }

        public void run() {
            try {
                readInputHeaders();
                writeResponse("<html><body><h1>We have a contact!!!!!!!!!</h1></body></html>");
            } catch (Throwable t) {
                /*do nothing*/
            } finally {
                try {
                    socket.close();
                } catch (Throwable t) {
                    /*do nothing*/
                }
            }
            System.err.println("Client processing finished");
        }

        private void writeResponse(String s) throws Throwable {
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Server: YarServer/2009-09-09\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + s.length() + "\r\n" +
                    "Connection: close\r\n\r\n";
            String result = response + s;
            outputStream.write(result.getBytes());
            outputStream.flush();
        }

        private void readInputHeaders() throws Throwable {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while(true) {
                String s = br.readLine();
                if(s == null || s.trim().length() == 0) {
                    break;
                }
            }
        }

    }

}
