package derouinw.snowball.server;

import derouinw.snowball.server.Message.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Connection to a single player
 */
public class PlayerThread extends Thread {
    private ObjectInputStream receive;
    private ObjectOutputStream send;
    private boolean running;
    private boolean connected = false;

    private int ptNum;
    private ServerThread st;

    private Room room;

    private boolean isAdmin = false;

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

    public boolean isAdmin() { return isAdmin; }
    public int getNum() { return ptNum; }
    public void setRoom(Room room) {
        this.room = room;
        room.addPlayerThread(this);
    }

    public boolean send(Message message) {
        try {
            send.writeObject(message);
            send.flush();
        } catch (IOException e) {
            System.out.println("Player " + ptNum + " disconnected");
            running = false;
            st.disconnect(ptNum);
        }

        return true;
    }

    public void run() {
        System.out.println("Player thread started");
        running = true;

        while (!connected) {
            Message msg = receive();
            if (msg instanceof ConnectMessage) {
                name = ((ConnectMessage) msg).getPlayer();
                connected = true;
                send(msg);
            }
            st.receive(msg, name);
        }

        MapDataMessage mdMsg = new MapDataMessage(st.getMap());
        send(mdMsg);
        st.sendPlayerData(ptNum);

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

                st.receive(msg, name);
            } else if (msg instanceof UsernameMessage) {
                UsernameMessage uMsg = (UsernameMessage)msg;
                name = uMsg.getUsername();
                if (name.equals("admin")) {
                    isAdmin = true;
                }
            } else if (msg instanceof ChatMessage) {
                ChatMessage cMsg = (ChatMessage)msg;
                System.out.println("Received chat message from " + cMsg.getSource() + ": " + cMsg.getMessage());

                st.receive(msg, name);
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
            st.disconnect(ptNum);
        } catch (SocketTimeoutException ste) {
            return null; /* Timeout, its k */
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException in PlayerThread->receive");
        } catch (IOException e) {
            System.out.println("Player " + ptNum + "Disconnected");
            running = false;
            st.disconnect(ptNum);
        }

        return msg;
    }
}
