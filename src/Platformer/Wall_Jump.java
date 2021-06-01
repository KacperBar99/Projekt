package Platformer;

import java.awt.*;

public class Wall_Jump {
    int x;
    int y;
    int width;
    int height;
    Image g;
    Rectangle hitBox;


    public Wall_Jump(int x,int y,Image I)
    {
        g=I;
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;

        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd) {
        gtd.drawImage(g, x, y, null);
    }

}
