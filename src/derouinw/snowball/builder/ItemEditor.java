package derouinw.snowball.builder;

import derouinw.snowball.client.Item.Item;
import derouinw.snowball.client.SBClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

/**
 * Frame to edit items
 */
public class ItemEditor extends JFrame {
    private JLabel spriteLabel;
    private ImageIcon sprite;
    private JTextField nameField;
    private JButton addButton;

    public ItemEditor() {
        super("Item editor");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(300,200);

        nameField = new JTextField(20);

        nameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                setItemName(nameField.getText());
            }
        });
        sprite = new ImageIcon();
        spriteLabel = new JLabel("", sprite, JLabel.CENTER);
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveItem(nameField.getText());
            }
        });

        setLayout(new FlowLayout());
        add(spriteLabel);
        add(nameField);
        add(addButton);

        setItemName("");
    }

    private void saveItem(String name) {
        Item newItem = new Item(name);

        String filename = Item.ITEMS_DIR + name;
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(newItem);
            out.flush();
            fos.close();
        } catch (IOException ioe) {
            System.out.println("IOException in ItemEditor->saveItem");
        }

        setItemName("");
    }

    private void setItemName(String name) {
        nameField.setText(name);
        updateImage(name);
    }

    private void updateImage(String name) {
        if (new File(SBClient.IMAGES_DIR + name + ".png").isFile()) {
            sprite = new ImageIcon(SBClient.IMAGES_DIR + name + ".png");
        } else {
            sprite = new ImageIcon(SBClient.IMAGES_DIR + "blankItem.png");
        }

        remove(spriteLabel);
        spriteLabel = new JLabel("", sprite, JLabel.CENTER);
        add(spriteLabel, 0);
        revalidate();
        repaint();
    }
}
