package derouinw.snowball.server.Message;

import java.io.Serializable;

/**
 * Data for a player state change of some sort
 */
public class PlayerDataMessage extends Message implements Serializable {
    String player;
    int x, y;

    public PlayerDataMessage(String player, int x, int y) {
        this.player = player;
        this.x = x;
        this.y = y;
    }
}
