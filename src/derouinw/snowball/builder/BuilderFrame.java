package derouinw.snowball.builder;

import derouinw.snowball.client.Map.Map;
import derouinw.snowball.client.Map.MapTile;
import derouinw.snowball.client.Map.TileType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Main window for Builder
 */
public class BuilderFrame extends JFrame {
    private JPanel choicePanel;
    private TileLabel[] choices;

    private MapComponent mc;

    private JPanel optionsPanel;
    private JTextField mapNameField;
    private JButton saveButton;

    private int selected;

    public BuilderFrame() {
        super("Snowball Builder");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        choicePanel = new JPanel();
        choicePanel.setLayout(new BoxLayout(choicePanel, BoxLayout.Y_AXIS));
        choices = new TileLabel[TileType.size];
        for (int i = 0; i < choices.length; i++) {
            TileType[] values = TileType.values();
            choices[i] = new TileLabel(values[i].toString(), values[i], i, this);
            MapTile mt = new MapTile(values[i], 0, 0);
            mt.loadImage();
            choices[i].setIcon(new ImageIcon(mt.getImage()));
            choicePanel.add(choices[i]);
        }

        mc = new MapComponent(this);

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout());
        mapNameField = new JTextField(20);
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMap(mc.getMap());
            }
        });
        optionsPanel.add(mapNameField);
        optionsPanel.add(saveButton);

        add(choicePanel, BorderLayout.WEST);
        add(mc, BorderLayout.CENTER);
        add(optionsPanel, BorderLayout.EAST);

        setVisible(true);

        setSelected(0);
    }

    private void saveMap(Map map) {
        String filename = Map.MAPS_DIR + mapNameField.getText();
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(map);
            out.flush();
            fos.close();
        } catch (IOException ioe) {
            System.out.println("IOException in BuilderFrame->saveMap");
        }
    }

    public void setSelected(int s) {
        selected = s;

        Font f = choices[0].getFont();

        for (int i = 0; i < choices.length; i++) {
            if (i == s) {
                choices[i].setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            } else {
                choices[i].setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
            }
        }

        revalidate();
        repaint();
    }

    public TileType getSelectedType() { return choices[selected].getType(); }
}
