package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import connection.ChatConnection;
import dto.ChatDTO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * The ConnectionDialog class represents a dialog box for establishing a connection in the chat application.
 */
public class ConnectionDialog extends JDialog {
    private static final long serialVersionUID = 992994705610187333L;
    private JTextField ipField;
    private JTextField usernameField;

    /**
     * Constructs a ConnectionDialog object.
     *
     * @param parent The parent Frame
     */
    public ConnectionDialog(Frame parent) {
        super(parent, "Connection", true);
        initializeUI();
    }

    /**
     * Initializes the UI components and layout for the ConnectionDialog.
     */
    private void initializeUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2)); // Panel with grid layout to organize components

        JLabel ipLabel = new JLabel("IP Address:");
        ipField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        // Add components to the panel
        panel.add(ipLabel);
        panel.add(ipField);
        panel.add(usernameLabel);
        panel.add(usernameField);

        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic to handle the connection here
                String ipAddress = ipField.getText();
                String username = usernameField.getText();

                if (!verifyUsername(username)) {
                    return;
                }

                new Thread(() -> {
                    ChatConnection.startClient(ipAddress);
                    ChatDTO chatDTO = new ChatDTO(username, "just joined!", new Date());
                    ChatFrame.setUserInfo(chatDTO);
                    ChatConnection.sendMessage(chatDTO);
                }).start();

                ChatFrame.getInstance().updateConnectionStatus(true);

                dispose();
            }
        });

        // Add the button to the panel
        panel.add(connectButton);

        // Configure the layout for the dialog's content
        getContentPane().add(panel, BorderLayout.CENTER);

        panel.setBorder(new EmptyBorder(10, 20, 20, 20));
        pack(); // Adjust the window size based on content
        setLocationRelativeTo(null); // Center the window on the screen
    }

    /**
     * Verifies the validity of the entered username.
     *
     * @param username The username to be verified
     * @return True if the username is valid, otherwise false
     */
    public boolean verifyUsername(String username) {
        if (!username.isBlank() && !username.trim().isEmpty()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(ConnectionDialog.this, "Invalid username.", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
}
