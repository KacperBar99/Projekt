package Platformer;

import java.awt.*;

public class WallB {

    int x;
    int y;
    int width;
    int height;
    Image g;
    int id;
    Rectangle hitBox;


    public WallB(int x,int y,Image i,int ID)
    {
        id=ID;
        g=i;
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;

        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.drawImage(g, x, y, null);
    }
    public int getX(){return x;}
    public int getY(){return y;}
    public int getid(){return id;}

}