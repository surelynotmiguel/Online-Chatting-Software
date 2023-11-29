/**
 * The ChatApp class is the main class that initiates the chat application.
 * It creates a Swing graphical interface and starts the chat server connection in a separate thread.
 */
package gui;

import java.awt.HeadlessException;
import javax.swing.SwingUtilities;
import connection.ChatConnection;

public class ChatApp {

    /**
     * The main method that initiates the chat application.
     *
     * @param args Command-line arguments (not used in this context)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                ChatFrame frame = new ChatFrame();
                frame.start();

                new Thread(ChatConnection::startServer).start();
            } catch (HeadlessException e) {
                printErrorAndFinish("Program terminated by a HeadlessException in the main() method", e);
            } catch (Exception e) {
                printErrorAndFinish("Program terminated by a Generic Exception in the main() method", e);
            }
        });
    }

    /**
     * Prints the error message and terminates the program.
     *
     * @param message The error message to be displayed
     * @param e       The occurred exception
     */
    private static void printErrorAndFinish(String message, Exception e) {
        System.out.println("Error message:\t" + message);
        System.out.println("Exception text:\t" + e.getMessage());
        System.exit(1);
    }
}
