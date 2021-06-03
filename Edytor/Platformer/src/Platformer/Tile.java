package Platformer;

import java.awt.*;

public class Tile {
    int x;
    int y;
    int width;
    int height;
    Image g;
    int id;

    public Tile(int x,int y,Image I,int i)
    {
        id=i;
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;
        g=I;
    }
    public void draw(Graphics2D gtd){
        gtd.drawImage(g, x, y, null);
    }
    public int getX(){return x;}
    public int getY(){return y;}
    public int geti(){return id;}

}