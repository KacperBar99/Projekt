package Platformer;

import java.awt.*;

public class Letter {
    int x;
    int y;
    int width;
    int height;
    Image g;



    public Letter(int x,int y,int w,int h,Image I)
    {
        this.x=x;
        this.y=y;
        this.width=w;
        this.height=h;
        g=I;
    }
    public void draw(Graphics2D gtd){
            gtd.drawImage(g, x, y, null);




    }
}