package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import dto.ChatDTO;
import connection.filehandler.FileReceiver;
import gui.ChatFrame;

/**
 * The ChatConnection class manages the client-server communication for the chat application.
 */
public class ChatConnection {
    private static Socket client;
    private static Socket server;

    /**
     * Starts the server to accept incoming connections.
     */
    public static void startServer() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(9999);
            server = serverSocket.accept();

            Thread receiveMessageThread = new Thread(() -> receiveMessage(server));
            receiveMessageThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the client and connects to the specified host.
     *
     * @param host The host address to connect to
     */
    public static void startClient(String host) {
        try {
            client = new Socket(host, 9999);

            Thread receiveThread = new Thread(() -> receiveMessage(client));
            receiveThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Listens for incoming messages from the specified socket.
     *
     * @param socket The socket to listen for incoming messages
     */
    public static void receiveMessage(Socket socket) {
        while (true) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                try {
                    Object receivedObject = objectInputStream.readObject();

                    if (receivedObject instanceof ChatDTO) {
                        ChatDTO chatDTO = (ChatDTO) receivedObject;

                        if (chatDTO.getMessageFile() != null) {
                            FileReceiver fileReceiver = new FileReceiver();
                            fileReceiver.receiveFile(socket);
                            ChatFrame.getInstance().addFileSentMessageToConversation(chatDTO);
                        } else {
                            ChatFrame.getInstance().addMessageToConversation(chatDTO);
                        }
                    } else {
                        System.err.println("Received object is not an instance of ChatDTO");
                    }
                } catch (ClassCastException e) {
                    System.err.println("Error casting to ChatDTO: " + e.getMessage());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends a message using the client socket.
     *
     * @param chatDTO The ChatDTO object containing the message
     */
    public static void sendMessage(ChatDTO chatDTO) {
        try {
            if (client != null) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                objectOutputStream.writeObject(chatDTO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the client socket.
     *
     * @return The client socket
     */
    public static Socket getClientSocket() {
        return client;
    }

    /**
     * Disconnects both client and server sockets.
     */
    public static void disconnect() {
        try {
            if (server != null && !server.isClosed()) {
                server.close();
            }
            if (client != null && !client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            System.err.println("Error disconnecting: " + e.getMessage());
        }
    }
}
