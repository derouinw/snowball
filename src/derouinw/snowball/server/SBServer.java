package derouinw.snowball.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main entry point for server
 */
public class SBServer {
    final static int PORT = 2000;
    final static int MAX_CONNECTIONS = 100;

    private ServerThread st;

    public SBServer() {
        System.out.println("Server started");

        try {
            ServerSocket ss = new ServerSocket(PORT);
            st = new ServerThread();

            int connections = 0;
            System.out.println("Accepting connections");
            while(connections < MAX_CONNECTIONS) {
                if (connections == 1)
                    st.start();

                Socket s = ss.accept();
                st.addConnection(s);

                connections++;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SBServer sbs = new SBServer();
    }
}
