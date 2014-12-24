package derouinw.snowball.client.Game;

import com.sun.deploy.util.SessionState;
import derouinw.snowball.client.ClientFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Main graphical panel that plays the game
 */
public class GamePanel extends JPanel implements KeyListener {
    private Player p;
    private ClientFrame cf;

    public GamePanel(ClientFrame cf) {
        super();
        setPreferredSize(new Dimension(500, 500));

        p = new Player();
        this.cf = cf;
    }

    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        g.setColor(Color.WHITE);
        g.drawRect(0,0,width,height);

        g.setColor(Color.BLACK);
        g.drawString("Loaded", 10, 10);

        g.drawImage(p.getImage(), p.getX(), p.getY(), null);
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
