package derouinw.snowball.client.Item;

import java.awt.*;
import java.io.Serializable;

/**
 * Basic framework for a single item
 */
public class Item implements Serializable {
    public static final String ITEMS_DIR = "./items/";
    static int numItems = 0;

    private int id;
    private String name;
    private transient Image sprite;

    public Item(String name) {
        id = numItems++;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Image getSprite() { return sprite; }
}
