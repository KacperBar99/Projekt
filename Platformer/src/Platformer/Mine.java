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


    public Mine(int x,int y)
    {
        follow=false;
        horizontal=true;
        increase=true;
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;
        tmp=Color.red;
        remove=false;


        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.setColor(tmp);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(tmp);
        gtd.fillRect(x+1,y+1,width-2,height-2);
    }
    public void set(Player P)
    {
        if(follow)
        {
            if(P.x>x)x++;
            else x--;
            if(P.y>y)y++;
            else y--;
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
            tmp=Color.MAGENTA;
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
        hitBox.x=x;
        hitBox.y=y;
    }


}
