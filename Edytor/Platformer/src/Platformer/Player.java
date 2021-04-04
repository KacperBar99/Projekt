package Platformer;

import java.awt.*;
import java.sql.SQLOutput;

public class Player {

    int Id_max;
    int Id;
    boolean hitBox_type;
    GamePanel panel;
    int counter;
    boolean dash;
    boolean gravity;
    int x;
    int y;
    int width;
    int height;

    double xspeed;
    double yspeed;

    Rectangle hitBox;

    boolean keyLeft;
    boolean keyRight;
    boolean keyUP;
    boolean keyDown;


    public Player(int x, int y, GamePanel panel)
    {
        Id=-1;
        Id_max=4;
        hitBox_type=true;
        counter=0;
        this.panel=panel;
        this.x=x;
        this.y=y;
        dash=false;
        gravity=true;

        width=49;
        height=49;
        hitBox = new Rectangle(x,y,width,height);

    }
    public void dash()
    {
        panel.walls.add(new Wall(x,y,width,height));
        if(dash)
        {
            counter=0;
            dash=false;
        }
        else
        {
            counter=0;
            dash=true;
        }
    }
    public void change_Gravity()
    {
        gravity=!gravity;
    }
    public void change_Id(boolean up)
    {
        if(up)
        {
            if(Id==Id_max)Id=0;
            else Id++;
        }
        else
        {
            if(Id==0)Id=Id_max;
            else Id--;
        }
    }
    public void set()
    {
        if(!keyLeft && !keyRight)xspeed=0;
        else if(keyRight) xspeed++;
        else xspeed--;


        if(xspeed > 0 && xspeed < 0.75)xspeed=0;
        if(xspeed < 0 && xspeed >-0.75)xspeed=0;

        if(dash)
        {
            counter++;
            if(xspeed>0)xspeed=30;
            else if(xspeed<0)xspeed=-30;
            if(counter>=10) dash=false;
        }
        else
        {
            if(xspeed>7)xspeed=7;
            if(xspeed<-7)xspeed=-7;
        }


        //jumping
        if(keyUP)
        {
            if(gravity) hitBox.y+=2;
            else hitBox.y-=2;

            if(hitBox_type)
            {
                for(Wall wall: panel.walls)
                    if(wall.hitBox.intersects(hitBox) )
                    {
                        if(gravity)yspeed=-6;
                        else yspeed=6;
                    }
            }
            else
            {
                for(WallB wallB:panel.wallsB)
                    if(wallB.hitBox.intersects(hitBox))
                    {
                        if(gravity)yspeed-=6;
                        else yspeed=6;
                    }
            }
            if(gravity)hitBox.y-=2;
            else hitBox.y+=2;
        }
        if(gravity) yspeed+=0.3;
        else yspeed-=0.3;
        //horizontal movement


        hitBox.x+=xspeed;
        if(hitBox_type)
        {
            for(Wall wall: panel.walls)
            {
                if(hitBox.intersects(wall.hitBox))
                {
                    hitBox.x-=xspeed;
                    while(!wall.hitBox.intersects(hitBox))
                    {
                        hitBox.x += Math.signum(xspeed);
                    }
                    hitBox.x-=Math.signum(xspeed);
                    xspeed = 0;
                    x = hitBox.x;
                }
            }
        }
        else
        {
            for(WallB wallB:panel.wallsB)
            {
                if(hitBox.intersects(wallB.hitBox))
                {
                    hitBox.x-=xspeed;
                    while(!wallB.hitBox.intersects(hitBox))
                    {
                        hitBox.x+=Math.signum(xspeed);
                    }
                    hitBox.x-=Math.signum(xspeed);
                    xspeed = 0;
                    x = hitBox.x;
                }
            }
        }


        for(Gravity_Changer changer:panel.changers)
        {
            if(hitBox.intersects(changer.hitBox))
            {
                hitBox.x-=xspeed;
                while(!changer.hitBox.intersects(hitBox))
                {
                    hitBox.x+=Math.signum(xspeed);
                }
                hitBox.x-=Math.signum(xspeed);
                xspeed = 0;
                x=hitBox.x;
            }
        }
        for(KillBox box:panel.boxes)
        {

            if(hitBox.intersects(box.hitBox))
            {
                x=400;
                y=300;
                yspeed=0;
                xspeed=0;
            }
        }

        //vert

        hitBox.y+=yspeed;

        if(hitBox_type)
        {
            for(Wall wall: panel.walls)
            {
                if(hitBox.intersects(wall.hitBox))
                {
                    hitBox.y-=yspeed;
                    while(!wall.hitBox.intersects(hitBox))
                    {
                        hitBox.y += Math.signum(yspeed);
                    }
                    hitBox.y-=Math.signum(yspeed);
                    yspeed = 0;
                    y = hitBox.y;
                }
            }
        }
        else
        {
            for(WallB wallB:panel.wallsB)
            {
                if(hitBox.intersects((wallB.hitBox)))
                {
                    hitBox.y-=yspeed;
                    while(!wallB.hitBox.intersects((hitBox)))
                    {
                        hitBox.y += Math.signum(yspeed);
                    }
                    hitBox.y-=Math.signum(yspeed);
                    yspeed = 0;
                    y=hitBox.y;
                }
            }
        }


        for(Gravity_Changer changer:panel.changers)
        {
            if(hitBox.intersects(changer.hitBox))
            {
                if(gravity)gravity=false;
                else gravity=true;

                hitBox.y-=yspeed;
                while(!changer.hitBox.intersects(hitBox))
                {
                    hitBox.y += Math.signum(yspeed);
                }
                hitBox.y-=Math.signum(yspeed);
                yspeed=0;
                y = hitBox.y;
            }
        }

        x+=xspeed;
        y+=yspeed;

        hitBox.x=x;
        hitBox.y=y;
        if(x<0 || x>1920)System.exit(0);
        if(y<0 || y>1080)System.exit(0);
    }
    public void change_HitBox_type()
    {
        hitBox_type=!hitBox_type;
    }
    public void draw(Graphics2D gtd)
    {
        change_size();
        if(hitBox_type) gtd.setColor(Color.white);
        else gtd.setColor(Color.black);
        gtd.fillRect(x,y,width,height);
    }
    public void change_size()
    {
        switch (Id)
        {
            case 0:
                width=10;
                height=10;
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 1:
                width=20;
                height=20;
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 2:
                width=30;
                height=30;
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 3:
                width=40;
                height=40;
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 4:
                width=50;
                height=50;
                hitBox=new Rectangle(x,y,width,height);
                break;
            default:
                width=1;
                height=1;
                hitBox=new Rectangle(x,y,width,height);
        }
    }
}