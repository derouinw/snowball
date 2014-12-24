package derouinw.snowball.client.Game;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Listens for changes in player data
 */
public class PlayerListener implements ChangeListener {
    private GamePanel gp;

    public PlayerListener(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        gp.sendPlayerData();
    }
}
