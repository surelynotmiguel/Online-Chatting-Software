package filehandler;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import dto.ChatDTO;
import gui.ChatFrame;

public class FileReceiver {
    public void receiveFile(Socket socket){
    	 try {
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

             // Recebe o objeto ChatDTO contendo informações do arquivo
             ChatDTO chatDTO = (ChatDTO) objectInputStream.readObject();

             // Recebe os bytes do arquivo
             if (chatDTO.getFileContent() != null) {
                 byte[] fileContent = new byte[chatDTO.getFileContent().length];
                 InputStream inputStream = socket.getInputStream();
                 int bytesRead = inputStream.read(fileContent, 0, fileContent.length);

                 if (bytesRead != -1) {
                     // Salva o arquivo localmente
                     saveFileLocally(chatDTO.getMessageFile().getName(), fileContent);
                     ChatFrame.getInstance().addFileSavedLocation(chatDTO.getMessageFile().getName());
                 }
             }
         } catch (IOException | ClassNotFoundException e) {
             e.printStackTrace();
             System.err.println("Error while receiving file over socket.");
         }
    }
    
    
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


