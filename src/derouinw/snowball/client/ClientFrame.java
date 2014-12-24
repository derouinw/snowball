package derouinw.snowball.client;

import derouinw.snowball.client.Game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Main GUI frame
 */
public class ClientFrame extends JFrame implements KeyListener {
    private JLabel statusLabel;

    private NetworkThread nt;
    private GamePanel gp;

    public ClientFrame() {
        super("Snowball");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);

        // Setup gui elements
        statusLabel = new JLabel("Status: ");
        add(statusLabel, BorderLayout.NORTH);
        pack();

        // logic elements
        nt = new NetworkThread(this, gp);
        gp = new GamePanel(this, nt);
        nt.setGp(gp);
        setStatus("Unconnected");

        setVisible(true);

        nt.connect("127.0.0.1");
    }

    public void setStatus(String status) {
        statusLabel.setText("Status: " + status);
        if (status.equals("Connected")) {
            add(gp, BorderLayout.CENTER);
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
