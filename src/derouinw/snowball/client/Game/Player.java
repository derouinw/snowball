package derouinw.snowball.client.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import derouinw.snowball.client.SBClient;
import derouinw.snowball.server.Message.PlayerDataMessage;

/**
 * Stores player data and functionality
 */
public class Player {
    private int x, y;
    private int speed;
    private Image sprite;

    public Player() {
        x = y = 100;
        speed = 10;

        sprite = new ImageIcon(SBClient.IMAGES_DIR + "player.png").getImage();
    }

    public Image getImage() { return sprite; }

    public int getX() { return x; }
    public int getY() { return y; }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                y += speed;
                break;
            case KeyEvent.VK_RIGHT:
                x += speed;
                break;
            case KeyEvent.VK_LEFT:
                x -= speed;
                break;
            case KeyEvent.VK_UP:
                y -= speed;
                break;
        }
    }
}
