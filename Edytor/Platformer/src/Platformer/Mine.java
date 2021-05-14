package Platformer;

import java.awt.*;

public class Mine {
    int x;
    int y;
    int width;
    int height;
    boolean horizontal;
    boolean increase;
    Image g;

    Rectangle hitBox;

    public Mine(int x,int y,Image i)
    {
        Toolkit t=Toolkit.getDefaultToolkit();
        g=i;
        horizontal=true;
        increase=true;
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;

        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.drawImage(g, x, y, null);
        /*
        gtd.setColor(Color.RED);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(Color.red);
        gtd.fillRect(x+1,y+1,width-2,height-2);

         */
    }

    public int getX(){return x;}
    public int getY(){return y;}


}