package filehandler;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import dto.ChatDTO;
import gui.ChatFrame;

public class FileSender {
    public void sendFile(ChatDTO chatDTO, Socket socket) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            // Envia o objeto ChatDTO contendo informações do arquivo
            objectOutputStream.writeObject(chatDTO);

            // Enviar o arquivo como bytes após o objeto ChatDTO
            if (chatDTO.getFileContent() != null) {
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(chatDTO.getFileContent());
                outputStream.flush();
            }

            // Adiciona mensagem à conversa indicando que o arquivo foi enviado
            ChatFrame.getInstance().addFileSentMessageToConversation(chatDTO);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while sending file over socket.");
        }
    }
}
