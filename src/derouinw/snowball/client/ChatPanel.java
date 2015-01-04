package derouinw.snowball.client;

import derouinw.snowball.client.Game.GamePanel;
import derouinw.snowball.server.Message.ChatMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles all chat messages
 */
public class ChatPanel extends JPanel {
    private JTextArea chatArea;
    private JTextField typeField;
    private JButton sendButton;

    private NetworkThread nt;
    private GamePanel gp;

    public ChatPanel(NetworkThread nt) {
        super();
        setFocusable(false);

        this.nt = nt;

        chatArea = new JTextArea();
        chatArea.setEditable(false);

        typeField = new JTextField(10);
        typeField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendButton.doClick();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        sendButton = new JButton("Send");
        sendButton.setFocusable(false);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = typeField.getText();
                sendMessage(text);
                typeField.setText("");
                gp.requestFocus();
            }
        });

        setLayout(new FlowLayout());

        add(chatArea);
        add(typeField);
        add(sendButton);
    }

    public void setGamePanel(GamePanel gp) { this.gp = gp; }

    private void sendMessage(String message) {
        ChatMessage msg = new ChatMessage(nt.getPlayer(), message);
        nt.send(msg);
    }

    public void addMessage(ChatMessage msg) {
        chatArea.append(msg.getSource() + ": " + msg.getMessage() + "\n");
    }
}
