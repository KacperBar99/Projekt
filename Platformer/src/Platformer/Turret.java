package Platformer;

import java.awt.*;

public class Turret {
    int x;
    int y;
    int width;
    int height;
    int count;

    Rectangle hitBox;

    public Turret(int x,int y)
    {
        count=0;
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;

        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.setColor(Color.magenta);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(Color.magenta);
        gtd.fillRect(x+1,y+1,width-2,height-2);
    }
    public void set(GamePanel panel)
    {
        if(count==50)
        {
            Bullet tmp=new Bullet(x,y+90,panel.player.x,panel.player.y);
            panel.bullets.add(tmp);
            count=0;
        }
        else
        {
            count++;
        }

    }

}
