package derouinw.snowball.builder;

import derouinw.snowball.client.Map.Map;
import derouinw.snowball.client.Map.MapTile;
import derouinw.snowball.client.Map.TileType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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
    private JButton loadButton;

    private JTextField sizeXField;
    private JTextField sizeYField;
    private JButton newMapButton;

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
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        mapNameField = new JTextField(20);
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMap(mc.getMap());
            }
        });
        loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMap("maps/" + mapNameField.getText());
            }
        });
        sizeXField = new JTextField(10);
        sizeXField.setText("Columns");
        sizeYField = new JTextField(10);
        sizeYField.setText("Rows");
        newMapButton = new JButton("New Map");
        newMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sizeX = Integer.valueOf(sizeXField.getText());
                int sizeY = Integer.valueOf(sizeYField.getText());
                mc.setMap(new Map(sizeX, sizeY));
                revalidate();
                repaint();
            }
        });
        optionsPanel.add(mapNameField);
        optionsPanel.add(saveButton);
        optionsPanel.add(loadButton);
        optionsPanel.add(sizeXField);
        optionsPanel.add(sizeYField);
        optionsPanel.add(newMapButton);
        optionsPanel.add(Box.createVerticalGlue());

        add(choicePanel, BorderLayout.WEST);
        add(mc, BorderLayout.CENTER);
        add(optionsPanel, BorderLayout.EAST);

        setVisible(true);

        setSelected(0);
    }



    private void saveMap(Map map) {
        String filename = "maps/" + mapNameField.getText();
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

    private void loadMap(String filename) {
        System.out.println("Loading map: " + filename);
        Map m;
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fis);
            m = (Map)in.readObject();
            in.close();
            fis.close();
        } catch (IOException ioe) {
            System.out.println("IOException in BuilderFrame->loadMap");
            m = new Map();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException in BuilderFrame->loadMap");
            m = new Map();
        }

        mc.setMap(m);
        revalidate();
        repaint();
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
