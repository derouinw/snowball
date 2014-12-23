package derouinw.snowball.server.Message;

import java.io.Serializable;

/**
 * Handles a simple string message
 */
public class StringMessage extends Message implements Serializable {
    private String player;
    private String message;

    public StringMessage(String player, String message) {
        this.player = player;
        this.message = message;
    }

    public String getPlayer() { return player; }
    public String getMessage() { return message; }
}
