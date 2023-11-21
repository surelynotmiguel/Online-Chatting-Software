package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import dto.ChatDTO;
import gui.ChatFrame;

public class ChatServer {
	
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    public static void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Connecting...");
            
            new Thread(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    System.out.println("Connection established!");

                    out = new ObjectOutputStream(clientSocket.getOutputStream());
                    in = new ObjectInputStream(clientSocket.getInputStream());

                    while (true) {
                        ChatDTO receivedMessage = (ChatDTO) in.readObject();
                        ChatFrame.getInstance().addMessageToConversation(receivedMessage);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            
            ChatFrame.getInstance().updateConnectionStatus(getConnectionStatus());
        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
        }
    }

    public static void disconnect() {
        try {
            if (serverSocket != null && !serverSocket.isClosed() && clientSocket != null && !clientSocket.isClosed()) {
            	clientSocket.close();
                serverSocket.close();
                ChatFrame.getInstance().updateConnectionStatus(getConnectionStatus());
                System.out.println("Connection ended.");
            }
        } catch (IOException e) {
            System.err.println("Error at ending connection: " + e.getMessage());
        }
    }
    
    public static boolean getConnectionStatus() {
    	return (serverSocket != null && clientSocket != null);
    }
    
    public static Socket getServerSocket() {
    	return clientSocket;
    }
}
