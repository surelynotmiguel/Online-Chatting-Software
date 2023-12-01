package gui.dialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import connection.ChatConnection;
import dto.ChatDTO;
import gui.ChatFrame;

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
        setResizable(false);
    }

    /**
     * Initializes the UI components and layout for the ConnectionDialog.
     */
    private void initializeUI() {
        JPanel panel = new JPanel(); // Panel with grid layout to organize components
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel ipLabel = new JLabel("IP Address:");
        ipField = new JTextField(15);
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);

        // Add components to the panel
        panel.add(createPanel(ipLabel, ipField));
        panel.add(createPanel(usernameLabel, usernameField));

        // Adds a space between inputs and connect button
        panel.add(Box.createVerticalStrut(10));
        
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

        connectButton.setFont(connectButton.getFont().deriveFont(12f));
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
    
    /**
     * Creates a panel with a GridLayout to organize the labels and input.
     *
     * @param label Label
     * @param field Input field
     * @return A JPanel with a label and input field
     */
    private JPanel createPanel(JLabel label, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Uses FlowLayout to align the components to the right
        panel.add(label);
        panel.add(field);
        return panel;
    }
}
