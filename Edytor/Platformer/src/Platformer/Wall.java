package Platformer;

import java.awt.*;

public class Wall {
    int x;
    int y;
    int width;
    int height;
    Image g;
    int id;

    Rectangle hitBox;

    public Wall(int x,int y,Image i,int N)
    {
        g=i;
        id=N;
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
    public int getId(){return id;}


}