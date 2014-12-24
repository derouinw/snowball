package derouinw.snowball.client.Game;

import java.awt.*;

/**
 * Structure for both Player and OtherPlayer
 */
public abstract class AbstractPlayer {
    protected int x, y;
    protected Image sprite;
    protected String name;

    public Image getImage() { return sprite; }
    public int getX() { return x; }
    public int getY() { return y; }
    public String getName() { return name; }
}
