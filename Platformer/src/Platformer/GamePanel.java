package Platformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.Timer;

public class GamePanel extends javax.swing.JPanel implements ActionListener {

    int win_counter=0;
    String[] listN;
    long listT[];
    int universal_64=64;
    boolean win=false;
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
    ArrayList <Spawn> spawns = new ArrayList<>();
    ArrayList <Tile> tiles =new ArrayList<>();
    ArrayList <Letter> letters = new ArrayList<>();
    Toolkit t=Toolkit.getDefaultToolkit();
    Image A_letter [] = new Image[26];
    Image Letter_0 [] = new Image[10];
    Image grav = t.getImage("files/Tiles/gravity.png");
    Image wallI[] = new Image[10];
    Image tileset[] = new Image[10];
    Image mine_png=t.getImage("files/Tiles/mine.png");
    Image bullet_png=t.getImage("files/Bullet.png");
    Instant start = Instant.now();


    public GamePanel()
    {
        for(int i=0;i<10;i++)
        {
            Letter_0[i]=t.getImage("literki/"+i+".png");
        }
        for(int i=0;i<26;i++)
        {
            char tmp= (char) ('A'+i);
            A_letter[i]=t.getImage("literki/"+tmp+".png");
        }


        for(int i=0;i<10;i++)
        {
            wallI[i]=t.getImage("files/Tiles/Wall/"+(i+1)+".png");
            tileset[i]=t.getImage("files/Tiles/Wall/"+(i+1)+".png");
        }
        player = new Player(200,300,this);
        try {
            File myObj = new File("levels/"+xlevel+"_"+ylevel+".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                int tmp1;
                int tmp2;
                int tmp3;
                switch (Integer.valueOf(data))
                {
                    case 0:
                         tmp1=Integer.valueOf(myReader.nextLine());
                         tmp2=Integer.valueOf(myReader.nextLine());
                         tmp3=Integer.valueOf(myReader.nextLine());
                    walls.add(new Wall(tmp1,tmp2,wallI[tmp3]));
                    break;
                    case 1:
                        wallsB.add(new WallB(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),wallI[5]));
                        break;
                    case 2:
                        changers.add(new Gravity_Changer(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),grav));
                        break;
                    case 3:
                        jumps.add(new Wall_Jump(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),wallI[6]));
                        break;
                    case 4:
                        spikes.add(new Spike(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),wallI[1]));
                        break;
                    case 5:
                        mines.add(new Mine(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),mine_png));
                        break;
                    case 6:
                        turrets.add(new Turret(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),wallI[6],bullet_png));
                        break;
                    case 7:
                        level_counter++;
                        spawns.add(new Spawn(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                        break;
                    case 8:
                        tmp1=Integer.valueOf(myReader.nextLine());
                        tmp2=Integer.valueOf(myReader.nextLine());
                        tmp3=Integer.valueOf(myReader.nextLine());
                        tiles.add(new Tile(tmp1,tmp2,universal_64,universal_64,tileset[tmp3]));
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
                if(!win)player.set();
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
            level_counter=0;
            xlevel+=player.where.changex(xlevel);
            ylevel+=player.where.changey(ylevel);

            clear();
            try {
                int tmp1;
                int tmp2;
                int tmp3;
                System.out.println(xlevel+"_"+ylevel);
                File myObj = new File("levels/"+xlevel+"_"+ylevel+".txt");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();

                    switch (Integer.valueOf(data))
                    {
                        case 0:
                          tmp1=Integer.valueOf(myReader.nextLine());
                            tmp2=Integer.valueOf(myReader.nextLine());
                           tmp3=Integer.valueOf(myReader.nextLine());
                            walls.add(new Wall(tmp1,tmp2,wallI[tmp3]));
                            break;
                        case 1:
                            wallsB.add(new WallB(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),wallI[5]));
                            break;
                        case 2:
                            changers.add(new Gravity_Changer(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),grav));
                            break;
                        case 3:
                            jumps.add(new Wall_Jump(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),wallI[6]));
                            break;
                        case 4:
                            spikes.add(new Spike(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),wallI[1]));
                            break;
                        case 5:
                            mines.add(new Mine(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),mine_png));
                            break;
                        case 6:
                            turrets.add(new Turret(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),wallI[6],bullet_png));
                            break;
                        case 7:
                            level_counter++;
                            spawns.add(new Spawn(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                            break;
                        case 8:
                            tmp1=Integer.valueOf(myReader.nextLine());
                            tmp2=Integer.valueOf(myReader.nextLine());
                            tmp3=Integer.valueOf(myReader.nextLine());
                            tiles.add(new Tile(tmp1,tmp2,universal_64,universal_64,tileset[tmp3]));
                            break;
                    }
                }
                myReader.close();
                if(player.where.up)
                {
                    level_counter=3;
                }
                else if(player.where.right)
                {
                    level_counter=4;
                }
                else if (player.where.down)
                {
                    level_counter=1;
                }
                else if (player.where.left)
                {
                    level_counter=2;
                }
                for(Spawn spawn:spawns)
                {
                    if(spawn.getIndex()==level_counter)player = new Player(spawn.getX(),spawn.getY(),this);
                }
            } catch (FileNotFoundException e) {
               xlevel=0;
               ylevel=0;
               new_level();
            }




        }
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D gtd = (Graphics2D) g;
        if(!win)
        {

            for(Tile tile:tiles)tile.draw(gtd,false);
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
        else
        {
            for(Tile tile:tiles)tile.draw(gtd,true);
            for(Letter letter:letters)letter.draw(gtd);
        }
    }



    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'a' )player.keyLeft = true;
        if(e.getKeyChar() == 's' )player.keyDown = true;
        if(e.getKeyChar() == 'd' )player.keyRight = true;
        if(e.getKeyChar() == 'w' )
        {
            player.test=false;
            if(!player.keyUP && !player.can_jump && player.djump) //tu wywolywany jest doublejump
            {
                player.jump();
                player.djump=false;
            }
            player.keyUP = true;
        }
        if(e.getKeyChar() == ' ')player.dash();
        if(e.getKeyChar()== 'g')player.change_Gravity();
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            exit_success();
        }
        if(e.getKeyChar() == 'f')player.change_HitBox_type();
        if(e.getKeyChar() == 'h')player.debug1=!player.debug1;
        if(e.getKeyCode() == KeyEvent.VK_ENTER && win)
        {

        }
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
    public void exit_success()
    {
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        long Input_time=(long)timeElapsed.toMillis();
        win=true;
        clear();
        listN = new String[10];
        listT = new long[10];

        //loading data from file
        try
        {
            File myObj = new File("Records/Records.txt");
            Scanner myReader = new Scanner(myObj);
            for(int i=0;i<10;i++)
            {
                listN[i]=myReader.nextLine();
                listT[i]=Integer.valueOf(myReader.nextLine());
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        create_End();


        String tmp = JOptionPane.showInputDialog("You won! Please enter your name:");
        Result(tmp,Input_time);
        System.exit(0);
    }
    public void exit_failure()
    {
        clear();
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Sorry "+timeElapsed.toMillis());
        System.exit(1);
    }
    public void Result(String N,long time)
    {









        //Changing best record possible
        for(int i=0;i<10;i++)
        {
            if(time>=listT[i])
            {
                Scanner scan=new Scanner(N);
                listN[i]=N;
                listT[i]=time;
                break;
            }
        }




        //saving new data from file
        try {
            FileWriter myWriter = new FileWriter("Records/Records.txt");
            for(int i=0;i<10;i++)
            {
                myWriter.write(listN[i]+"\n");
                myWriter.write(listT[i]+"\n");
            }
            myWriter.flush();
            myWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clear()
    {
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
        itr=spawns.iterator();
        while(itr.hasNext())
        {
            Spawn spawn = (Spawn) itr.next();
            itr.remove();
        }
        itr=tiles.iterator();
        while (itr.hasNext())
        {
            Tile tile = (Tile) itr.next();
            itr.remove();
        }
    }
    public void create_End()
    {
        for(int j=0;j<10;j++)
        {
            for (int i = 0; i < listN[j].length(); i++) {
                letters.add(new Letter(i*77,j*102,77,137,A_letter[listN[j].charAt(i)-'A']));
            }
            String tmp=(listT[j]+"");
            for (int i = 0; i < tmp.length(); i++) {
                letters.add(new Letter(960 + i * 77, j * 102, 77, 137,Letter_0[tmp.charAt(i) - '0']));
            }
        }





        for(int i=0;i<10;i++)
        {
            tiles.add(new Tile(0,i*102,960,102,tileset[0]));
            tiles.add(new Tile(960,i*102,960,102,tileset[0]));
        }



    }
}