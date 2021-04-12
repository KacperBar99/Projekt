package Platformer;

import java.awt.*;

public class Wall {
    int x;
    int y;
    int width;
    int height;

    Rectangle hitBox;


    public Wall(int x,int y, int width, int height)
    {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;

        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.setColor(Color.black);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(Color.white);
        gtd.fillRect(x+1,y+1,width-2,height-2);
    }
    public int getID(){return 0;}
    public int getX(){return x;}
    public int getY(){return y;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
}