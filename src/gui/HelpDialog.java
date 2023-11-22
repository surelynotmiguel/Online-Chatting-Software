package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.FlowLayout;

public class HelpDialog extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HelpDialog(JFrame parent) {
        super(parent, "Help", true);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new FlowLayout());
        JLabel label = new JLabel("<html>" + "Welcome to Chat Application 1.4!" + "<br>" + "<br>" +"Want to start a chat? Go to:" + "<br>" + "File ➜ Connection"  + "</html>");
        add(label);
        setSize(300, 150);
        setLocationRelativeTo(null);
    }
}
