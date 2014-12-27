package derouinw.snowball.client;

import derouinw.snowball.client.Game.GamePanel;
import derouinw.snowball.client.Item.InventoryPanel;

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

    private JPanel actionsPanel;
    private JButton inventoryButton;
    private InventoryPanel inventoryPanel;

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

                nt = new NetworkThread(ClientFrame.this, gp);
                gp = new GamePanel(ClientFrame.this, nt);
                nt.setGp(gp);
                nt.setIp(ip);
                nt.setPlayer(name);
                nt.start();
                gp.setPlayerName(name);

                ipField.setEnabled(false);
                userField.setEnabled(false);
                connectButton.setEnabled(false);
                // TODO: validate ip and username
            }
        });

        statusPanel.add(statusLabel);
        statusPanel.add(ipField);
        statusPanel.add(userField);
        statusPanel.add(connectButton);

        actionsPanel = new JPanel();
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
        actionsPanel.setFocusable(false);
        inventoryButton = new JButton("Show Inventory");
        inventoryButton.setFocusable(false);
        inventoryPanel = new InventoryPanel(gp);
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inventoryPanel.getParent() == actionsPanel) {
                    // panel is showing, remove it
                    actionsPanel.remove(inventoryPanel);
                    inventoryButton.setText("Show Inventory");
                    pack();
                    revalidate();
                    repaint();
                } else {
                    // panel isn't showing, add it
                    actionsPanel.add(inventoryPanel,1);
                    inventoryButton.setText("Hide Inventory");
                    pack();
                    revalidate();
                    repaint();
                }
            }
        });
        actionsPanel.add(inventoryButton);
        //actionsPanel.add(Box.createGlue());
        //actionsPanel.add(Box.createHorizontalGlue());

        add(statusPanel, BorderLayout.NORTH);
        pack();

        // logic elements
        setStatus("Unconnected");

        setVisible(true);
    }

    public void setStatus(String status) {
        statusLabel.setText("Status: " + status);
        if (status.equals("Connected")) {
            add(gp, BorderLayout.CENTER);
            add(actionsPanel, BorderLayout.EAST);
            ipField.setVisible(false);
            userField.setVisible(false);
            connectButton.setVisible(false);
            gp.sendPlayerData();
        } else if (status.equals("Disconnected")) {
            remove(gp);
            remove(actionsPanel);
            ipField.setVisible(true);
            ipField.setEnabled(true);
            userField.setVisible(true);
            userField.setEnabled(true);
            connectButton.setVisible(true);
            connectButton.setEnabled(true);
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

    public void disconnect() {
        gp.disconnect();
        setStatus("Disconnected");
    }

    public void reconnect() {
        connectButton.doClick();
    }
}
