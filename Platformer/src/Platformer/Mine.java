package Platformer;

import java.awt.*;

public class Mine {
    int x;
    int y;
    int width;
    int height;
    boolean horizontal;
    boolean increase;
    Rectangle hitBox;
    Image g;


    public Mine(int x,int y,Image I)
    {
        g=I;
        horizontal=true;
        increase=true;
        this.x=x;
        this.y=y;
        this.width=64;
        this.height=64;


        hitBox = new Rectangle(x,y,width,height);
    }
    public void draw(Graphics2D gtd){
        gtd.drawImage(g, x, y, null);
        /*
        gtd.setColor(Color.red);
        gtd.drawRect(x,y,width,height);
        gtd.setColor(Color.red);
        gtd.fillRect(x+1,y+1,width-2,height-2);

         */
    }
    public void set()
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
        hitBox.x=x;
        hitBox.y=y;

    }
    public void check(GamePanel panel)
    {
        for(Wall wall:panel.walls)
        {
            if(hitBox.intersects(wall.hitBox))
            {
                increase=!increase;
            }
        }
        for(Gravity_Changer gravity_changer:panel.changers)
        {
            if(hitBox.intersects(gravity_changer.hitBox)){
                increase=!increase;
            }
        }
        for(WallB wallB:panel.wallsB)
        {
            if(hitBox.intersects(wallB.hitBox)){
                increase=!increase;
            }
        }
        for(Wall_Jump Wjump:panel.jumps)
        {
            if(hitBox.intersects(Wjump.hitBox)){
                increase=!increase;
            }
        }
        for(Spike spike:panel.spikes)
        {

            if (hitBox.intersects(spike.hitBox)){
                increase=!increase;
            }


        }
        for(Mine mine:panel.mines)
        {
            if(mine!=this && hitBox.intersects(mine.hitBox)){

                increase=!increase;
            }
        }
        for(Turret turret:panel.turrets)
        {
            if(hitBox.intersects(turret.hitBox))
            {
                increase=!increase;
            }
        }
        hitBox=new Rectangle(x,y,width,height);
    }


}
