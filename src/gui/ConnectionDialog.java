package gui;

import javax.swing.*;

import connection.ChatConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionDialog extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 992994705610187333L;
	private JTextField ipField;
	private JTextField portField;
	private ChatConnection chatConnection;

    public ConnectionDialog(Frame parent) {
        super(parent, "Connection", true);
        this.chatConnection = new ChatConnection();
        initializeUI();
    }

    private void initializeUI() {
    	JPanel panel = new JPanel(new GridLayout(3, 2)); // Painel com layout de grade para organizar os componentes

        JLabel ipLabel = new JLabel("IP Address:");
        ipField = new JTextField();
        JLabel portLabel = new JLabel("Port:");
        portField = new JTextField();

        // Adicione os componentes ao painel
        panel.add(ipLabel);
        panel.add(ipField);
        panel.add(portLabel);
        panel.add(portField);

        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para lidar com a conexão aqui
            	String ipAddress = ipField.getText();
                int port = Integer.parseInt(portField.getText());

                boolean connected = chatConnection.connectToServer(ipAddress, port);

                if (connected) {
                	((ChatFrame) getParent()).updateConnectionStatus(true);
                    JOptionPane.showMessageDialog(ConnectionDialog.this,
                            "Connection successfully established!", "Connection", JOptionPane.INFORMATION_MESSAGE);
                    // Atualiza o status da conexão no ChatFrame
                    
                    dispose(); // Fechar a caixa de diálogo após a conexão
                } else {
                	((ChatFrame) getParent()).updateConnectionStatus(false);
                    JOptionPane.showMessageDialog(ConnectionDialog.this,
                            "Failed to connect. Please verify IP address and port.", "Connection Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicione o botão ao painel
        panel.add(connectButton);

        // Configure o layout para o conteúdo da caixa de diálogo
        getContentPane().add(panel, BorderLayout.CENTER);

        pack(); // Ajustar o tamanho da janela com base no conteúdo
        setLocationRelativeTo(null); // Centralizar a janela na tela
    }
}
