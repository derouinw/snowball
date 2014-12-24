package derouinw.snowball.client;
import derouinw.snowball.server.Message.*;

/**
 * Main entry point for client
 */
public class SBClient {
    public static final String IMAGES_DIR = "./images/";

    public static void main(String[] args) {
        System.out.println("Client started");

        ClientFrame cf = new ClientFrame();
    }
}
