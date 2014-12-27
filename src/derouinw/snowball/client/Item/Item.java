package derouinw.snowball.client.Item;

import java.awt.*;

/**
 * Basic framework for a single item
 */
public class Item {
    static int numItems = 0;

    private int id;
    private String name;
    private Image sprite;

    public Item() {
        id = numItems++;
    }

    public String getName() { return name; }
    public Image getSprite() { return sprite; }
}
