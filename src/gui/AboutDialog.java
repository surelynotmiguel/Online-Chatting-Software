package gui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;

/**
 * The AboutDialog class represents a dialog box displaying information about the application.
 */
public class AboutDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an AboutDialog object.
     *
     * @param parent The parent JFrame
     */
    public AboutDialog(JFrame parent) {
        super(parent, "About", true);
        initializeUI();
        setResizable(false);
    }

    /**
     * Initializes the UI components and layout for the AboutDialog.
     */
    private void initializeUI() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel nameVersion = createLabel(GlobalConstants.getNameVersion(), SwingConstants.CENTER);
        nameVersion.setFont(nameVersion.getFont().deriveFont(20f));
        add(nameVersion);
        
        add(Box.createVerticalStrut(20));
        
        JLabel createdBy = createLabel("Created by:", SwingConstants.CENTER);
        createdBy.setFont(createdBy.getFont().deriveFont(15f));
        add(createdBy);

        add(Box.createVerticalStrut(20));
        
        // Add labels for each author
        addAuthor(GlobalConstants.FELIPE, GlobalConstants.FELIPE_IMAGE_PATH);
        addAuthor(GlobalConstants.JULIO, GlobalConstants.JULIO_IMAGE_PATH);
        addAuthor(GlobalConstants.LORENA, GlobalConstants.LORENA_IMAGE_PATH);
        addAuthor(GlobalConstants.MIGUEL, GlobalConstants.MIGUEL_IMAGE_PATH);
        addAuthor(GlobalConstants.PEDRO, GlobalConstants.PEDRO_IMAGE_PATH);
        
        add(Box.createVerticalStrut(20));

        JLabel lastModifiedDateLabel = createLabel("Last modified:  " + GlobalConstants.getLastModifiedDate(), SwingConstants.CENTER);
        // Defines a new font size for the text
        lastModifiedDateLabel.setFont(lastModifiedDateLabel.getFont().deriveFont(15f)); // Alters the font size to a desired size
        add(lastModifiedDateLabel);

        EmptyBorder emptyBorder = new EmptyBorder(20, 30, 30, 30);
        ((JComponent) getContentPane()).setBorder(emptyBorder);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Creates a JLabel with specified text and alignment.
     *
     * @param text      The text for the label
     * @param alignment The alignment for the label
     * @return A JLabel with the provided text and alignment
     */
    private JLabel createLabel(String text, int alignment) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(alignment);
        return label;
    }

    /**
     * Adds an author with their name and image to the AboutDialog.
     *
     * @param name      The name of the author
     * @param imagePath The path to the author's image
     */
    private void addAuthor(String name, String imagePath) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // Load the image
        ImageIcon image = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(new ImageIcon(image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(nameLabel.getFont().deriveFont(15f));

        panel.add(imageLabel);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(nameLabel);
        add(panel);
    }
}
