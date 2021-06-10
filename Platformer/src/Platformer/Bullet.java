package Platformer;

import java.awt.*;

public class Bullet {
    int x;
    int y;
    int width;
    int height;
    Rectangle hitBox;
    boolean remove;
    int Px;
    int Py;
    int count;
    boolean xincrease;
    boolean yincrease;
    int xspeed;
    int yspeed;
    Image g;
    int direction;


    public Bullet(int x,int y,Image I,int D)
    {
        direction=D;
        g=I;
        xspeed=10;
        yspeed=10;
        this.x=x;
        this.y=y;
        this.width=9;
        this.height=9;
        remove=false;
        count=0;


        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.drawImage(g, x, y, null);
        /*
        gtd.setColor(Color.pink);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(Color.pink);
        gtd.fillRect(x+1,y+1,width-2,height-2);
         */
    }
    public void set()
    {
            count++;
            if(count==100)remove=true;

            switch (direction)
            {
                case 0:
                    x-=xspeed;
                    break;
                case 1:
                    x+=xspeed;
                    break;
                case 2:
                    y+=yspeed;
                    break;
                case 3:
                    y-=yspeed;
                    break;
            }

        hitBox.x=x;
        hitBox.y=y;
    }
    public void check(GamePanel panel)
    {

        hitBox = new Rectangle(x,y,width+4,height+4);

        for(Wall wall:panel.walls)
        {
            if(hitBox.intersects(wall.hitBox))
            {
                remove=true;
            }
        }
        for(Gravity_Changer gravity_changer:panel.changers)
        {
            if(hitBox.intersects(gravity_changer.hitBox)) {
                remove=true;
            }
        }
        for(WallB wallB:panel.wallsB)
        {
            if(hitBox.intersects(wallB.hitBox)) {
                remove=true;
            }
        }
        for(Wall_Jump Wjump:panel.jumps)
        {
            if(hitBox.intersects(Wjump.hitBox)) {
                remove=true;
            }
        }
        for(Spike spike:panel.spikes)
        {

            if (hitBox.intersects(spike.hitBox)) {
                remove=true;
            }

        }
        for(Mine mine:panel.mines)
        {
            if(hitBox.intersects(mine.hitBox)) {
                remove=true;
            }
        }
        for(Bullet bullet:panel.bullets)
        {
            if(this!=bullet && hitBox.intersects(bullet.hitBox))
            {
                remove=true;
            }
        }
        /*
        for(Turret turret:panel.turrets)
        {
            if(hitBox.intersects(turret.hitBox))
            {
                remove=true;
            }
        }*/
        hitBox=new Rectangle(x,y,width,height);
    }


}
