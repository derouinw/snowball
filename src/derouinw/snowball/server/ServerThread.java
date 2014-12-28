package derouinw.snowball.server;

import derouinw.snowball.client.Map.Map;
import derouinw.snowball.server.Message.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handles all server interactions
 */
public class ServerThread extends Thread {
    private ArrayList<PlayerThread> playerThreads;
    private ArrayList<Room> rooms;
    private boolean running;

    private Map m;

    public ServerThread() {
        playerThreads = new ArrayList<PlayerThread>();
        rooms = new ArrayList<Room>();

        String filename = Map.MAPS_DIR + "default.map";
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fis);
            m = (Map)in.readObject();
            in.close();
            fis.close();
        } catch (IOException ioe) {
            System.out.println("IOException in ServerThread->ServerThread");
            m = new Map();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException in ServerThread->ServerThread");
            m = new Map();
        }

        rooms.add(new Room(m, this));
    }

    protected synchronized Map getMap() { return m; }

    public void run() {
        System.out.println("Server thread started");
        running = true;

        while(running) {

        }
    }

    public void addConnection(Socket s) {
        PlayerThread pt = new PlayerThread(s, playerThreads.size(), this);
        playerThreads.add(pt);
        rooms.get(0).addPlayerThread(pt);
    }

    protected void sendPlayerData(int ptNum) {
        for (int i = 0; i < playerThreads.size()-1; i++) {
            PlayerThread pt = playerThreads.get(i);
            PlayerDataMessage pdMsg = new PlayerDataMessage(pt.getPlayer(), pt.getX(), pt.getY());
            playerThreads.get(ptNum).send(pdMsg);
        }
    }

    private void broadcast(Message msg) {
        for (int i = 0; i < playerThreads.size(); i++) {
            playerThreads.get(i).send(msg);
        }
    }

    protected void disconnect(int ptNum) {
        String user = null;
        for (int i = 0; i < playerThreads.size(); i++) {
            if (playerThreads.get(i).getNum() == ptNum) {
                user = playerThreads.get(i).getPlayer();
                playerThreads.remove(i);
            }
        }
        if (user != null) {
            DisconnectMessage dMsg = new DisconnectMessage(user);
            broadcast(dMsg);
        } else {
            System.out.println("oops");
        }
    }

    private PlayerThread getPlayerThread(String player) {
        for (int i = 0; i < playerThreads.size(); i++) {
            if (playerThreads.get(i).getPlayer().equals(player)) return playerThreads.get(i);
        }
        return null;
    }

    public void receive(Message msg, String source) {
        if (msg instanceof PlayerDataMessage) {
            for (int i = 0; i < playerThreads.size(); i++) {
                if (playerThreads.get(i).getPlayer() != source) playerThreads.get(i).send(msg);
            }
        } else if (msg instanceof ConnectMessage) {
            getPlayerThread(source).setRoom(rooms.get(0));
        }
    }
}
