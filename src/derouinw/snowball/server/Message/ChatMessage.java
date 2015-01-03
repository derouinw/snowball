package derouinw.snowball.server.Message;

import java.io.Serializable;

/**
 * A simple chat message
 */
public class ChatMessage extends Message implements Serializable {
    private String source;
    private String message;

    public ChatMessage(String source, String message) {
        this.source = source;
        this.message = message;
    }

    public String getSource() { return source; }
    public String getMessage() { return message; }
}
