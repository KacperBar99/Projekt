package Platformer;

import java.awt.*;

public class Turret {
    int x;
    int y;
    int width;
    int height;
    int count;

    Rectangle hitBox;

    public Turret(int x,int y)
    {
        count=0;
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;

        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.setColor(Color.magenta);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(Color.magenta);
        gtd.fillRect(x+1,y+1,width-2,height-2);
    }
    public int getX(){return x;}
    public int getY(){return y;}

}