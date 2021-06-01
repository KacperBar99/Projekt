package Platformer;

import java.awt.*;

public class Win_block {
    int x;
    int y;
    int width;
    int height;
    Image g;

    Rectangle hitBox;
    public Win_block(int x,int y,Image I)
    {
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;
        g=I;
        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.drawImage(g, x, y, null);

    }
}
