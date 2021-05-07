package Platformer;

import java.awt.*;

public class Gravity_Changer {
    int x;
    int y;
    int width;
    int height;
    Rectangle hitBox;
    Image g;

    public Gravity_Changer(int x,int y,Image i)
    {
        Toolkit t=Toolkit.getDefaultToolkit();
        g=i;
        this.x=x;
        this.y=y;
        width=64;
        height=64;
        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.drawImage(g, x, y, null);
        /*
        gtd.setColor(Color.BLUE);
        gtd.fillRect(x,y,width,height);

         */
    }
    public int getX(){return x;}
    public int getY(){return y;}
}