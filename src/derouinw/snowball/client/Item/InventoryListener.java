package derouinw.snowball.client.Item;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Update panel when items are changed
 */
public class InventoryListener implements ChangeListener {
    private InventoryPanel ip;

    public InventoryListener(InventoryPanel ip) {
        this.ip = ip;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        ip.updateLabels();
    }
}
