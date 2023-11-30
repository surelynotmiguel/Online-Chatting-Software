package gui;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.io.File;

/**
 * The AboutDialog class represents the dialog box displaying information about the application.
 */
public class AboutDialog extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Constructs an AboutDialog object.
     *
     * @param parent The parent JFrame
     */
	public AboutDialog(JFrame parent) {
        super(parent, "About", true);
        initializeUI();
    }

    /**
     * Initializes the UI components and layout for the AboutDialog.
     */
	private void initializeUI() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(createLabel(GlobalConstants.getNameVersion(), SwingConstants.CENTER));
        add(createLabel("Created by:", SwingConstants.CENTER));

        // Adiciona labels para cada autor
        addAuthor(GlobalConstants.FELIPE, GlobalConstants.FELIPE_IMAGE_PATH);
        addAuthor(GlobalConstants.JULIO, GlobalConstants.JULIO_IMAGE_PATH);
        addAuthor(GlobalConstants.LORENA, GlobalConstants.LORENA_IMAGE_PATH);
        addAuthor(GlobalConstants.MIGUEL, GlobalConstants.MIGUEL_IMAGE_PATH);
        addAuthor(GlobalConstants.PEDRO, GlobalConstants.PEDRO_IMAGE_PATH);
        // Repetir para os demais autores...

        add(createLabel("Last modified:  " + GlobalConstants.getLastModifiedDate(), SwingConstants.CENTER));

        pack();
        setLocationRelativeTo(null);
    }

    private JLabel createLabel(String text, int alignment) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(alignment);
        return label;
    }

    private void addAuthor(String name, String imagePath) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        // Carregar a imagem
        ImageIcon image = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(new ImageIcon(image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        JLabel nameLabel = new JLabel(name);

        panel.add(imageLabel);
        panel.add(nameLabel);
        add(panel);
    }
}
