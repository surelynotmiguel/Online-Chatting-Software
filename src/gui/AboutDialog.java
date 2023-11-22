package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.FlowLayout;

public class AboutDialog extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AboutDialog(JFrame parent) {
        super(parent, "About", true);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new FlowLayout());
        JLabel label = new JLabel("<html>" + "Chat Application Version 1.4" + "<br>" + "<br>" +"Created by:" + "<br>" + "Julio Morino Anastácio | 173434" + "<br>" + "\nFelipe Akira Nozaki | 172885" + "<br>" + "\nMiguel Miranda Melo Donanzam | 260851" + "<br>" + "<br>" + "Last modified: November 22th, 2023" + "</html>");
        add(label);
        setSize(300, 200);
        setLocationRelativeTo(null);
    }
}
