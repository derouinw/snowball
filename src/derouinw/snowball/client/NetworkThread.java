package derouinw.snowball.client;

import derouinw.snowball.client.Game.GamePanel;
import derouinw.snowball.client.Map.Map;
import derouinw.snowball.server.Message.*;
import derouinw.snowball.server.SBServer;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Main networking thread for client
 */
public class NetworkThread extends Thread {
    private ObjectInputStream receive;
    private ObjectOutputStream send;
    private ClientFrame cf;
    private GamePanel gp;

    private boolean running;
    private boolean connected;
    private String username;

    private String ip;
    private String player;

    public NetworkThread(ClientFrame cf, GamePanel gp) {
        super();
        this.cf = cf;
        this.gp = gp;
        connected = false;
    }

    public void setIp(String ip) { this.ip = ip; }
    public void setPlayer(String player) { this.player = player; }

    public void connect(String host, String username) {
        this.username = username;

        while (!setupConnection(host, SBServer.PORT)) {
            System.out.println("Attempting connection at " + host + ":" + SBServer.PORT);
        }
        cf.setStatus("Connected");
    }

    private boolean setupConnection(String host, int port) {
        try {
            Socket s = new Socket(host, port);
            s.setSoTimeout(1000);
            send = new ObjectOutputStream(s.getOutputStream());
            receive = new ObjectInputStream(s.getInputStream());
        } catch (SocketTimeoutException ste) {
            System.out.println("timeout");
            return false;
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public void run() {
        connect(ip, player);
        System.out.println("Network thread started");
        running = true;

        while (!connected) {
            Message sendMsg = new ConnectMessage(player);
            send(sendMsg);

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message msg = receive();
            if (msg instanceof ConnectMessage) {
                if (((ConnectMessage)msg).getPlayer().equals(player)) connected = true;
            }
        }

        UsernameMessage uMsg = new UsernameMessage(username);
        send(uMsg);

        while (running) {
            Message msg = receive();

            if (msg instanceof StringMessage) {
                System.out.println("Received string message: \"" + ((StringMessage) msg).getMessage() + "\"");
            } else if (msg instanceof PlayerDataMessage) {
                PlayerDataMessage pdMsg = (PlayerDataMessage)msg;
                System.out.println("Received player data message: [player: " + pdMsg.getPlayer() + ", x: " + pdMsg.getX() + ", y: " + pdMsg.getY() + "]");

                gp.receive(msg);
            } else if (msg instanceof DisconnectMessage) {
                DisconnectMessage dMsg = (DisconnectMessage)msg;
                System.out.println("Received disconnect message: " + dMsg.getPlayer());

                gp.receive(msg);
            } else if (msg instanceof MapDataMessage) {
                MapDataMessage mdMsg = (MapDataMessage)msg;
                System.out.println("Received map data message");

                gp.receive(msg);
            }
        }
    }

    private Message receive() {
        Message msg = null;

        try {
            msg = (Message) receive.readObject();
        } catch (EOFException eofe) {
           disconnect();
        } catch (SocketTimeoutException ste) {
            return null; /* Timeout, its k */
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException in PlayerThread->receive");
        } catch (IOException e) {
            disconnect();
            cf.reconnect();
        } catch (ClassCastException cce) {
            disconnect();
            cf.reconnect();
        } catch (NullPointerException npe) {
            System.out.println("NullPointerException in PlayerThread->receive");
        }

        return msg;
    }

    public void send(Message msg) {
        try {
            send.writeObject(msg);
            send.flush();
        } catch (IOException e) {
            System.out.println("IOException: NetworkThread->send");
        }
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public void disconnect() {
        System.out.println("Disconnected");
        running = false;
        cf.disconnect();
    }

    public void disconnect(boolean sendUp) {
        System.out.println("Disconnected");
        running = false;
        if (sendUp) cf.disconnect();
    }
}
