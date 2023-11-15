package chat_GUI;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.FlowLayout;

public class HelpDialog extends JDialog {
    public HelpDialog(JFrame parent) {
        super(parent, "Ajuda", true);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new FlowLayout());
        JLabel label = new JLabel("<html>" + "Welcome to Chat Application 1.0!" + "<br>" + "<br>" +"Want to start a chat? go to:" + "<br>" + "File -> Connection"  + "</html>");
        add(label);
        setSize(300, 150);
        setLocationRelativeTo(null);
    }
}
