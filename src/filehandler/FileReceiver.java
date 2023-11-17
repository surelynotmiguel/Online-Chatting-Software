package filehandler;

import java.io.*;
import java.net.Socket;

public class FileReceiver {
    private Socket socket;

    public FileReceiver(Socket socket) {
        this.socket = socket;
    }

    public void receiveFile(String filePath) throws IOException {
        try (InputStream inputStream = socket.getInputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
