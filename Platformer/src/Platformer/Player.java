package Platformer;

import java.awt.*;
import java.awt.desktop.AboutEvent;
import java.util.Iterator;


public class Player {

    class New_level
    {
        boolean left;
        boolean right;
        boolean up;
        boolean down;
        boolean start;
        New_level()
        {
            down=false;
            up=false;
            right=false;
            left=false;
        }
        void set_start()
        {
            start=true;
        }
        boolean is_true()
        {
            if(start)
            {
                start=false;
                return true;
            }

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
        void clear()
        {
            start=true;
            down=false;
            up=false;
            right=false;
            left=false;
        }

    }
    public enum Images
    {
        IDLE_LEFT(0),
        IDLE_RIGHT(1),
        RUNNING_RIGHT(2),
        RUNNING_LEFT(3),
        JUMPING_RIGHT(4),
        JUMPING_LEFT(5),
        INVERTED_IDLE_RIGHT(6),
        INVERTED_IDLE_LEFT(7),
        INVERTED_RUNNING_LEFT(8),
        INVERTED_RUNNING_RIGHT(9),
        INVERTED_JUMPING_RIGHT(10),
        INVERTED_JUMPING_LEFT(11),

        DARK_IDLE_RIGHT(12),
        DARK_IDLE_LEFT(13),
        DARK_RUNNING_RIGHT(14),
        DARK_RUNNING_LEFT(15),
        DARK_JUMPING_RIGHT(16),
        DARK_JUMPING_LEFT(17),
        DARK_INVERTED_IDLE_RIGHT(18),
        DARK_INVERTED_IDLE_LEFT(19),
        DARK_INVERTED_RUNNING_LEFT(20),
        DARK_INVERTED_RUNNING_RIGHT(21),
        DARK_INVERTED_JUMPING_RIGHT(22),
        DARK_INVERTED_JUMPING_LEFT(23),

        HOLDING(24);


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

    int Penalty=100;
    int Restart=0;
    boolean hitBox_type;
    GamePanel panel;
    int counter, counter2;

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
    double grav;

    boolean dash;

    boolean can_jump;
    int jumpforce;

    boolean djump;

    boolean can_left;
    boolean can_right;
    boolean holds;

    boolean removed=false;

    Rectangle hitBox;

    public Player(int x, int y, GamePanel panel)
    {
        test=false;
        debug1=true;
        looking_left=false;
        hitBox_type=true;
        counter=0;
        counter2=0;
        this.panel=panel;
        this.x=x;
        this.y=y;
        dash=false;
        gravity_switch=true;
        maxspeed=12;
        frc=1.3;
        grav=1.4;
        jumpforce=18;
        can_left=true;
        can_right=true;
        holds=false;

        where = new New_level();
        i = new Image[25];
        Toolkit t=Toolkit.getDefaultToolkit();

        i[Images.IDLE_RIGHT.give_Id()]=t.getImage("files/character/idle_right.gif");
        i[Images.IDLE_LEFT.give_Id()]=t.getImage("files/character/idle_left.gif");
        i[Images.RUNNING_RIGHT.give_Id()]=t.getImage("files/character/running_right.gif");
        i[Images.RUNNING_LEFT.give_Id()]=t.getImage("files/character/running_left.gif");
        i[Images.JUMPING_RIGHT.give_Id()]=t.getImage("files/character/jumping_right.gif");
        i[Images.JUMPING_LEFT.give_Id()]=t.getImage("files/character/jumping_left.gif");

        i[Images.INVERTED_IDLE_LEFT.give_Id()]=t.getImage("files/character/idle_left_inverted.gif");
        i[Images.INVERTED_IDLE_RIGHT.give_Id()]=t.getImage("files/character/idle_right_inverted.gif");
        i[Images.INVERTED_RUNNING_LEFT.give_Id()]=t.getImage("files/character/running_left_inverted.gif");
        i[Images.INVERTED_RUNNING_RIGHT.give_Id()]=t.getImage("files/character/running_right_inverted.gif");
        i[Images.INVERTED_JUMPING_RIGHT.give_Id()]=t.getImage("files/character/jumping_right_inverted.gif");
        i[Images.INVERTED_JUMPING_LEFT.give_Id()]=t.getImage("files/character/jumping_left_inverted.gif");

        i[Images.DARK_IDLE_RIGHT.give_Id()]=t.getImage("files/character/alt/idle_right.gif");
        i[Images.DARK_IDLE_LEFT.give_Id()]=t.getImage("files/character/alt/idle_right.gif");
        i[Images.DARK_RUNNING_RIGHT.give_Id()]=t.getImage("files/character/alt/running_right.gif");
        i[Images.DARK_RUNNING_LEFT.give_Id()]=t.getImage("files/character/alt/running_left.gif");
        i[Images.DARK_JUMPING_RIGHT.give_Id()]=t.getImage("files/character/alt/jumping_right.gif");
        i[Images.DARK_JUMPING_LEFT.give_Id()]=t.getImage("files/character/alt/jumping_left.gif");

        i[Images.DARK_INVERTED_IDLE_LEFT.give_Id()]=t.getImage("files/character/alt/idle_left_inverted.gif");
        i[Images.DARK_INVERTED_IDLE_RIGHT.give_Id()]=t.getImage("files/character/alt/idle_right_inverted.gif");
        i[Images.DARK_INVERTED_RUNNING_LEFT.give_Id()]=t.getImage("files/character/alt/running_left_inverted.gif");
        i[Images.DARK_INVERTED_RUNNING_RIGHT.give_Id()]=t.getImage("files/character/alt/running_right_inverted.gif");
        i[Images.DARK_INVERTED_JUMPING_RIGHT.give_Id()]=t.getImage("files/character/alt/jumping_right_inverted.gif");
        i[Images.DARK_INVERTED_JUMPING_LEFT.give_Id()]=t.getImage("files/character/alt/jumping_left_inverted.gif");

        i[Images.HOLDING.give_Id()]=t.getImage("files/character/holdin.png");

        width=63;
        height=74;
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
        x+=xspeed;
        y+=yspeed;

        if (!can_left) can_right=true;
        if (!can_right) can_left=true;
        if (counter2>0) counter2--;
        if (counter2<=0) {
            can_right = true;
            can_left = true;
        }

        //gravitation acceleration
        if(gravity_switch) yspeed+=grav;
        else yspeed-=grav;

        //horizontal movement of a character

        if (keyRight && xspeed < maxspeed && can_right) {
            xspeed = 6;
            looking_left = false;
        }

        if (keyLeft && xspeed > -maxspeed && can_left) {
            xspeed = -6;
            looking_left = true;
        }


        if (xspeed>0 && !keyRight) xspeed-=frc;
        if (xspeed<0 && !keyLeft) xspeed+=frc;

        //counter makes sure that dash is only applied for a brief moment
        if(dash)
        {
            counter++;
            if(!looking_left) xspeed=20;
            else xspeed=-20;
            if(counter>=10) dash=false;
        }
        else
        {
            if(xspeed>maxspeed) xspeed=maxspeed;
            if(xspeed<-maxspeed) xspeed=-maxspeed;
        }

        if(keyUP && can_jump) {
            panel.Sound_Play("files/sounds/jump.wav");
            jump();
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
            if (hitBox.x+width>=changer.hitBox.x && hitBox.x<changer.hitBox.x+64) {
                if (gravity_switch && hitBox.y+height==changer.hitBox.y)
                    gravity_switch=false;
                else if (!gravity_switch && hitBox.y==changer.hitBox.y+64)
                    gravity_switch=true;
            }

            if(hitBox.intersects(changer.hitBox))
            {
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
            hitBox.x += Math.signum(xspeed);
            if(hitBox.intersects(Wjump.hitBox))
            {
                //test=false;
                can_jump=false;
                djump=false;
                xspeed=0;
                if (keyRight && !keyUP)
                {
                    yspeed=0;
                    holds=true;
                } else holds=false;

                if(keyUP && !can_jump && (can_left || can_right))
                {
                    counter2=20;

                    xspeed=-10;
                    can_right=false;

                    if(gravity_switch) yspeed=-jumpforce*1.2;
                    else yspeed=jumpforce*1.2;
                }
                /*if (keyUP)
                {
                    yspeed=-4;
                }
                if (keyDown)
                {
                    yspeed=4;
                }*/
            }
            hitBox.x -= Math.signum(xspeed);
        }

        //Collision tests for spikes
        for(Spike spike:panel.spikes)
        {
            if(hitBox.intersects(spike.hitBox))
            {
                panel.Points-=Penalty;
                if(panel.Points<=0)
                {
                    panel.exit_failure();
                    break;
                }
                else
                {
                    Player_restart();
                    break;
                }
            }
        }
        //Collision tests for mines
        for(Mine mine:panel.mines)
        {
            if(hitBox.intersects(mine.hitBox))
            {
                panel.Points-=Penalty;
                if(panel.Points<=0)
                {
                   panel.exit_failure();
                    break;
                }
                else
                {
                    Player_restart();

                    break;
                }
            }
        }
        for(Bullet bullet:panel.bullets)
        {
            if(hitBox.intersects(bullet.hitBox))
            {
                panel.Points-=Penalty;
                if(panel.Points<=0)
                {
                    bullet.remove=true;
                    panel.exit_failure();
                    break;
                }

                else
                {
                    Player_restart();

                    break;
                }
            }
        }
        for(Turret turret:panel.turrets)
        {
            if(hitBox.intersects(turret.hitBox))
            {
                panel.Points-=Penalty;
                if(panel.Points<=0)
                {
                    panel.exit_failure();
                    break;
                }
                else
                {
                    Player_restart();

                    break;
                }
            }
        }

        if (!removed) {
            for (Lever lever : panel.levers) {
                if (hitBox.intersects(lever.hitBox)) {
                    if (!panel.opened) panel.opened = true;
                    System.out.println(panel.opened);
                    panel.Sound_Play("files/sounds/tmp.wav");
                    removed=true;
                }

            }
        }


        for(Win_block win_block:panel.win_blocks)
        {
            if(hitBox.intersects(win_block.hitBox))
            {
                panel.exit_success();
                break;
            }
        }

        //Changes applied here
        hitBox.x=x;
        hitBox.y=y;


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
        Images tmp=Images.IDLE_RIGHT;
        if(gravity_switch)
        {
            if (!test) {
                if(looking_left)
                {
                    if (keyLeft)
                        tmp=Images.RUNNING_LEFT;
                    else
                        tmp=Images.IDLE_LEFT;
                } else {

                    if (keyRight)
                        if (holds) tmp=Images.HOLDING;
                        else tmp=Images.RUNNING_RIGHT;
                    else
                        tmp=Images.IDLE_RIGHT;
                }
            } else {
                if (looking_left)
                    tmp=Images.JUMPING_LEFT;
                else
                    tmp=Images.JUMPING_RIGHT;
            }
        }
        else
        {
            if (!test) {
                if(looking_left)
                {
                    if (keyLeft)
                        tmp=Images.INVERTED_RUNNING_LEFT;
                    else
                        tmp=Images.INVERTED_IDLE_LEFT;
                } else {
                    if (keyRight)
                        tmp=Images.INVERTED_RUNNING_RIGHT;
                    else
                        tmp=Images.INVERTED_IDLE_RIGHT;
                }
            } else {
                if (looking_left)
                    tmp=Images.INVERTED_JUMPING_LEFT;
                else
                    tmp=Images.INVERTED_JUMPING_RIGHT;
            }
        }

        if(!hitBox_type)
        {
            if(gravity_switch)
            {
                if (!test) {
                    if(looking_left)
                    {
                        if (keyLeft)
                            tmp=Images.DARK_RUNNING_LEFT;
                        else
                            tmp=Images.DARK_IDLE_LEFT;
                    } else {
                        if (keyRight)

                            tmp=Images.DARK_RUNNING_RIGHT;
                        else
                            tmp=Images.DARK_IDLE_RIGHT;
                    }
                } else {
                    if (looking_left)
                        tmp=Images.DARK_JUMPING_LEFT;
                    else
                        tmp=Images.DARK_JUMPING_RIGHT;
                }
            }
            else
            {
                if (!test) {
                    if(looking_left)
                    {
                        if (keyLeft)
                            tmp=Images.DARK_INVERTED_RUNNING_LEFT;
                        else
                            tmp=Images.DARK_INVERTED_IDLE_LEFT;
                    } else {
                        if (keyRight)
                            tmp=Images.DARK_INVERTED_RUNNING_RIGHT;
                        else
                            tmp=Images.DARK_INVERTED_IDLE_RIGHT;
                    }
                } else {
                    if (looking_left)
                        tmp=Images.DARK_INVERTED_JUMPING_LEFT;
                    else
                        tmp=Images.DARK_INVERTED_JUMPING_RIGHT;
                }
            }
        }

        if(debug1) gtd.drawImage(i[tmp.give_Id()], x, y, null);
        else
        {
            Color C=Color.gray;
            if(test)C=Color.ORANGE;

            gtd.setColor(C);
            gtd.drawRect(hitBox.x,hitBox.y,width,height);
            gtd.setColor(C);
            gtd.fillRect(x+1,y+1,width-2,height-2);
        }
    }

    void Player_restart()
    {
        gravity_switch=true;
        if(Restart==0)
        {
            x= panel.StartX;
            y= panel.StartY;
        }
        else
        {
            for(Spawn spawn:panel.spawns)
            {
                if(spawn.getIndex()==Restart)
                {
                    x=spawn.getX();
                    y=spawn.getY();
                }
            }
        }

    }

}