package filehandler;

import java.io.*;
import java.net.Socket;

public class FileSender {
    private Socket socket;

    public FileSender(Socket serverSocket) {
        this.socket = serverSocket;
    }

    public void sendFile(String filePath) throws IOException {
        try (OutputStream outputStream = socket.getOutputStream();
             FileInputStream fileInputStream = new FileInputStream(filePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
