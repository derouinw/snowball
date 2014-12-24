package derouinw.snowball.server.Message;

import java.io.Serializable;

/**
 * Sends username to server
 */
public class UsernameMessage extends Message implements Serializable {
    private String username;

    public UsernameMessage(String username) {
        this.username = username;
    }

    public String getUsername() { return username; }
}
