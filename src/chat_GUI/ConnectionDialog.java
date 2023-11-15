package chat_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionDialog extends JDialog {
    private JTextField ipField;
    private JTextField portField;

    public ConnectionDialog(Frame parent) {
        super(parent, "Conexão", true);
        initializeUI();
    }

    private void initializeUI() {
        // Adicione os componentes necessários, como JLabels, JTextFields, etc.

        JButton connectButton = new JButton("Conectar");
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para lidar com a conexão aqui
                dispose(); // Fechar a caixa de diálogo após a conexão
            }
        });

        // Adicione os componentes ao layout conforme necessário
    }
}
