package derouinw.snowball.client.Item;

import java.util.ArrayList;

/**
 * Basically a collection of items
 */
public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<Item>();
    }

    public Item getItem(int index) { return items.get(index); }
    public boolean hasItem(Item item) { return items.contains(item); }

    public void addItem(Item item) { items.add(item); }
    public void setItem(int index, Item item) { items.set(index, item); }
}
