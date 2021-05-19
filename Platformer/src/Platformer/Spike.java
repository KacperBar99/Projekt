package Platformer;

import java.awt.*;

public class Spike {
    int x;
    int y;
    int width;
    int height;
    int count;
    Image g;

    Rectangle hitBox;

    public Spike(int x,int y,Image I)
    {
        g=I;
        count=0;
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;

        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.drawImage(g, x, y, null);

    }

}
