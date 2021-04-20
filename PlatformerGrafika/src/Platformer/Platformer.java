package Platformer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Platformer {
    public static  void main (String[] args)
    {
        MainFrame frame = new MainFrame();
        frame.setSize(1920,1080);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int)(screenSize.getWidth()/2-frame.getSize().getWidth()/2), (int)(screenSize.getHeight()/2-frame.getSize().getHeight()/2));

        frame.setResizable(true);
        frame.setTitle("Platformówka");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}