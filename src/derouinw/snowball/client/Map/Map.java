package derouinw.snowball.client.Map;

import java.awt.*;
import java.io.Serializable;

/**
 * Contains data and functionality for the game world
 */
public class Map implements Serializable {
    static final int DEFAULT_X = 10;
    static final int DEFAULT_Y = 10;

    private int sizeX, sizeY;
    private MapTile[][] tiles;

    public Map(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        tiles = new MapTile[sizeY][];
        for (int i = 0; i < sizeY; i++) {
            tiles[i] = new MapTile[sizeX];
            for (int j = 0; j < sizeX; j++) {
                tiles[i][j] = new MapTile();
            }
        }
    }

    public Map() {
        this.sizeX = DEFAULT_X;
        this.sizeY = DEFAULT_Y;

        tiles = new MapTile[DEFAULT_Y][];
        for (int i = 0; i < DEFAULT_Y; i++) {
            tiles[i] = new MapTile[DEFAULT_X];
            for (int j = 0; j < DEFAULT_X; j++) {
                tiles[i][j] = new MapTile();
            }
        }
    }

    public int getSizeX() { return sizeX; }
    public int getSizeY() { return sizeY; }

    public MapTile getTile(int x, int y) {
        if (y < tiles.length && x < tiles[y].length) {
            return tiles[y][x];
        }
        return null;
    }

    public void drawMap(Graphics g) {
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                g.drawImage(tiles[i][j].getImage(), j * MapTile.TILE_SIZE, i * MapTile.TILE_SIZE, null);
            }
        }
    }
}
