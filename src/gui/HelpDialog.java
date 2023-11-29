package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

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
        JLabel label = new JLabel("<html>" + "Welcome to " + GlobalConstants.getNameVersion() + "<br><br>" +"Want to start a chat? Go to:" + "<br>" + "<center>File ➜ Connection</center>" + "</html>");
        label.setBorder(new EmptyBorder(10, 20, 20, 20));
        add(label);
        pack();
        setLocationRelativeTo(null);
    }
}
