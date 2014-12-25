package derouinw.snowball.builder;

import derouinw.snowball.client.Map.TileType;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Selected label for tiles on left-hand side
 */
public class TileLabel extends JLabel implements MouseListener {
    private TileType type;
    private int index;
    private BuilderFrame bf;

    public TileLabel(String s, TileType type, int index, BuilderFrame bf) {
        super(s);
        this.type = type;
        this.index = index;
        this.bf = bf;

        addMouseListener(this);
    }

    public TileType getType() { return type; }

    @Override
    public void mouseClicked(MouseEvent e) {
        bf.setSelected(index);
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
}
