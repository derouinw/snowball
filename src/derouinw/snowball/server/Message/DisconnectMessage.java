package derouinw.snowball.server.Message;

import java.io.Serializable;

/**
 * Message sent from the server to let players know someone disconnected
 */
public class DisconnectMessage extends Message implements Serializable {
    private String player;

    public DisconnectMessage(String player) {
        this.player = player;
    }

    public String getPlayer() { return player; }
}
