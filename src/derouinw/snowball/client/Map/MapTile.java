package derouinw.snowball.client.Map;

import derouinw.snowball.client.SBClient;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * A single map tile
 */
public class MapTile implements Serializable {
    public static final int TILE_SIZE = 50;

    private TileType type;
    private int x, y;
    private transient Image sprite;

    public MapTile() {
        type = TileType.Dirt;
        x = y = 0;

        loadImage();
    }

    public MapTile(TileType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;

        loadImage();
    }

    public TileType getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Image getImage() { return sprite; }

    public void setType(TileType type) {
        this.type = type;
        loadImage();
    }

    public void loadImage() {
        switch (type) {
            case Dirt:
                sprite = new ImageIcon(SBClient.IMAGES_DIR + "dirt.png").getImage();
                break;
            case Grass:
                sprite = new ImageIcon(SBClient.IMAGES_DIR + "grass.png").getImage();
                break;
        }
    }
}
