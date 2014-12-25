package derouinw.snowball.client.Game;

import derouinw.snowball.client.ClientFrame;
import derouinw.snowball.client.Map.Map;
import derouinw.snowball.client.Map.MapTile;
import derouinw.snowball.client.NetworkThread;
import derouinw.snowball.server.Message.DisconnectMessage;
import derouinw.snowball.server.Message.MapDataMessage;
import derouinw.snowball.server.Message.Message;
import derouinw.snowball.server.Message.PlayerDataMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Main graphical panel that plays the game
 */
public class GamePanel extends JPanel implements KeyListener {
    private ClientFrame cf;
    private NetworkThread nt;

    private Player p;
    private ArrayList<OtherPlayer> otherPlayers = new ArrayList<OtherPlayer>();

    private Map m;

    public GamePanel(ClientFrame cf, NetworkThread nt) {
        super();
        setPreferredSize(new Dimension(500, 500));
        addKeyListener(this);
        setFocusable(true);

        this.cf = cf;
        this.nt = nt;

        p = new Player();
        p.addChangeListener(new PlayerListener(this));

        m = new Map();
    }

    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        g.setColor(Color.WHITE);
        g.drawRect(0,0,width,height);

        m.drawMap(g);

        g.drawImage(p.getImage(), p.getX(), p.getY(), null);
        for (int i = 0; i < otherPlayers.size(); i++) {
            OtherPlayer op = otherPlayers.get(i);
            g.drawImage(op.getImage(), op.getX(), op.getY(), null);
        }

    }

    public void receive(Message msg) {
        if (msg instanceof PlayerDataMessage) {
            PlayerDataMessage pdMsg = (PlayerDataMessage)msg;
            boolean newOther = true;
            int i;
            for (i = 0; i < otherPlayers.size(); i++) {
                if (otherPlayers.get(i).getName() == pdMsg.getPlayer()) {
                    newOther = false;
                    break;
                }
            }
            if (newOther) {
                otherPlayers.add(new OtherPlayer(pdMsg.getPlayer(), pdMsg.getX(), pdMsg.getY()));
            } else {
                OtherPlayer op = otherPlayers.get(i);
                op.setName(pdMsg.getPlayer());
                op.setX(pdMsg.getX());
                op.setY(pdMsg.getY());
            }
            revalidate();
            repaint();
        } else if (msg instanceof DisconnectMessage) {
            DisconnectMessage dMsg = ((DisconnectMessage) msg);
            for (int i = 0; i < otherPlayers.size(); i++) {
                if (dMsg.getPlayer().equals(otherPlayers.get(i).getName())) otherPlayers.remove(i);
            }
            revalidate();
            repaint();
        } else if (msg instanceof MapDataMessage) {
            MapDataMessage mdMsg = (MapDataMessage)msg;
            loadMap(mdMsg.getMap());
            revalidate();
            repaint();
        }
    }

    public void setPlayerName(String name) { p.setName(name); }

    // MapTile images are transient, so they have to be loaded again
    private void loadMap(Map m) {
        this.m = m;

        int sizeY = this.m.getSizeY();
        int sizeX = this.m.getSizeX();

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                MapTile tile = this.m.getTile(x, y);
                tile.loadImage();
            }
        }

        //System.out.println(m.getTile(0,0).getType());
    }

    public void sendPlayerData() {
        String name = p.getName();
        int x = p.getX();
        int y = p.getY();

        PlayerDataMessage pdMsg = new PlayerDataMessage(name, x, y);
        nt.send(pdMsg);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_UP:
                p.keyPressed(e);
                break;
        }
        cf.revalidate();
        cf.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
