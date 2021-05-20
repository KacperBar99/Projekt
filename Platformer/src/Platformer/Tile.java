package Platformer;

import java.awt.*;

public class Tile {
    int x;
    int y;
    int width;
    int height;
    Image g;
    boolean special;


    public Tile(int x,int y,int w,int h,Image I)
    {
        special=false;
        this.x=x;
        this.y=y;
        this.width=w;
        this.height=h;
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
    public void change_special(){special=!special;}
}