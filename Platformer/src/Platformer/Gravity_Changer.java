package Platformer;

import java.awt.*;

public class Gravity_Changer {
    int x;
    int y;
    int width;
    int height;
    Rectangle hitBox;

    public Gravity_Changer(int x,int y)
    {
        this.x=x;
        this.y=y;
        width=70;
        height=50;
        hitBox = new Rectangle(x,y,50,50);
    }
    public void draw(Graphics2D gtd){
        gtd.setColor(Color.BLUE);
        gtd.fillRect(x,y,width,height);
    }
}