package derouinw.snowball.server;

import derouinw.snowball.server.Message.Message;
import derouinw.snowball.server.Message.StringMessage;

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

    public ServerThread() {
        playerThreads = new ArrayList<PlayerThread>();
    }

    public void run() {
        System.out.println("Server thread started");
        running = true;

        while(running) {
            broadcast("ping");

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addConnection(Socket s) {
        playerThreads.add(new PlayerThread(s));
    }

    private void broadcast(String message) {
        Message msg = new StringMessage("server", message);

        for (int i = 0; i < playerThreads.size(); i++) {
            playerThreads.get(i).send(msg);
        }
    }

    class PlayerThread extends Thread {
        private ObjectInputStream receive;
        private ObjectOutputStream send;
        private boolean running;

        public PlayerThread(Socket s) {
            try {
                s.setSoTimeout(1000);
                send = new ObjectOutputStream(s.getOutputStream());
                receive = new ObjectInputStream(s.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean send(Message message) {
            try {
                send.writeObject(message);
                send.flush();
            } catch (IOException e) {
                System.out.println("IOException: PlayerThread->send");
            }

            return true;
        }

        public void run() {
            System.out.println("Player thread started");
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
}
