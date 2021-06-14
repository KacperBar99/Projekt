package Platformer;

import java.awt.*;

public class Lever {
    int x;
    int y;
    int width;
    int height;
    Image g;
    Rectangle hitBox;

    Lever(int _x,int _y, Image I)
    {
        g=I;
        x=_x;
        y=_y;

        this.width=64;
        this.height=64;

        hitBox = new Rectangle(x,y,width,height);
    }


    public void draw(Graphics2D gtd){
        gtd.drawImage(g, x, y, null);
    }
}

