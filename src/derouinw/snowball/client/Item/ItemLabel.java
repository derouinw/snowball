package derouinw.snowball.client.Item;

import derouinw.snowball.client.SBClient;

import javax.swing.*;
import java.awt.*;

/**
 * Displays a single item in the inventory panel
 */
public class ItemLabel extends JLabel {
    private Item item;
    private static Image blankItem = new ImageIcon(SBClient.IMAGES_DIR + "blankItem.png").getImage();

    public ItemLabel() {
        item = null;
    }

    public Image getSprite() { return (item == null) ? blankItem : item.getSprite(); }
}
