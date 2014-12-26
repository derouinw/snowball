package derouinw.snowball.server.Message;

import java.io.Serializable;

/**
 * Initial handshake between server and client
 */
public class ConnectMessage extends Message implements Serializable {
    private String player;

    public ConnectMessage(String player) {
        this.player = player;
    }

    public String getPlayer() { return player; }
}
