package derouinw.snowball.client;

import javax.swing.*;

/**
 * Main GUI frame
 */
public class ClientFrame extends JFrame {
    private JLabel statusLabel;

    private NetworkThread nt;

    public ClientFrame() {
        super("Snowball");

        // Setup gui elements
        statusLabel = new JLabel("Status: ");
        add(statusLabel);
        pack();

        // logic elements
        nt = new NetworkThread(this);
        setStatus("Unconnected");

        setVisible(true);

        nt.connect("127.0.0.1");
    }

    public void setStatus(String status) {
        statusLabel.setText("Status: " + status);
        revalidate();
        pack();
    }

    public String getStatus() { return statusLabel.getText().substring(8); }
}
