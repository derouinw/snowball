package derouinw.snowball.client.Item;

import derouinw.snowball.client.SBClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Displays a single item in the inventory panel
 */
public class ItemLabel extends JLabel implements MouseListener {
    private Item item;
    private static Image blankItem = new ImageIcon(SBClient.IMAGES_DIR + "blankItem.png").getImage();

    public ItemLabel() {
        item = null;
        addMouseListener(this);
    }

    public Image getSprite() { return (item == null) ? blankItem : item.getSprite(); }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
