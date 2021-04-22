package Platformer;

import java.awt.*;

public class Player {

    boolean hitBox_type;
    GamePanel panel;
    int counter;
    boolean dash;
    boolean gravity;
    int x;
    int y;
    int width;
    int height;
    boolean new_level;
    boolean looking_left;
    Image i[];

    double xspeed;
    double yspeed;

    Rectangle hitBox;

    boolean keyLeft;
    boolean keyRight;
    boolean keyUP;
    boolean keyDown;


    public Player(int x, int y, GamePanel panel)
    {
        looking_left=false;
        hitBox_type=true;
        counter=0;
        this.panel=panel;
        this.x=x;
        this.y=y;
        dash=false;
        gravity=true;
        new_level=false;
        i = new Image[6];
        Toolkit t=Toolkit.getDefaultToolkit();
        i[0]=t.getImage("files/yusmini.gif");
        i[1]=t.getImage("files/yusminiflipped.gif");
        i[2]=t.getImage("files/yusminiinvertflipped.gif");
        i[3]=t.getImage("files/yusminiinvert.gif");
        i[4]=t.getImage("files/yusdark.gif");
        i[5]=t.getImage("files/yusdarkflipped.gif");

        width=64;
        height=64;
        hitBox = new Rectangle(x,y,width,height);

    }
    public void dash()
    {
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
    public void set()
    {
        //horizontal movement of a character
        //registering key inputs
        if(!keyLeft && !keyRight)xspeed=0;
        else if(keyRight)
        {
            looking_left=false;
            xspeed++;
        }
        else
            {
                looking_left=true;
                xspeed--;
            }

        //stopping a character movement if the speed is low
        //this is to avoid sliding
        if(xspeed > 0 && xspeed < 0.75)xspeed=0;
        if(xspeed < 0 && xspeed >-0.75)xspeed=0;


        //speed limit is dependant on dash being on
        //counter makes sure that dash is only applied for a brief moment
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


        //Jumping requires asking surfaces valid for jump if there is a collision
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

        //gravitation acceleration
        if(gravity) yspeed+=0.3;
        else yspeed-=0.3;


        //All the collision tests for the horizontal movement
        //It is necessary to ask all valid block types if hitboxes intersect


        //tests for white and black walls with collision depending on type
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

        //test for blocks changing gravity
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

        //Collision tests for blocks
        //It is a different thing then jumping

        hitBox.y+=yspeed;

        //Test for walls
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

        //Test for blocks changing gravity
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
        for(Wall_Jump Wjump:panel.jumps)
        {
            if(gravity) hitBox.y+=2;
            else hitBox.y-=2;
            if(looking_left)hitBox.x-=2;
            else hitBox.x+=2;




            if(hitBox.intersects(Wjump.hitBox))
            {
                if(looking_left) xspeed=10;
                else xspeed=-10;
                yspeed=-10;
            }
            if(gravity)hitBox.y-=2;
            else hitBox.y+=2;
            if(looking_left)hitBox.x+=2;
            else hitBox.x-=2;
        }


        //Changes applied here
        x+=xspeed;
        y+=yspeed;
        hitBox.x=x;
        hitBox.y=y;
        if(x<0 || x>1920)new_level=true;
        if(y<0 || y>1080)new_level=true;
    }
    public void change_HitBox_type()
    {
        hitBox_type=!hitBox_type;
    }
    public void draw(Graphics2D gtd)
    {
        int tmp=0;
        if(gravity)
        {
            if(looking_left)
            {
                tmp=1;
            }
        }
        else
        {
            if(looking_left)tmp=2;
            else tmp=3;
        }
        if(!hitBox_type)
        {
            if(looking_left)tmp=5;
            else tmp=4;
        }

        gtd.drawImage(i[tmp], x, y, null);
    }
}