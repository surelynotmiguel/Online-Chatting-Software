package connection;

import java.io.IOException;
import java.net.Socket;

public class ChatConnection {
	private static Socket socket;

    public boolean connectToServer(String ipAddress, int port) {
        try {
            socket = new Socket(ipAddress, port);
            System.out.println("Conexão estabelecida com sucesso ao servidor: " + ipAddress + ":" + port);
            return true;
        } catch (IOException e) {
            System.err.println("Falha ao conectar ao servidor: " + e.getMessage());
            return false;
        }
    }

    public void disconnect() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Conexão encerrada.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }
    
    public static Socket getSocket() {
    	return socket;
    }
}
