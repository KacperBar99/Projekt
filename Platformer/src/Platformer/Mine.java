package Platformer;

import java.awt.*;

public class Mine {
    int x;
    int y;
    int width;
    int height;
    boolean horizontal;
    boolean increase;
    boolean follow;
    Rectangle hitBox;
    Color tmp;
    boolean remove;
    int Px;
    int Py;
    int count;
    boolean xincrease;
    boolean yincrease;
    int xspeed;
    int yspeed;


    public Mine(int x,int y,Player P)
    {
        xspeed=1;
        yspeed=10;
        follow=false;
        horizontal=true;
        increase=true;
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;
        tmp=Color.red;
        remove=false;
        Px=P.x;
        Py=P.y;
        count=0;
        if(x<P.x)xincrease=true;
        else xincrease= false;
        if(y<P.y)yincrease=true;
        else yincrease=false;


        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.setColor(tmp);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(tmp);
        gtd.fillRect(x+1,y+1,width-2,height-2);
    }
    public void set()
    {
        if(follow)
        {
            count++;
            if(count==100)remove=true;
           if(xincrease)x+=xspeed;
           else x-=xspeed;
           if(yincrease)y+=yspeed;
           else y-=yspeed;
           /* if(Px>x)x+=1;
            else x-=1;*/
            /*
            if(Py>y)y+=5;
            else y-=5;*/
        }
        else
        {
            if(horizontal)
            {
                if(increase)x++;
                else x--;
                if(x<0 || x>1920)increase=!increase;
            }
            else
            {
                if(increase)y++;
                else y--;
                if(y<0 || y>1080)increase=!increase;
            }
        }
        hitBox.x=x;
        hitBox.y=y;

    }
    public void check(GamePanel panel)
    {


        if(follow)
        {
            hitBox = new Rectangle(x,y,width+4,height+4);
            tmp=Color.MAGENTA;
        }

        else tmp=Color.red;

        for(Wall wall:panel.walls)
        {
            if(hitBox.intersects(wall.hitBox))
            {
                if(follow)remove=true;
                else increase=!increase;
            }
        }
        for(Gravity_Changer gravity_changer:panel.changers)
        {
            if(hitBox.intersects(gravity_changer.hitBox)){
                if(follow)remove=true;
                else increase=!increase;
            }
        }
        for(WallB wallB:panel.wallsB)
        {
            if(hitBox.intersects(wallB.hitBox)){
                if(follow)remove=true;
                else increase=!increase;
            }
        }
        for(Wall_Jump Wjump:panel.jumps)
        {
            if(hitBox.intersects(Wjump.hitBox)){
                if(follow)remove=true;
                else increase=!increase;
            }
        }
        for(Spike spike:panel.spikes)
        {

            if (hitBox.intersects(spike.hitBox)){
                if(follow)remove=true;
                else increase=!increase;
            }


        }
        for(Mine mine:panel.mines)
        {
            if(mine!=this && hitBox.intersects(mine.hitBox)){

                if(follow)remove=true;
                else increase=!increase;
            }
        }
        hitBox=new Rectangle(x,y,width,height);
    }


}
