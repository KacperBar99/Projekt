package Platformer;

import java.awt.*;

public class Gravity_Changer {
    int x;
    int y;
    int width;
    int height;
    Image g;
    Rectangle hitBox;

    public Gravity_Changer(int x,int y)
    {
        this.x=x;
        this.y=y;
        g=null;
        width=50;
        height=50;
        hitBox = new Rectangle(x,y,50,50);
    }
    public void draw(Graphics2D gtd){
        Toolkit t=Toolkit.getDefaultToolkit();
        g=t.getImage("files/gravity.png");
        gtd.drawImage(g, x, y, null);
        //gtd.setColor(Color.BLUE);
        //gtd.fillRect(x,y,width,height);
    }
}