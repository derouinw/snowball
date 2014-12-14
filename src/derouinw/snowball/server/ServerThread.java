package derouinw.snowball.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handles all server interactions
 */
public class ServerThread extends Thread {
    private ArrayList<PlayerThread> playerThreads;

    public ServerThread() {
        playerThreads = new ArrayList<PlayerThread>();
    }

    public void run() {

    }

    public void addConnection(Socket s) {
        playerThreads.add(new PlayerThread(s));
    }

    class PlayerThread extends Thread {
        ObjectInputStream receive;
        ObjectOutputStream send;

        public PlayerThread(Socket s) {
            try {
                send = new ObjectOutputStream(s.getOutputStream());
                receive = new ObjectInputStream(s.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
