package Platformer;

import java.awt.*;

public class Wall {
    int x;
    int y;
    int width;
    int height;
    Image g;

    Rectangle hitBox;


    public Wall(int x,int y,Image I)
    {
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;
        g=I;

        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.drawImage(g, x, y, null);
        /*
        gtd.setColor(Color.white);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(Color.white);
        gtd.fillRect(x+1,y+1,width-2,height-2);
        */
    }
}