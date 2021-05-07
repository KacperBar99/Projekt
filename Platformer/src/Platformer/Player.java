package Platformer;

import java.awt.*;


public class Player {

    class New_level
    {
        boolean left;
        boolean right;
        boolean up;
        boolean down;
        New_level()
        {
            down=false;
            up=false;
            right=false;
            left=false;
        }
        boolean is_true()
        {
            if(up || down || left || right)
                return true;
            else return false;
        }
        int changex(int x)
        {
            if(right)return 1;
            else if(left)return -1;
            else return 0;
        }
        int changey(int y)
        {
            if(up)return 1;
            else if(down)return -1;
            else return 0;
        }

    }
    public enum Images
    {


        NORMAL_RIGHT(0),
        NORMAL_LEFT(1),
        INVERTED_RIGHT(3),
        INVERTED_LEFT(2),
        DARK_RIGHT(4),
        DARK_LEFT(5);

        private int Id;
        private Images(int I)
        {
            Id=I;
        }
        private int give_Id()
        {
            return Id;
        }
    }

    boolean hitBox_type;
    GamePanel panel;
    int counter;
    boolean dash;
    boolean gravity;
    int x;
    int y;
    int width;
    int height;
    New_level where;
    boolean looking_left;
    Image i[];
    boolean debug1;
    int jump;
    boolean jumped;
    boolean test;

    double xspeed;
    double yspeed;

    Rectangle hitBox;

    boolean keyLeft;
    boolean keyRight;
    boolean keyUP;
    boolean keyDown;


    public Player(int x, int y, GamePanel panel)
    {
        test=false;
        jumped=false;
        jump=1;
        debug1=true;
        looking_left=false;
        hitBox_type=true;
        counter=0;
        this.panel=panel;
        this.x=x;
        this.y=y;
        dash=false;
        gravity=true;
        where = new New_level();
        i = new Image[6];
        Toolkit t=Toolkit.getDefaultToolkit();
        i[Images.NORMAL_RIGHT.give_Id()]=t.getImage("files/yusmini.gif");
        i[Images.NORMAL_LEFT.give_Id()]=t.getImage("files/yusminiflipped.gif");
        i[Images.INVERTED_LEFT.give_Id()]=t.getImage("files/yusminiinvertflipped.gif");
        i[Images.INVERTED_RIGHT.give_Id()]=t.getImage("files/yusminiinvert.gif");
        i[Images.DARK_RIGHT.give_Id()]=t.getImage("files/yusdark.gif");
        i[Images.DARK_LEFT.give_Id()]=t.getImage("files/yusdarkflipped.gif");

        width=64;
        height=64;
        hitBox = new Rectangle(x,y,width,height);

    }
    public void jump()
    {
        test=true;
        jump--;
        if(gravity)yspeed=-12;
        else yspeed=12;
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
        //gravitation acceleration
        if(gravity) yspeed+=.6;
        else yspeed-=.6;


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
                    if(wall.hitBox.intersects(hitBox))
                    {
                        jumped=true;
                        if(gravity)yspeed=-12;
                        else yspeed=12;
                    }
                //double jump?

                if(!jumped && jump>0) {
                    jump();
                }
            }
            else
            {
                for(WallB wallB:panel.wallsB)
                    if(wallB.hitBox.intersects(hitBox))
                    {
                        jumped=true;
                        if(gravity)yspeed-=12;
                        else yspeed=12;
                    }
                if(!jumped && jump>0) {
                    jump();
                }
            }

            if(gravity)hitBox.y-=2;
            else hitBox.y+=2;
        }



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
                    test=false;
                    jump=1;
                    jumped=false;
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
                    test=false;
                    jump=1;
                    jumped=false;
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
                gravity=!gravity;
                jump=1;
                //set_double_jump();

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


        //Testing for wall jump
        for(Wall_Jump Wjump:panel.jumps)
        {
            if(looking_left)hitBox.x-=2;
            else hitBox.x+=2;
            if(hitBox.intersects(Wjump.hitBox))
            {
                test=false;
                yspeed=0;
                xspeed=0;
                jump=1;
                if(keyUP)
                {
                    if(looking_left) xspeed=30;
                    else xspeed=-30;
                    if(gravity)yspeed=-10;
                    else yspeed=10;
                }
            }
            if(looking_left)hitBox.x+=2;
            else hitBox.x-=2;
        }
        //Collision tests for spikes
        for(Spike spike:panel.spikes)
        {
            if(hitBox.intersects(spike.hitBox))
            {
                x=100;
                y=100;
                yspeed=0;
                xspeed=0;
            }
        }
        //Collision tests for mines
        for(Mine mine:panel.mines)
        {
            if(hitBox.intersects(mine.hitBox))
            {
                x=100;
                y=100;
                yspeed=0;
                xspeed=0;
            }
        }
        for(Bullet bullet:panel.bullets)
        {
            if(hitBox.intersects(bullet.hitBox))
            {
                x=100;
                y=100;
                yspeed=0;
                xspeed=0;
            }
        }
        for(Turret turret:panel.turrets)
        {
            if(hitBox.intersects(turret.hitBox))
            {
                x=100;
                y=100;
                yspeed=0;
                xspeed=0;
            }
        }



        //Changes applied here

        x+=xspeed;
        y+=yspeed;
        hitBox.x=x;
        hitBox.y=y;


        if(x<0)where.left=true;
        else if(x>1920)where.right=true;
        else if(y<0)where.up=true;
        else if(y>1080)where.down=true;
    }
    public void change_HitBox_type()
    {
        hitBox_type=!hitBox_type;
    }
    public void draw(Graphics2D gtd)
    {
        Images tmp=Images.NORMAL_RIGHT;
        if(gravity)
        {
            if(looking_left)
            {
                tmp=Images.NORMAL_LEFT;
            }
        }
        else
        {
            if(looking_left)tmp=Images.INVERTED_LEFT;
            else tmp=Images.INVERTED_RIGHT;
        }
        if(!hitBox_type)
        {
            if(looking_left)tmp=Images.DARK_LEFT;
            else tmp=Images.DARK_RIGHT;
        }

        if(debug1) gtd.drawImage(i[tmp.give_Id()], x, y, null);
        else
        {
            Color C=Color.gray;
            if(test)C=Color.ORANGE;

            gtd.setColor(C);
            gtd.drawRect(x,y,width,height);
            gtd.setColor(C);
            gtd.fillRect(x+1,y+1,width-2,height-2);
        }
    }


}