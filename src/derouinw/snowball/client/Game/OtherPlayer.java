package derouinw.snowball.client.Game;

import derouinw.snowball.client.SBClient;

import javax.swing.*;

/**
 * An other player
 */
public class OtherPlayer extends AbstractPlayer {

    public OtherPlayer(String name, int x, int y) {
        super();

        this.x = x;
        this.y = y;

        sprite = new ImageIcon(SBClient.IMAGES_DIR + "otherPlayer.png").getImage();

        setName(name);
    }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}
