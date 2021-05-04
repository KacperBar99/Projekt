package Platformer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GamePanel extends javax.swing.JPanel implements ActionListener {

    int xlevel=0;
    int ylevel=0;
    int level_counter=0;
    Player player;
    Timer gameTimer;
    ArrayList <Wall> walls = new ArrayList<>();
    ArrayList <Gravity_Changer> changers = new ArrayList<>();
    ArrayList <WallB> wallsB = new ArrayList<>();
    ArrayList <Wall_Jump> jumps = new ArrayList<>();
    ArrayList <Spike> spikes = new ArrayList<>();
    ArrayList <Mine> mines = new ArrayList<>();
    ArrayList <Bullet> bullets = new ArrayList<>();
    ArrayList <Turret> turrets = new ArrayList<>();

    public GamePanel()
    {
        player = new Player(200,300,this);
        try {
            File myObj = new File(xlevel+""+ylevel+".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                switch (Integer.valueOf(data))
                {
                    case 0:
                        walls.add(new Wall(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                        break;
                    case 1:
                        wallsB.add(new WallB(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                        break;
                    case 2:
                        changers.add(new Gravity_Changer(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                        break;
                    case 3:
                        jumps.add(new Wall_Jump(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                        break;
                    case 4:
                        spikes.add(new Spike(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                        break;
                    case 5:
                        mines.add(new Mine(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                        break;
                    case 6:
                        turrets.add(new Turret(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                        break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }




        gameTimer = new Timer();
        GamePanel copy=this;

        gameTimer.schedule(new TimerTask(){

            @Override
            public void run() {
                new_level();
                player.set();
                for(Mine mine:mines)
                {
                    mine.set();
                    mine.check(copy);
                }
                for(Bullet bullet:bullets)
                {
                    bullet.set();
                    bullet.check(copy);
                }
                for(Turret turret:turrets)
                {
                    turret.set(copy);
                }
                Iterator itr;
                itr=bullets.iterator();
                while ((itr.hasNext()))
                {
                    Bullet bullet = (Bullet) itr.next();
                    if(bullet.remove)itr.remove();
                }
                repaint();

            }
        },0,17);
    }
    //Deleting objects from level and loading new ones
    public void new_level()
    {
        if(player.where.is_true())
        {
            xlevel=player.where.changex(xlevel);
            ylevel=player.where.changey(ylevel);
            level_counter++;
            Iterator itr;
            itr = walls.iterator();
            while(itr.hasNext())
            {
                Wall wall=(Wall) itr.next();
                itr.remove();
            }
            itr = wallsB.iterator();
            while(itr.hasNext())
            {
               WallB wallb=(WallB) itr.next();
                itr.remove();
            }
            itr = changers.iterator();
            while(itr.hasNext())
            {
                Gravity_Changer changer=(Gravity_Changer) itr.next();
                itr.remove();
            }
            itr=jumps.iterator();
            while(itr.hasNext())
            {
                Wall_Jump Wjump=(Wall_Jump) itr.next();
                itr.remove();
            }
            itr=spikes.iterator();
            while(itr.hasNext())
            {
                Spike spike = (Spike)itr.next();
                itr.remove();
            }
            itr=mines.iterator();
            while(itr.hasNext())
            {
                Mine mine = (Mine)itr.next();
                itr.remove();
            }
            itr=bullets.iterator();
            while(itr.hasNext())
            {
                Bullet bullet = (Bullet)itr.next();
                itr.remove();
            }
            itr=turrets.iterator();
            while (itr.hasNext())
            {
                Turret turret = (Turret) itr.next();
                itr.remove();
            }
            try {
                File myObj = new File(xlevel+""+ylevel+".txt");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();

                    switch (Integer.valueOf(data))
                    {
                        case 0:
                            walls.add(new Wall(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                            break;
                        case 1:
                            wallsB.add(new WallB(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                            break;
                        case 2:
                            changers.add(new Gravity_Changer(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                            break;
                        case 3:
                            jumps.add(new Wall_Jump(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                            break;
                        case 4:
                            spikes.add(new Spike(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                            break;
                        case 5:
                            mines.add(new Mine(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                            break;
                        case 6:
                            turrets.add(new Turret(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                            break;
                    }
                }
                myReader.close();
                player = new Player(200,300,this);
            } catch (FileNotFoundException e) {
                System.exit(0);
            }




        }
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D gtd = (Graphics2D) g;
        for(Wall wall:walls)wall.draw(gtd);
        for(Gravity_Changer gravity_changer:changers)gravity_changer.draw(gtd);
        for(WallB wallB:wallsB)wallB.draw(gtd);
        for(Wall_Jump Wjump:jumps)Wjump.draw(gtd);
        for(Spike spike:spikes)spike.draw(gtd);
        for(Mine mine:mines)mine.draw(gtd);
        for(Bullet bullet:bullets)bullet.draw(gtd);
        for(Turret turret:turrets)turret.draw(gtd);
        player.draw(gtd);
    }



    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'a' )player.keyLeft = true;
        if(e.getKeyChar() == 's' )player.keyDown = true;
        if(e.getKeyChar() == 'd' )player.keyRight = true;
        if(e.getKeyChar() == 'w' )
        {
            player.test=false;
            if(player.keyUP==false && player.jump>0 && player.jumped==true) player.jump();
            player.keyUP = true;
        }
        if(e.getKeyChar() == ' ')player.dash();
        if(e.getKeyChar()== 'g')player.change_Gravity();
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)System.exit(0);
        if(e.getKeyChar() == 'f')player.change_HitBox_type();
        if(e.getKeyChar() == 'h')player.debug1=!player.debug1;
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == 'a')player.keyLeft = false;
        if(e.getKeyChar() == 's' )player.keyDown = false;
        if(e.getKeyChar() == 'd' )player.keyRight = false;
        if(e.getKeyChar() == 'w' )
        {
            player.keyUP = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}