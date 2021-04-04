package Platformer;

import java.awt.*;

public class KillBox {

    int x;
    int y;
    int width;
    int height;

    Rectangle hitBox;


    public KillBox(int x,int y, int width, int height)
    {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;

        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.setColor(Color.RED);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(Color.RED);
        gtd.fillRect(x+1,y+1,width-2,height-2);
    }


}