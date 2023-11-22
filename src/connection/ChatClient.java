package connection;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import dto.ChatDTO;
import gui.ChatFrame;

public class ChatClient {
    private static Socket socket;
    private static ObjectOutputStream out;
    private static final String SENDER = "Client";

    public static void start(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);

            System.out.println("Connection established.");
            
            ChatFrame.getInstance().updateConnectionStatus(getConnectionStatus());
            
            out = new ObjectOutputStream(socket.getOutputStream());

            sendMessage("If you can see this message, connection has been successful.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String textContent) {
        try {
            ChatDTO message = new ChatDTO(SENDER, textContent, new Date());
            ChatFrame.getInstance().addMessageToConversation(message);
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void sendFileMessage(String textContent, File file) {
    	try {
    		ChatDTO message = new ChatDTO(SENDER, textContent, new Date(), file);
    		ChatFrame.getInstance().addFileSentMessageToConversation(message);
            out.writeObject(message);
            out.flush();
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public static boolean getConnectionStatus() {
    	return (socket != null);
    }
    
    public static Socket getSocket() {
    	return socket;
    }
}
