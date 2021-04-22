package Platformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Platformer {

    public static void main (String[] args)
    {
        MainFrame frame = new MainFrame();
        frame.setSize(1920,1080);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int)(screenSize.getWidth()/2-frame.getSize().getWidth()/2), (int)(screenSize.getHeight()/2-frame.getSize().getHeight()/2));

        frame.setResizable(false);
        frame.setTitle("Platformer");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}