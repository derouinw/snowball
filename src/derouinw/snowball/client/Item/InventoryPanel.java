package derouinw.snowball.client.Item;

import derouinw.snowball.client.Game.GamePanel;
import derouinw.snowball.client.Map.MapTile;

import javax.swing.*;
import java.awt.*;

/**
 * Shows an inventory on screen
 */
public class InventoryPanel extends JPanel {
    public static final int WIDTH = 6, HEIGHT = 5;
    private Inventory inv;
    private GamePanel gp;

    private ItemLabel[][] labels;

    public InventoryPanel(GamePanel gp) {
        super();

        this.gp = gp;

        setLayout(new GridLayout(HEIGHT, WIDTH));
        labels = new ItemLabel[HEIGHT][];
        for (int i = 0; i < HEIGHT; i++) {
            labels[i] = new ItemLabel[WIDTH];
            for (int j = 0; j < WIDTH; j++) {
                labels[i][j] = new ItemLabel();
                add(labels[i][j]);
            }
        }

        setSize(new Dimension(WIDTH * MapTile.TILE_SIZE, HEIGHT * MapTile.TILE_SIZE));

        gp.setInventoryPanel(this);
    }

    public void setInventory(Inventory inv) { this.inv = inv; }

    public void paintComponent(Graphics g) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                g.drawImage(labels[y][x].getSprite(), x * MapTile.TILE_SIZE, y * MapTile.TILE_SIZE, null);
            }
        }
    }

    public void updateLabels() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                labels[y][x].setItem(inv.getItem(y*WIDTH + x));
            }
        }
    }
}
