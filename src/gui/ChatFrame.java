package gui;

import javax.swing.*;

import connection.ChatClient;
import connection.ChatServer;
import dto.ChatDTO;
import filehandler.AudioPlayer;
import filehandler.FileSender;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ChatFrame extends JFrame implements ActionListener{
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

    public ChatFrame() {
        super(GlobalConstants.getNameVersion());
        instance = this;
        configureFrame();
        createAndAddMenuBar();
        addListenersMenu(this);
        addComponents();
    }
    
    public static ChatFrame getInstance() {
    	return instance;
    }
    
    public void start() {
    	this.setVisible(true);
    } 
    
    private void configureFrame() {
        this.setTitle(GlobalConstants.name);
        this.setSize(800, 600); //initial size
        this.setLocationRelativeTo(null); //centralize window
        this.setBackground(Color.white);
        // setIconjanela
        
        //case where user just click on X button
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(ChatFrame.this, "Are you sure?", "Exit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
    
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
    
    private void addListenersMenu(ActionListener listener) {
    	for(Component menu : this.getJMenuBar().getComponents()) {
    		if(menu instanceof JMenu) {
    			addListenersItemMenu(listener, (JMenu) menu);
    		}
    	}
    }
    
    private void addListenersItemMenu(ActionListener listener, JMenu menu) {
    	for (Component item : menu.getMenuComponents()) {
    		if(item instanceof JMenuItem) {
    			((JMenuItem) item).addActionListener(listener);
    		}
    	}
    }
    
    public void actionPerformed(ActionEvent event) {
    	if(event.getSource() == connectItem) {
    		ConnectionDialog dialog = new ConnectionDialog(ChatFrame.this);
            dialog.setSize(300, 150);
            dialog.setLocationRelativeTo(ChatFrame.this);
            dialog.setVisible(true);
            //(new ConnectionDialog(ChatFrame.this))
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
            	ChatServer.disconnect();
                System.exit(0);
            }
        }
    }
    
    private void addComponents() {
    	addStatusBar();
    	addContentPanel();
    	addConversationArea();
    	addMessageInput();
    }
    
    private void addStatusBar() {
    	JPanel statusPanel = new JPanel();
    	statusPanel.setBackground(Color.white);
    	statusBar = new JLabel("Connection Status: Not Connected");
    	statusPanel.add(statusBar);
    	this.add(statusPanel, BorderLayout.SOUTH);
    }
    
    public void updateConnectionStatus(boolean connected) {
        if (connected) {
            statusBar.setText("Connection Status: Connected");
        } else {
            statusBar.setText("Connection Status: Disconnected");
        }
    }
    
    public void insertUsername() {
        String username = null;

        do {
            username = JOptionPane.showInputDialog(ChatFrame.this, "Enter username: ");

            if (username != null && !username.trim().isEmpty()) {
                // Enviar 'userInfo' para o servidor
                userInfo = new ChatDTO(username, "just joined!", new Date());

                // Adicione lógica para exibir uma mensagem indicando a entrada do usuário na conversa
                addMessageToConversation(userInfo);
            } else if (username != null) {
                JOptionPane.showMessageDialog(ChatFrame.this, "You didn't enter a valid username!", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                // Se o usuário clicar em Cancelar, saia do loop
            	ChatServer.disconnect();
            	JOptionPane.showMessageDialog(ChatFrame.this, "You've been disconnected.", "Connection", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        } while (username == null || username.trim().isEmpty());
    }
    
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
    
    public String formatMessage(String message) {
        final int MAX_CHARACTERS_PER_LINE = 80;
        StringBuilder formattedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i += MAX_CHARACTERS_PER_LINE) {
            int endIndex = Math.min(i + MAX_CHARACTERS_PER_LINE, message.length());
            formattedMessage.append(message.substring(i, endIndex));

            if (endIndex < message.length()) {
                formattedMessage.append("\n");
            }
        }

        return formattedMessage.toString();
    }

    public void sendMessage() {
        addMessageToConversation(new ChatDTO("Eu", textField.getText(), new Date()));
        textField.setText("");
    }
    
    public void sendFileMessage(File file) {
    	addFileSentMessageToConversation(new ChatDTO("Eu", textField.getText().isBlank() ? null : textField.getText(), new Date(), file));
    }
    
    public void addMessageToConversation(ChatDTO receivedMessage) {
        if (receivedMessage.getMessageFile() != null) {
            String fileName = receivedMessage.getMessageFile().getName();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

            SwingUtilities.invokeLater(() -> conversationArea.append(receivedMessage.getUsername() + "\n"));
            if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("gif")) {
                // Se for uma imagem
                ImageIcon imageIcon = new ImageIcon(receivedMessage.getMessageFile().getAbsolutePath());
                Image image = imageIcon.getImage();
                Image resizedImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Tamanho personalizado da imagem
                ImageIcon resizedImageIcon = new ImageIcon(resizedImage);

                JLabel imageLabel = new JLabel();
                imageLabel.setIcon(resizedImageIcon);
                conversationArea.add(imageLabel);
            } else if (extension.equalsIgnoreCase("mp3") || extension.equalsIgnoreCase("wav")) {
                // Se for um arquivo de áudio
                JButton audioPlayerButton = new JButton("Play Audio");
                audioPlayerButton.addActionListener(e -> {
                    try {
                        AudioPlayer.playAudio(receivedMessage.getMessageFile().getAbsolutePath());
                    } catch (Exception ex) {
                        ex.printStackTrace(); // Trate a exceção conforme necessário
                    }
                });
                conversationArea.add(audioPlayerButton);
            } else {
                // Outros tipos de arquivos - apenas exibe o nome do arquivo
                JLabel fileLabel = new JLabel(fileName);
                conversationArea.add(fileLabel);
            }

            // Adicione a data/hora da mensagem depois do arquivo ou da imagem
            SwingUtilities.invokeLater(() -> conversationArea.append("\n" + receivedMessage.getDateTimeOfMessage() + "\n\n"));
        } else {
            // Se não for um arquivo, exibe o texto da mensagem
            String message = receivedMessage.getMessage();
            SwingUtilities.invokeLater(() -> conversationArea.append(receivedMessage.getUsername() + ":\n" + formatMessage(message) + "\n" + receivedMessage.getDateTimeOfMessage() + "\n\n"));
        }
    }

    
    public void addFileSentMessageToConversation(ChatDTO receivedFileMessage) {
        addMessageToConversation(receivedFileMessage);
        textField.setText("");
    }
    
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

        	            try {
        	                // Suponha que 'socket' seja a instância do socket para a conexão
        	                FileSender fileSender = new FileSender(ChatClient.getSocket());
        	                fileSender.sendFile(selectedFile.getAbsolutePath());

        	                // Aqui você pode adicionar lógica para exibir uma mensagem indicando o envio do arquivo na conversa
        	                sendFileMessage(selectedFile);
        	            } catch (IOException ex) {
        	                ex.printStackTrace(); // Trate a exceção conforme necessário
        	            }
        	        }
    	    	} else {
    	    		JOptionPane.showMessageDialog(ChatFrame.this, "You're not connected.", "Connection", JOptionPane.WARNING_MESSAGE);
    	    	}
    	    }
    	});
    	
    	ImageIcon originalIcon = new ImageIcon("..\\Projeto III - Chat Online\\icon\\6583130.png"); // Substitua pelo caminho do seu ícone
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
