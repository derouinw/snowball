package derouinw.snowball.builder;

import derouinw.snowball.client.Map.Map;
import derouinw.snowball.client.Map.MapTile;
import derouinw.snowball.client.Map.TileType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Main part of builder, shows map
 */
public class MapComponent extends JComponent implements MouseMotionListener {
    private Map m;
    private BuilderFrame bf;

    public MapComponent(BuilderFrame bf) {
        super();

        this.bf = bf;

        m = new Map();
        addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {
        m.drawMap(g);
    }

    public void mouseDragged(MouseEvent e) {
        int tX = e.getX() / MapTile.TILE_SIZE;
        int tY = e.getY() / MapTile.TILE_SIZE;

        paintTile(tX, tY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getClickCount() > 0) {
            int tX = e.getX() / MapTile.TILE_SIZE;
            int tY = e.getY() / MapTile.TILE_SIZE;

            paintTile(tX, tY);
        }
    }

    private void paintTile(int tX, int tY) {
        MapTile t = m.getTile(tX, tY);
        if (t != null) {
            TileType type = bf.getSelectedType();
            t.setType(type);
        }

        revalidate();
        repaint();
    }

    public Map getMap() {
        return m;
    }
    public void setMap(Map m) {
        this.m = m;

        int sizeY = this.m.getSizeY();
        int sizeX = this.m.getSizeX();

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                MapTile tile = this.m.getTile(x, y);
                tile.loadImage();
            }
        }
    }
}
