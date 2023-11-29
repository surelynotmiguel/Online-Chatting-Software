package connection.filehandler;

import java.io.*;
import java.net.Socket;
import dto.ChatDTO;
import gui.ChatFrame;

/**
 * The FileSender class handles sending files over a socket connection.
 */
public class FileSender {
    /**
     * Sends a file using the provided ChatDTO and Socket.
     *
     * @param chatDTO The ChatDTO containing information about the file
     * @param socket  The socket over which the file will be sent
     */
    public void sendFile(ChatDTO chatDTO, Socket socket) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            // Sends the ChatDTO object containing file information
            objectOutputStream.writeObject(chatDTO);

            // Sends the file as bytes after the ChatDTO object
            if (chatDTO.getFileContent() != null) {
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(chatDTO.getFileContent());
                outputStream.flush();
            }

            // Adds a message to the conversation indicating that the file has been sent
            ChatFrame.getInstance().addFileSentMessageToConversation(chatDTO);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while sending file over socket.");
        }
    }
}
