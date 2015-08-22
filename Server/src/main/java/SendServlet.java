import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Freemahn on 21.08.2015.
 */
public class SendServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String remote = "198.199.73.244";
        String local = "127.0.0.1";
        Socket socket = new Socket(local, 8080);
        socket.getOutputStream().write("hello".getBytes());
        //socket.connect(new InetSocketAddress("198.199.73.244", 8080));
        String answer = "";
        while (socket.getInputStream().available() >= 0) {
            answer += (char) socket.getInputStream().read();
        }
        resp.getOutputStream().print(answer);
    }
}
