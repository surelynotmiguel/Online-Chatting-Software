package connection.filehandler;

import java.io.*;
import java.net.Socket;
import dto.ChatDTO;
import gui.ChatFrame;

/**
 * The FileReceiver class manages receiving files over a socket connection.
 */
public class FileReceiver {
    /**
     * Receives a file using the provided Socket.
     *
     * @param socket The socket over which the file will be received
     */
    public void receiveFile(Socket socket) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            // Receives the ChatDTO object containing file information
            ChatDTO chatDTO = (ChatDTO) objectInputStream.readObject();

            // Receives the bytes of the file
            if (chatDTO.getFileContent() != null) {
                byte[] fileContent = new byte[chatDTO.getFileContent().length];
                InputStream inputStream = socket.getInputStream();
                int bytesRead = inputStream.read(fileContent, 0, fileContent.length);

                if (bytesRead != -1) {
                    // Saves the file locally
                    saveFileLocally(chatDTO.getMessageFile().getName(), fileContent);
                    ChatFrame.getInstance().addFileSavedLocation(chatDTO.getMessageFile().getName());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error while receiving file over socket.");
        }
    }

    /**
     * Saves the file content locally with the given file name.
     *
     * @param fileName    The name of the file to be saved
     * @param fileContent The content of the file as bytes
     */
    public void saveFileLocally(String fileName, byte[] fileContent) {
        String savePath = "..\\Online-Chatting-Software\\receivedFiles\\" + fileName;

        try (FileOutputStream fos = new FileOutputStream(savePath)) {
            fos.write(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while trying to save file locally.");
        }
    }
}
