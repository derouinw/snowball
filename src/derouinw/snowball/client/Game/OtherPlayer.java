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
        this.name = name;

        sprite = new ImageIcon(SBClient.IMAGES_DIR + "otherPlayer.png").getImage();
    }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setName(String name) { this.name = name; }
}
