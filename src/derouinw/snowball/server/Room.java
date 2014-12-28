package derouinw.snowball.server;

import derouinw.snowball.client.Map.Map;
import derouinw.snowball.server.Message.MapDataMessage;

import java.util.ArrayList;

/**
 * A single room is an area in the game world
 */
public class Room {
    private Map m;
    private ServerThread st;
    private ArrayList<PlayerThread> playerThreads;

    public Room(Map m, ServerThread st) {
        this.m = m;
        this.st = st;
    }

    public void addPlayerThread(PlayerThread pt) {
        playerThreads.add(pt);
    }

    public void removePlayerThread(PlayerThread pt) {
        if (playerThreads.contains(pt)) playerThreads.remove(pt);
    }

    public void sendMapData() {
        for (PlayerThread pt : playerThreads) {
            pt.send(new MapDataMessage(m));
        }
    }
}
