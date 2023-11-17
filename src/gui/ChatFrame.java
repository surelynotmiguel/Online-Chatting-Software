package gui;

import javax.swing.*;

import connection.ChatConnection;
import dto.ChatDTO;
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
	private ChatConnection chatConnection;

    public ChatFrame() {
        super(GlobalConstants.getNameVersion());
        configureFrame();
        createAndAddMenuBar();
        addListenersMenu(this);
        addComponents();
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
            // Solicitar ao usuário que insira um nome de usuário
            if(insertUsername());
            else {
                // Se o usuário cancelar ou não fornecer um nome de usuário, faça alguma ação apropriada (por exemplo, desconectar)
            	chatConnection.disconnect();
                statusBar.setText("Connection Status: Disconnected");
            }
        } else {
            statusBar.setText("Connection Status: Couldn't Connect");
        }
    }
    
    public boolean insertUsername() {
    	String username = JOptionPane.showInputDialog(ChatFrame.this, "Enter username: ");

        if (username != null && !username.isEmpty()) {
            userInfo = new ChatDTO(username, " just joined!", new Date());
            // Enviar 'userInfo' para o servidor

            // Adicione lógica para exibir uma mensagem indicando a entrada do usuário na conversa
            addMessageToConversation("Welcome, " + username + "!");
            return true;
        } else {
        	return false;
        }
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
    
    private void addMessageToConversation(String message) {
        conversationArea.append(userInfo.getUsername() + message + userInfo.getDateTimeOfMessage() + "\n");
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
    	sendButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if(userInfo != null) {
    				addMessageToConversation(textField.getText());
    			} else {
    				JOptionPane.showMessageDialog(null, "You're not connected.");
    			}
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
        	                FileSender fileSender = new FileSender(ChatConnection.getSocket());
        	                fileSender.sendFile(selectedFile.getAbsolutePath());

        	                // Aqui você pode adicionar lógica para exibir uma mensagem indicando o envio do arquivo na conversa
        	                addMessageToConversation("File sent: " + selectedFile.getName());
        	            } catch (IOException ex) {
        	                ex.printStackTrace(); // Trate a exceção conforme necessário
        	            }
        	        }
    	    	} else {
    	    		JOptionPane.showMessageDialog(null, "You're not connected.");
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
