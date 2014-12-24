package derouinw.snowball.client.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

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

    public void setName(String name) {
        if (!name.equals(this.name)) {
            this.name = name;

            // update name on image
            // Create a buffered image with transparency
            BufferedImage bimage = new BufferedImage(sprite.getWidth(null), sprite.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            // Draw the image on to the buffered image
            Graphics2D g = bimage.createGraphics();
            g.drawImage(sprite, 0, 0, null);
            g.setColor(Color.black);
            g.drawString(name, 5, 10);
            g.dispose();

            sprite = bimage;
        }
    }
}
