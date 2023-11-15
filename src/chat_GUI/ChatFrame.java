package chat_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ChatFrame extends JFrame {
    private JTextArea conversationArea;

    public ChatFrame() {
        initializeUI();
    }

    
    private void initializeUI() {
        conversationArea = new JTextArea();
        conversationArea.setEditable(false); //avoiding changes on this area

        JScrollPane scrollPane = new JScrollPane(conversationArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        createMenuBar();
        
        

    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem connectItem = new JMenuItem("Connection");
        JMenuItem exitItem = new JMenuItem("Exit program");

        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("Starting a chat");
        JMenuItem aboutItem = new JMenuItem("About");

        fileMenu.add(connectItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        helpMenu.add(helpItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
        
        //connect menu
        connectItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConnectionDialog dialog = new ConnectionDialog(ChatFrame.this);
                dialog.setSize(300, 150);
                dialog.setLocationRelativeTo(ChatFrame.this);
                dialog.setVisible(true);
            }
        });
        
        //about menu
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AboutDialog dialog = new AboutDialog(ChatFrame.this);
                dialog.setVisible(true);
            }
        });
        
        //help menu
        helpItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HelpDialog dialog = new HelpDialog(ChatFrame.this);
                dialog.setVisible(true);
            }
        });
        
        //exiting program
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(ChatFrame.this, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        //case where user just click on X button
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(ChatFrame.this, "Deseja realmente sair?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

    }

}
