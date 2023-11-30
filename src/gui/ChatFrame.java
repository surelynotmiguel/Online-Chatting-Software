package gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import connection.ChatConnection;
import dto.ChatDTO;
import connection.filehandler.FileSender;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.Date;

/**
 * The ChatFrame class represents the main graphical user interface for the chat application.
 */
public class ChatFrame extends JFrame implements ActionListener{
	@Serial
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPanel;
	private JTextArea conversationArea;
	private JTextField textField;
	private JButton sendButton;
	private JMenuBar menuBar;
	private JMenu fileMenu, helpMenu;
	private JMenuItem connectItem, exitItem, helpItem, aboutItem;
	private JLabel statusBar;
	private ChatDTO userInfo;
	private static ChatFrame instance;

    /**
     * Constructs a ChatFrame object.
     * Initializes the GUI components and sets up the frame.
     */
    public ChatFrame() {
        super(GlobalConstants.getNameVersion());
        instance = this;
        configureFrame();
        createAndAddMenuBar();
        addListenersMenu(this);
        addComponents();
    }
    
    /**
     * Gets the instance of the ChatFrame (Singleton pattern).
     *
     * @return The instance of ChatFrame
     */
    public static ChatFrame getInstance() {
    	return instance;
    }
    
    /**
     * Sets the user information for the chat.
     *
     * @param userInfo The user information (ChatDTO)
     */
    public static void setUserInfo(ChatDTO userInfo){
        getInstance().userInfo = userInfo;
    }

    /**
     * Gets the user information for the chat.
     *
     * @return The user information (ChatDTO)
     */
    public static ChatDTO getUserInfo(){
        return getInstance().userInfo;
    } 
    
    /**
     * Shows the chat frame.
     */
    public void start() {
    	this.setVisible(true);
    } 
    
    /**
     * Configures the frame properties.
     */
    private void configureFrame() {
        this.setTitle(GlobalConstants.SOFTWARE_NAME);
        this.setSize(800, 600); //initial size
        this.setLocationRelativeTo(null); //centralize window
        this.setBackground(Color.white);
        
        // sets window icon
        try {
            BufferedImage icon = ImageIO.read(new File("resources\\icons\\chat_icon.png")); // Loads image
            setIconImage(icon); // Sets the image as the window icon
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //case where user just click on X button
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(ChatFrame.this, "Are you sure?", "Exit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                	if(getUserInfo() != null && ChatConnection.getClientSocket() != null) {
                    	ChatConnection.sendMessage(new ChatDTO(getUserInfo().getUsername(), "disconneted.", new Date()));
                    	ChatConnection.disconnect();
                	}
                    System.exit(0);
                }
            }
        });
    }
    
    /**
     * Creates and adds the menu bar with menu items.
     */
    private void createAndAddMenuBar() {
    	menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        connectItem = new JMenuItem("Connection");
        connectItem.setMnemonic('C');
        exitItem = new JMenuItem("Exit program");
        exitItem.setMnemonic('E');

        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        helpItem = new JMenuItem("Starting a chat");
        helpItem.setMnemonic('S');
        aboutItem = new JMenuItem("About");
        aboutItem.setMnemonic('A');

        fileMenu.add(connectItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        helpMenu.add(helpItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        menuBar.setBackground(Color.LIGHT_GRAY);
        this.setJMenuBar(menuBar);
    }
    
    /**
     * Adds action listeners to the menu items.
     *
     * @param listener The ActionListener
     */
    private void addListenersMenu(ActionListener listener) {
    	for(Component menu : this.getJMenuBar().getComponents()) {
    		if(menu instanceof JMenu) {
    			addListenersItemMenu(listener, (JMenu) menu);
    		}
    	}
    }
    
    /**
     * Adds action listeners to the menu items in the given menu.
     *
     * @param listener The ActionListener
     * @param menu     The JMenu where action listeners need to be added
     */
    private void addListenersItemMenu(ActionListener listener, JMenu menu) {
    	for (Component item : menu.getMenuComponents()) {
    		if(item instanceof JMenuItem) {
    			((JMenuItem) item).addActionListener(listener);
    		}
    	}
    }
    
    /**
     * Handles actions performed on menu items.
     *
     * @param event The ActionEvent
     */
    public void actionPerformed(ActionEvent event) {
    	if(event.getSource() == connectItem) {
    		ConnectionDialog dialog = new ConnectionDialog(ChatFrame.this);
            dialog.setSize(300, 150);
            dialog.setLocationRelativeTo(ChatFrame.this);
            dialog.setVisible(true);
    	}
    	if(event.getSource() == aboutItem) {
    		(new AboutDialog(ChatFrame.this)).setVisible(true);
    	}
    	if(event.getSource() == helpItem) {
    		(new HelpDialog(ChatFrame.this)).setVisible(true);
    	}
        if(event.getSource() == exitItem) {
        	int option = JOptionPane.showConfirmDialog(ChatFrame.this, "Are you sure?", "Exit", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
            	if(getUserInfo() != null && ChatConnection.getClientSocket() != null) {
                	ChatConnection.sendMessage(new ChatDTO(getUserInfo().getUsername(), "disconneted.", new Date()));
                	ChatConnection.disconnect();
            	}
                System.exit(0);
            }
        }
    }
    
    /**
     * Adds components to the chat frame.
     */
    private void addComponents() {
    	addStatusBar();
    	addContentPanel();
    	addConversationArea();
    	addMessageInput();
    }
    
    /**
     * Adds the status bar at the bottom of the chat frame.
     */
    private void addStatusBar() {
    	JPanel statusPanel = new JPanel();
    	statusPanel.setBackground(Color.white);
    	statusBar = new JLabel("Connection Status: Not Connected");
    	statusPanel.add(statusBar);
    	this.add(statusPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Updates the connection status in the status bar.
     *
     * @param connected A boolean representing the connection status
     */
    public void updateConnectionStatus(boolean connected) {
        if (connected) {
            statusBar.setText("Connection Status: Connected");
        } else {
            statusBar.setText("Connection Status: Disconnected");
        }
    }
    
    /**
     * Adds the content panel to the chat frame.
     */
    private void addContentPanel() {
    	contentPanel = new JPanel(new BorderLayout());
    	
    	JPanel leftSpace = new JPanel();
    	JPanel rightSpace = new JPanel();
    	leftSpace.setPreferredSize(new Dimension(50, 0));
    	rightSpace.setPreferredSize(new Dimension(50, 0));
    	
    	contentPanel.add(leftSpace, BorderLayout.WEST);
    	contentPanel.add(rightSpace, BorderLayout.EAST);
    	this.add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Adds the conversation area to display chat messages.
     */
    private void addConversationArea() {
    	JPanel chatPanel = new JPanel(new BorderLayout());
    	
    	JPanel spaceTop = new JPanel();
    	spaceTop.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
    	chatPanel.add(spaceTop, BorderLayout.NORTH);
    	
    	conversationArea = new JTextArea();
    	conversationArea.setEditable(false);
    	JScrollPane scrollPane = new JScrollPane(conversationArea);
    	chatPanel.add(scrollPane, BorderLayout.CENTER);
    	
    	contentPanel.add(chatPanel, BorderLayout.CENTER);
    }
    
    /**
     * Formats the chat message for display in the conversation area.
     *
     * @param message The message to be formatted
     * @return The formatted message as a String
     */
    public String formatMessage(String message) {
        final int MAX_CHARACTERS_PER_LINE = 80;
        StringBuilder formattedMessage = new StringBuilder();

        int startIndex = 0;
        while (startIndex < message.length()) {
            int endIndex = Math.min(startIndex + MAX_CHARACTERS_PER_LINE, message.length());
            String subMessage = message.substring(startIndex, endIndex);

            if (endIndex < message.length() && !Character.isWhitespace(message.charAt(endIndex - 1))) {
                // Verifica se a última posição não é um espaço em branco para evitar cortar palavras no meio
                int lastSpaceIndex = subMessage.lastIndexOf(' ');
                if (lastSpaceIndex > 0) {
                    endIndex = startIndex + lastSpaceIndex; // Atualiza o índice final para o último espaço
                    subMessage = message.substring(startIndex, endIndex);
                }
            }

            formattedMessage.append(subMessage);
            if (endIndex < message.length()) {
                formattedMessage.append("-\n");
            }

            startIndex = endIndex; // Atualiza o índice inicial para a próxima parte do texto
            while (startIndex < message.length() && Character.isWhitespace(message.charAt(startIndex))) {
                startIndex++; // Avança para o próximo caractere não branco
            }
        }

        return formattedMessage.toString();
    }

    /**
     * Sends a text message in the chat.
     */
    public void sendMessage() {
    	String message = textField.getText();
    	ChatConnection.sendMessage(new ChatDTO(getUserInfo().getUsername(), message, new Date()));
        addMessageToConversation(new ChatDTO("Me", message, new Date()));
        textField.setText("");
    }
    
    /**
     * Sends a file message in the chat.
     *
     * @param file The file to be sent
     */
    public void sendFileMessage(File file) {
    	String message = textField.getText();
    	ChatConnection.sendMessage(new ChatDTO(getUserInfo().getUsername(), message, new Date(), file));
    	addFileSentMessageToConversation(new ChatDTO("Me", message, new Date(), file));
    	textField.setText("");
    }
    
    /**
     * Adds the file save location to the conversation area.
     *
     * @param savePath The path where the file is saved
     */
	public void addFileSavedLocation(String savePath) {
		SwingUtilities.invokeLater(() -> conversationArea.append("File saved to: " + savePath + "\n\n"));
	}
    
    /**
     * Adds a received message to the conversation area.
     *
     * @param receivedMessage The received chat message
     */
    public void addMessageToConversation(ChatDTO receivedMessage) {
        if (receivedMessage.getMessageFile() != null) {
            String message = receivedMessage.getMessage();
            String fileName = receivedMessage.getMessageFile().getName();
            SwingUtilities.invokeLater(() -> conversationArea.append(receivedMessage.getUsername() + ":\n" + (message.isBlank() ? "" : formatMessage(message) + "\n" + fileName + "\n" + receivedMessage.getDateTimeOfMessage() + "\n")));
        } else {
            // Se não for um arquivo, exibe o texto da mensagem
            String message = receivedMessage.getMessage();
            SwingUtilities.invokeLater(() -> conversationArea.append(receivedMessage.getUsername() + ":\n" + formatMessage(message) + "\n" + receivedMessage.getDateTimeOfMessage() + "\n\n"));
        }
    }

    /**
     * Adds a file-sent message to the conversation area.
     *
     * @param receivedFileMessage The file-sent chat message
     */
    public void addFileSentMessageToConversation(ChatDTO receivedFileMessage) {
        addMessageToConversation(receivedFileMessage);
        textField.setText("");
    }
    
    /**
     * Adds the message input area for sending messages.
     */
    private void addMessageInput() {
    	JPanel panel = new JPanel(new BorderLayout());
    	
    	JPanel spaceTop = new JPanel();
    	spaceTop.setPreferredSize(new Dimension(Integer.MAX_VALUE, 20));
    	
    	JPanel spaceBottom = new JPanel();
    	spaceBottom.setPreferredSize(new Dimension(Integer.MAX_VALUE, 20));
    	
    	JPanel spaceLeft = new JPanel();
    	spaceLeft.setPreferredSize(new Dimension(50, 0));
    	
    	JPanel spaceRight = new JPanel();
    	spaceRight.setPreferredSize(new Dimension(50, 0));
    	
    	JPanel messageInputPanel = new JPanel(new BorderLayout());
    	textField = new JTextField();
    	sendButton = new JButton("Send");
    	messageInputPanel.add(textField, BorderLayout.CENTER);
    	messageInputPanel.add(sendButton, BorderLayout.EAST);
    	sendButton.addActionListener(e -> {
    		if(userInfo != null) {
    			if(!textField.getText().isEmpty() || !textField.getText().isBlank()) {
    				sendMessage();
    			} else {
    				JOptionPane.showMessageDialog(ChatFrame.this, "Please enter a message before sending.", "Connection", JOptionPane.WARNING_MESSAGE);
    			}
    		} else {
    			JOptionPane.showMessageDialog(ChatFrame.this, "You're not connected.", "Connection", JOptionPane.WARNING_MESSAGE);
    		}
    	});
    	
    	final JButton fileButton = new JButton();
    	fileButton.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	    	if(userInfo != null) {
    	    		JFileChooser fileChooser = new JFileChooser();
        	        int option = fileChooser.showOpenDialog(ChatFrame.this);
        	        if (option == JFileChooser.APPROVE_OPTION) {
        	            File selectedFile = fileChooser.getSelectedFile();

        	            ChatDTO chatDTO = new ChatDTO(getUserInfo().getUsername(), "", new Date(), selectedFile);
        	            try {
        	                // Suponha que 'socket' seja a instância do socket para a conexão
        	                FileSender fileSender = new FileSender();
        	                fileSender.sendFile(chatDTO, ChatConnection.getClientSocket());

        	                // Aqui você pode adicionar lógica para exibir uma mensagem indicando o envio do arquivo na conversa
        	                sendFileMessage(selectedFile);
        	            } catch (Exception ex) {
        	                ex.printStackTrace(); // Trate a exceção conforme necessário
        	            }
        	        }
    	    	} else {
    	    		JOptionPane.showMessageDialog(ChatFrame.this, "You're not connected.", "Connection", JOptionPane.WARNING_MESSAGE);
    	    	}
    	    }
    	});
    	
    	ImageIcon originalIcon = new ImageIcon("..\\Projeto III - Chat Online\\resources\\icons\\6583130.png"); // Substitua pelo caminho do seu ícone
    	// Obtenha a imagem do ícone original
    	Image originalImage = originalIcon.getImage();

    	// Redimensione a imagem para o tamanho desejado (por exemplo, 32x32)
    	int newWidth = 25;
    	int newHeight = 25;
    	Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

    	// Crie um novo ImageIcon com a imagem redimensionada
    	ImageIcon scaledIcon = new ImageIcon(scaledImage);

    	// Atribua o ícone redimensionado ao botão
    	fileButton.setIcon(scaledIcon);
    	messageInputPanel.add(fileButton, BorderLayout.WEST); // Adicione o botão à interface
    	
    	panel.add(spaceTop, BorderLayout.NORTH);
    	panel.add(spaceLeft, BorderLayout.WEST);
    	panel.add(spaceRight, BorderLayout.EAST);
    	panel.add(messageInputPanel, BorderLayout.CENTER);
    	panel.add(spaceBottom, BorderLayout.SOUTH);
    	
    	contentPanel.add(panel, BorderLayout.SOUTH);
    }
}
