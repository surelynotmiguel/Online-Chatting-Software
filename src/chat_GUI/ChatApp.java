package chat_GUI;
import javax.swing.*;

public class ChatApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    
    private static void createAndShowGUI() {
        JFrame frame = new ChatFrame();
        frame.setTitle("Chatting Application");
        frame.setSize(800, 600); //initial size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); //centralize window
        frame.setVisible(true);
    }

}
