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

    int x;
    int y;
    int width;
    int height;
    New_level where;
    boolean looking_left;
    Image i[];
    boolean debug1; //przelacza na widok klocka

    boolean test;

    //poruszanie
    boolean keyLeft;
    boolean keyRight;
    boolean keyUP;
    boolean keyDown;

    double xspeed;
    double yspeed;
    static int maxspeed;
    static double frc; //tarcie

    boolean gravity_switch;
    static double grav;

    boolean dash;

    boolean can_jump;
    boolean djump;
    int jumpforce;

    Rectangle hitBox;

    public Player(int x, int y, GamePanel panel)
    {
        test=false;
        debug1=true;
        looking_left=false;
        hitBox_type=true;
        counter=0;
        this.panel=panel;
        this.x=x;
        this.y=y;
        dash=false;
        gravity_switch=true;
        maxspeed=12;
        frc=1.3;
        grav=1.6;
        jumpforce=20;
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
        height=96;
        hitBox = new Rectangle(x,y,width,height);

    }

    public void jump()
    {
        test=true;
        can_jump=false;
        if(!gravity_switch)yspeed=jumpforce;
        else yspeed=-jumpforce;
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
        gravity_switch=!gravity_switch;
    }

    public void set()
    {


        //gravitation acceleration
        if(gravity_switch) yspeed+=grav;
        else yspeed-=grav;

        //horizontal movement of a character
        if(keyRight && xspeed<maxspeed)
        {
            xspeed=7;
            looking_left=false;
        }

        if(keyLeft && xspeed>-maxspeed)
        {
            xspeed=-7;
            looking_left=true;
        }

        if (xspeed>0 && !keyRight) xspeed-=frc;
        if (xspeed<0 && !keyLeft) xspeed+=frc;

        //counter makes sure that dash is only applied for a brief moment
        if(dash)
        {
            counter++;
            if(!looking_left) xspeed=30;
            else xspeed=-30;
            if(counter>=10) dash=false;
        }
        else
        {
            if(xspeed>maxspeed) xspeed=maxspeed;
            if(xspeed<-maxspeed) xspeed=-maxspeed;
        }

        if(keyUP && can_jump) { jump(); }

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
                        hitBox.x+=Math.signum(xspeed);
                    }
                    hitBox.x-=Math.signum(xspeed);
                    xspeed=0;
                    x=hitBox.x;
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
                    xspeed=0;
                    x=hitBox.x;
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
                xspeed=0;
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
                    if (gravity_switch)
                    {
                        if (hitBox.y<=wall.hitBox.y) {
                            test=false;
                            can_jump=true;
                            djump=true;
                        }
                    } else {
                        if (hitBox.y>=wall.hitBox.y) {
                            test=false;
                            can_jump=true;
                            djump=true;
                        }
                    }

                    hitBox.y-=yspeed;
                    while(!wall.hitBox.intersects(hitBox))
                    {
                        hitBox.y+=Math.signum(yspeed);
                    }
                    hitBox.y-=Math.signum(yspeed);
                    yspeed=0;
                    y=hitBox.y;
                }
            }
        }
        else
        {
            for(WallB wallB:panel.wallsB)
            {
                if(hitBox.intersects((wallB.hitBox)))
                {
                    if (gravity_switch)
                    {
                        if (hitBox.y<=wallB.hitBox.y) {
                            test=false;
                            can_jump=true;
                            djump=true;
                        }
                    } else {
                        if (hitBox.y>=wallB.hitBox.y) {
                            test=false;
                            can_jump=true;
                            djump=true;
                        }
                    }

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
                gravity_switch=!gravity_switch;
                can_jump=true;
                djump=true;

                hitBox.y-=yspeed;
                while(!changer.hitBox.intersects(hitBox))
                {
                    hitBox.y += Math.signum(yspeed);
                }
                hitBox.y-=Math.signum(yspeed);
                yspeed=0;
                y=hitBox.y;
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
                can_jump=true;
                djump=true;

                if(keyUP)
                {
                    if(looking_left) xspeed=80;
                    else xspeed=-80;

                    if(gravity_switch) yspeed=-10;
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
        hitBox.x=x;
        hitBox.y=y;
        x+=xspeed;
        y+=yspeed;

        if(x<0)where.left=true;
        else if(x>1920)where.right=true;
        else if(y<0)where.up=true;
        else if(y>1080)where.down=true;

        if(keyLeft && keyRight) xspeed=0;
    }
    public void change_HitBox_type()
    {
        hitBox_type=!hitBox_type;
    }
    public void draw(Graphics2D gtd)
    {
        Images tmp=Images.NORMAL_RIGHT;
        if(gravity_switch)
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