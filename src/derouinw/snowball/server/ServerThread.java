package derouinw.snowball.server;

import derouinw.snowball.client.Map.Map;
import derouinw.snowball.client.Map.MapTile;
import derouinw.snowball.client.Map.TileType;
import derouinw.snowball.server.Message.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Handles all server interactions
 */
public class ServerThread extends Thread {
    private ArrayList<PlayerThread> playerThreads;
    private boolean running;

    private Map m;

    public ServerThread() {
        playerThreads = new ArrayList<PlayerThread>();

        m = new Map(15, 15);
    }

    protected synchronized Map getMap() { return m; }

    public void run() {
        System.out.println("Server thread started");
        running = true;

        while(running) {

        }
    }

    public void addConnection(Socket s) {
        playerThreads.add(new PlayerThread(s, playerThreads.size(), this));

        for (int i = 0; i < playerThreads.size()-1; i++) {
            PlayerThread pt = playerThreads.get(i);
            PlayerDataMessage pdMsg = new PlayerDataMessage(pt.getPlayer(), pt.getX(), pt.getY());
            playerThreads.get(playerThreads.size()-1).send(pdMsg);
        }
    }

    private void broadcast(String message) {
        Message msg = new StringMessage("server", message);

        for (int i = 0; i < playerThreads.size(); i++) {
            playerThreads.get(i).send(msg);
        }
    }

    private void broadcast(Message msg) {
        for (int i = 0; i < playerThreads.size(); i++) {
            playerThreads.get(i).send(msg);
        }
    }

    protected void sendUpdatedMap() {
        MapDataMessage mdMsg = new MapDataMessage(getMap());
        broadcast(mdMsg);
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

    protected void receive(Message msg, String source) {
        if (msg instanceof PlayerDataMessage) {
            for (int i = 0; i < playerThreads.size(); i++) {
                if (playerThreads.get(i).getPlayer() != source) playerThreads.get(i).send(msg);
            }
        }
    }

    class PlayerThread extends Thread {
        private ObjectInputStream receive;
        private ObjectOutputStream send;
        private boolean running;

        private int ptNum;
        private ServerThread st;

        // player data
        String name = "";
        int x = 0, y = 0;

        public String getPlayer() { return name; }
        public int getX() { return x; }
        public int getY() { return y; }

        public PlayerThread(Socket s, int num, ServerThread st) {
            this.ptNum = num;
            this.st = st;

            try {
                s.setSoTimeout(1000);
                send = new ObjectOutputStream(s.getOutputStream());
                receive = new ObjectInputStream(s.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            start();
        }

        public int getNum() { return ptNum; }

        public boolean send(Message message) {
            try {
                send.writeObject(message);
                send.flush();
            } catch (IOException e) {
                System.out.println("Player " + ptNum + " disconnected");
                running = false;
                disconnect(ptNum);
            }

            return true;
        }

        public void run() {
            System.out.println("Player thread started");
            running = true;

            MapDataMessage mdMsg = new MapDataMessage(getMap());
            send(mdMsg);

            while (running) {
                Message msg = receive();

                if (msg instanceof StringMessage) {
                    System.out.println("Received string message: \"" + ((StringMessage) msg).getMessage() + "\"");
                } else if (msg instanceof PlayerDataMessage) {
                    PlayerDataMessage pdMsg = (PlayerDataMessage)msg;
                    System.out.println("Received player data message: [player: " + pdMsg.getPlayer() + ", x: " + pdMsg.getX() + ", y: " + pdMsg.getY() + "]");

                    name = pdMsg.getPlayer();
                    x = pdMsg.getX();
                    y = pdMsg.getY();

                    int tX = x / MapTile.TILE_SIZE;
                    int tY = y / MapTile.TILE_SIZE;

                    getMap().getTile(tX, tY).setType(TileType.Grass);
                    System.out.println(getMap().getTile(0,0).getType());
                    sendUpdatedMap();

                    st.receive(msg, name);
                } else if (msg instanceof UsernameMessage) {
                    UsernameMessage uMsg = (UsernameMessage)msg;
                    name = uMsg.getUsername();
                }
            }
        }

        private Message receive() {
            Message msg = null;

            try {
                msg = (Message) receive.readObject();
            } catch (EOFException eofe) {
                System.out.println("Player " + ptNum + " disconnected");
                running = false;
                disconnect(ptNum);
            } catch (SocketTimeoutException ste) {
                return null; /* Timeout, its k */
            } catch (ClassNotFoundException e) {
                System.out.println("ClassNotFoundException in PlayerThread->receive");
            } catch (IOException e) {
                System.out.println("IOException in PlayerThread->receive");
            }

            return msg;
        }
    }
}
