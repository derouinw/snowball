package derouinw.snowball.client;

import derouinw.snowball.server.Message.Message;
import derouinw.snowball.server.Message.StringMessage;
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

    private boolean running;

    public NetworkThread(ClientFrame cf) {
        super();
        this.cf = cf;
    }

    public void connect(String host) {
        while (!setupConnection(host, SBServer.PORT)) {
            System.out.println("Attempting connection at " + host + ":" + SBServer.PORT);
        }
        cf.setStatus("Connected");
        start();
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
        System.out.println("Network thread started");
        running = true;

        while (running) {
            Message msg = receive();

            if (msg instanceof StringMessage) {
                System.out.println("Received string message: \"" + ((StringMessage) msg).getMessage() + "\"");
            }
        }
    }

    private Message receive() {
        Message msg = null;

        try {
            msg = (Message) receive.readObject();
        } catch (EOFException eofe) {
            System.out.println("EOFException in PlayerThread->receive");
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
