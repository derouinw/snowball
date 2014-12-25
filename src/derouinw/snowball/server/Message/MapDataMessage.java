package derouinw.snowball.server.Message;

import derouinw.snowball.client.Map.Map;

import java.io.Serializable;

/**
 * Sends a map to the players
 */
public class MapDataMessage extends Message implements Serializable {
    private Map m;

    public MapDataMessage(Map m) {
        this.m = m;
    }

    public Map getMap() { return m; }
}
