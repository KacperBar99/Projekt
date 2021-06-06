package Platformer;

import java.awt.*;

public class Background {
    int x;
    int y;
    int width;
    int height;
    Image g;
    boolean special;


    public Background(int x,int y,Image I)
    {
        special=false;
        this.x=x;
        this.y=y;
        this.width=1920;
        this.height=1080;
        g=I;
    }

    public void draw(Graphics2D gtd){
        gtd.drawImage(g, x, y, null);
    }
    public void draw_C(Graphics2D gtd)
    {
        gtd.setColor(Color.darkGray);
        gtd.drawRect(x,y,width,height);
        if(special)gtd.setColor(Color.yellow);
        else gtd.setColor(Color.GRAY);
        gtd.fillRect(x+1,y+1,width-2,height-2);
    }
}