package Platformer;

import java.awt.*;

public class MainFrame extends javax.swing.JFrame {
    public MainFrame()
    {
        GamePanel panel = new GamePanel();
        panel.setLocation(0,0);
        panel.setSize(this.getSize());
        panel.setBackground(Color.DARK_GRAY);
        panel.setVisible(true);
        this.add(panel);

        addKeyListener(new KeyCheck(panel));
    }




}