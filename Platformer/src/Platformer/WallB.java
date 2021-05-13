package Platformer;

import java.awt.*;

public class WallB {

    int x;
    int y;
    int width;
    int height;

    Rectangle hitBox;


    public WallB(int x,int y)
    {
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;

        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.setColor(Color.BLACK);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(Color.black);
        gtd.fillRect(x+1,y+1,width-2,height-2);
    }


}