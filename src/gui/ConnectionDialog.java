package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import connection.ChatConnection;
import dto.ChatDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ConnectionDialog extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 992994705610187333L;
	private JTextField ipField;
	private JTextField usernameField;

    public ConnectionDialog(Frame parent) {
        super(parent, "Connection", true);
        initializeUI();
    }

    private void initializeUI() {
    	JPanel panel = new JPanel(new GridLayout(3, 2)); // Painel com layout de grade para organizar os componentes

        JLabel ipLabel = new JLabel("IP Address:");
        ipField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        // Adicione os componentes ao painel
        panel.add(ipLabel);
        panel.add(ipField);
        panel.add(usernameLabel);
        panel.add(usernameField);

        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para lidar com a conexão aqui
            	String ipAddress = ipField.getText();
            	String username = usernameField.getText();
                
            	if(!verifyUsername(username)) {
            		return;
            	}
        		
        		new Thread(() -> {
        			ChatConnection.startClient(ipAddress);
            		ChatDTO chatDTO = new ChatDTO(username, "just joined!", new Date());
            		ChatFrame.setUserInfo(chatDTO);
            		ChatConnection.sendMessage(chatDTO);
        		}).start();
        		
        		ChatFrame.getInstance().updateConnectionStatus(rootPaneCheckingEnabled);
        		
            	dispose();
            }});

        // Adicione o botão ao painel
        panel.add(connectButton);

        // Configure o layout para o conteúdo da caixa de diálogo
        getContentPane().add(panel, BorderLayout.CENTER);

        panel.setBorder(new EmptyBorder(10, 20, 20, 20));
        pack(); // Ajustar o tamanho da janela com base no conteúdo
        setLocationRelativeTo(null); // Centralizar a janela na tela
    }
    
    public boolean verifyUsername(String username) {
    	if(!username.isBlank() && !username.trim().isEmpty()) {
    		return true;
    	} else {
    		JOptionPane.showMessageDialog(ConnectionDialog.this, "Invalid username.", "Warning", JOptionPane.WARNING_MESSAGE);
    		return false;
    	}
    }
}
