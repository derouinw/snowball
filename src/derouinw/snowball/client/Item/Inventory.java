package derouinw.snowball.client.Item;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import java.util.ArrayList;

/**
 * Basically a collection of items
 */
public class Inventory {
    private EventListenerList listenerList = new EventListenerList();
    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<Item>();
    }

    public Item getItem(int index) {
        if (index < items.size())
            return items.get(index);
        else
            return null;
    }
    public boolean hasItem(Item item) { return items.contains(item); }

    public void addItem(Item item) { items.add(item); fireStateChanged(); }
    public void setItem(int index, Item item) { items.set(index, item); fireStateChanged(); }

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
