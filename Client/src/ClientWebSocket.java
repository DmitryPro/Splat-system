import org.apache.log4j.Logger;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 * This is client endpoint. Contains implementation of interaction between
 * server and client in client side
 * 
 * @see Client
 * @see ServerWebSocket(in Server)
 * 
 * @author Pavel Gordon
 *
 */
@ClientEndpoint
public class ClientWebSocket {
	private Logger logger = Logger.getLogger(ClientWebSocket.class);
	public static List<ExpandedDataObject> data = Collections.synchronizedList(new ArrayList<ExpandedDataObject>());
	/**
	 * Handles server-socket connection
	 * 
	 * @param sess
	 *            session with server
	 */
	@OnOpen
	public void onWebSocketConnect(Session sess) {
		logger.info("ServerSocket Connected: " + sess);

	}

	/**
	 * Handles text messages, received from server
	 * 
	 * @param message
	 */
	@OnMessage
	public  void onWebSocketText(String message) {
		//System.out.println("Received TEXT message: " + message);
		//logger.info("Received TEXT message: " + message);
		System.out.println("Parsed " + parseJSON(message));
		// FrameDisplayer.testFigure.addPoint(parseJSON(message));
		//FrameDisplayer.testFigure.addPoint(new ExpandedDataObject(10,100,10));
		// draw graphic
		
		data.add(parseJSON(message));
	}

	@OnClose
	public void onWebSocketClose(CloseReason reason) {
		System.out.println("Socket Closed: " + reason.getReasonPhrase() + "/" + reason);
	}

	@OnError
	public void onWebSocketError(Throwable cause) {
		cause.printStackTrace(System.err);
	}

	ExpandedDataObject parseJSON(String txt) {
		ExpandedDataObject result = null;
		result = new Gson().fromJson(txt, ExpandedDataObject.class);
		return result;
	}

}