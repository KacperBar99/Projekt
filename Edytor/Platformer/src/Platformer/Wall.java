package Platformer;

import java.awt.*;

public class Wall {
    int x;
    int y;
    int width;
    int height;

    Rectangle hitBox;


    public Wall(int x,int y)
    {
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;

        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.setColor(Color.black);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(Color.white);
        gtd.fillRect(x+1,y+1,width-2,height-2);
    }

    public int getX(){return x;}
    public int getY(){return y;}
}