package derouinw.snowball.client.Game;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import java.awt.event.KeyEvent;

import derouinw.snowball.client.Item.Inventory;
import derouinw.snowball.client.SBClient;

/**
 * Stores player data and functionality
 */
public class Player extends AbstractPlayer {
    private int speed;
    private EventListenerList listenerList = new EventListenerList();

    private Inventory inventory;

    public Player() {
        super();

        x = y = 100;
        speed = 10;
        name = "You";

        sprite = new ImageIcon(SBClient.IMAGES_DIR + "player.png").getImage();

        inventory = new Inventory();
    }

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
        fireStateChanged();
    }

    public void addChangeListener(ChangeListener cl) {
        listenerList.add(ChangeListener.class, cl);
    }

    protected void fireStateChanged() {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -=2 ) {
            if (listeners[i] == ChangeListener.class) {
                ChangeEvent changeEvent = new ChangeEvent(this);
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }
    }
}
