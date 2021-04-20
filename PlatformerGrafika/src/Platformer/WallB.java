package Platformer;

import java.awt.*;

public class WallB {

    int x;
    int y;
    int width;
    int height;

    Rectangle hitBox;


    public WallB(int x,int y, int width, int height)
    {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;

        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.setColor(Color.BLACK);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(Color.black);
        gtd.fillRect(x+1,y+1,width-2,height-2);
    }


}