package Platformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainFrame extends javax.swing.JFrame {
    public MainFrame()
    {
        GamePanel panel = new GamePanel();
        panel.setLocation(0,0);
        panel.setSize(this.getSize());
        panel.setBackground(Color.DARK_GRAY);
        panel.setVisible(true);
        this.add(panel);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addKeyListener(new KeyCheck(panel));
    }
}