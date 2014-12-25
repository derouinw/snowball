package derouinw.snowball.builder;

import derouinw.snowball.client.Map.Map;
import derouinw.snowball.client.Map.MapTile;
import derouinw.snowball.client.Map.TileType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Main part of builder, shows map
 */
public class MapComponent extends JComponent implements MouseListener {
    private Map m;
    private BuilderFrame bf;

    public MapComponent(BuilderFrame bf) {
        super();

        this.bf = bf;

        m = new Map();
        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        m.drawMap(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int tX = e.getX() / MapTile.TILE_SIZE;
        int tY = e.getY() / MapTile.TILE_SIZE;

        MapTile t = m.getTile(tX, tY);
        if (t != null) {
            TileType type = bf.getSelectedType();
            t.setType(type);
        }

        revalidate();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public Map getMap() {
        return m;
    }
}
