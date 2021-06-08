package Platformer;

import javax.swing.*;
import java.awt.*;

public class Spawn {
    int x;
    int y;
    int index;
    Toolkit t=Toolkit.getDefaultToolkit();
    Image g=t.getImage("files/Tiles/background/1.png");

    Spawn(int _x,int _y,int i)
    {
        x=_x;
        y=_y;
        index=i;
    }
    int getX(){return x;}
    int getY(){return y;}
    int getIndex(){return index;}

    public void draw(Graphics2D gtd){
        gtd.drawImage(g, x, y, null);
    }
}
