package Platformer;

import java.awt.*;

public class Player_spawn {
    int x;
    int y;
    int N;
    int width;
    int height;

    Rectangle hitBox;

    public Player_spawn(int x,int y,int number)
    {
        N=number;
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;

        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.setColor(Color.gray);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(Color.gray);
        gtd.fillRect(x+1,y+1,width-2,height-2);
    }

    public int getX(){return x;}
    public int getY(){return y;}
    public  int getN(){return N;}
}