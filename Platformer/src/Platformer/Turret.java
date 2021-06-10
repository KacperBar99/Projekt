package Platformer;

import java.awt.*;

public class Turret {
    int x;
    int y;
    int width;
    int height;
    int count;
    int direction;
    Image g;
    Image b;

    Rectangle hitBox;

    public Turret(int x,int y,Image I1,Image I2,int D)
    {
        direction=D;
        count=0;
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;
        g=I1;
        b=I2;
        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.drawImage(g, x, y, null);
        /*
        gtd.setColor(Color.magenta);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(Color.magenta);
        gtd.fillRect(x+1,y+1,width-2,height-2);

         */
    }
    public void set(GamePanel panel)
    {
        if(count==50)
        {
            panel.Sound_Play("files/sounds/shoot.wav");
            Bullet tmp=new Bullet(x+32,y+32,panel.player.x,panel.player.y,b);
            panel.bullets.add(tmp);
            count=0;
        }
        else
        {
            count++;
        }

    }

}
