import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by ִלטענטי on 25.08.2015.
 */
public class Test {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getByName("198.199.73.244"), 6969);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("HEY NIGAA\n".getBytes());
            outputStream.write("\n".getBytes());
            outputStream.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while(true) {
                String s = br.readLine();
                System.out.println(s);
                if(s == null || s.trim().length() == 0) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
