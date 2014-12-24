package derouinw.snowball.client;

import derouinw.snowball.client.Game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Main GUI frame
 */
public class ClientFrame extends JFrame implements KeyListener {
    private JPanel statusPanel;
    private JLabel statusLabel;
    private JTextField ipField;
    private JTextField userField;
    private JButton connectButton;

    private NetworkThread nt;
    private GamePanel gp;

    public ClientFrame() {
        super("Snowball");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);

        // Setup gui elements
        statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusLabel = new JLabel("Status: ");
        ipField = new JTextField(20);
        ipField.setText("127.0.0.1");
        userField = new JTextField(20);
        userField.setText("Player");
        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipField.getText();
                String name = userField.getText();
                gp.setPlayerName(name);

                ipField.setEnabled(false);
                userField.setEnabled(false);
                connectButton.setEnabled(false);
                nt.connect(ip, name); // TODO: validate ip and username
            }
        });

        statusPanel.add(statusLabel);
        statusPanel.add(ipField);
        statusPanel.add(userField);
        statusPanel.add(connectButton);

        add(statusPanel, BorderLayout.NORTH);
        pack();

        // logic elements
        nt = new NetworkThread(this, gp);
        gp = new GamePanel(this, nt);
        nt.setGp(gp);
        setStatus("Unconnected");

        setVisible(true);
    }

    public void setStatus(String status) {
        statusLabel.setText("Status: " + status);
        if (status.equals("Connected")) {
            add(gp, BorderLayout.CENTER);
            ipField.setVisible(false);
            userField.setVisible(false);
            connectButton.setVisible(false);
            gp.sendPlayerData();
        }
        revalidate();
        pack();
    }

    public String getStatus() { return statusLabel.getText().substring(8); }

    @Override
    public void keyTyped(KeyEvent e) {
        gp.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gp.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gp.keyReleased(e);
    }
}
