package Platformer;

import javax.lang.model.type.NullType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.Timer;

public class GamePanel extends javax.swing.JPanel implements ActionListener {

    int tileset_size = 21;
    int background_size = 13+1;

    int state=0;
    int menu_handler=0;
    int StartX=200;
    int StartY=300;
    int Points=1000;
    int start_points=1000;
    int name_iterator;
    int max_nickname_length=10;
    Letter enter_name[];
    char nickname[];
    int Place_in_results;
    String[] listN;
    long listT[];
    int universal_value=64;
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
    Image bullet_png=t.getImage("files/Bullet.png");
    Image wallI[] = new Image[tileset_size];
    Image grav = t.getImage("files/Tiles/gravity.png");
    Image wallBI[] = new Image[tileset_size];
    Image wallJI=t.getImage("files/Tiles/wjump.png");
    Image spikeI=t.getImage("files/Tiles/bolec.png");
    Image mine_png=t.getImage("files/Tiles/mine.png");
    Image turret_png[] = new Image[4];
    Image tileset[] = new Image[background_size];
    Image menuN[] = new Image[3];
    Image menuS[]= new Image[3];
    Image hp_numbers[]=new Image[10];
    ArrayList <Letter> Points_Show = new ArrayList<>();
    ArrayList <Win_block> win_blocks = new ArrayList<>();
    Image win_blockI=t.getImage("files/Tiles/win.png");
    Image background_menu=t.getImage("files/menu/menubackground.png");
    ArrayList <Background> backgrounds= new ArrayList<>();
    Image result_false=t.getImage("files/menu/wynikiw.png");
    Image result_true=t.getImage("files/menu/wyniki.png");

    Instant start;


    public GamePanel()
    {
        for(int i=0;i<4;i++)
        {
            turret_png[i]=t.getImage("files/Tiles/d"+(i+1)+".png");
        }
        for(int i=0;i<10;i++)
        {
            hp_numbers[i]=t.getImage("literki/hp/"+i+".png");
        }

        for(int i=0;i<3;i++)
        {
            menuN[i]=t.getImage("files/menu/N"+(i+1)+".png");
            menuS[i]=t.getImage("files/menu/S"+(i+1)+".png");
        }

        for(int i=0;i<10;i++)
        {
            Letter_0[i]=t.getImage("literki/"+i+".png");
        }

        for(int i=0;i<26;i++)
        {
            char tmp= (char) ('A'+i);
            A_letter[i]=t.getImage("literki/"+tmp+".png");
        }

        for(int i=0;i<tileset_size;i++)
        {
            wallI[i]=t.getImage("files/Tiles/Wall/"+(i+1)+".png");
            wallBI[i]=t.getImage("files/Tiles/Wall_B/"+(i+1)+".png");
        }

        for(int i=0;i<background_size;i++)
        {
            tileset[i] = t.getImage("files/Tiles/background/" + (i + 1) + ".png");
        }
        tileset[background_size-1]=t.getImage("files/Tiles/torch.gif");

        listN = new String[10];
        listT = new long[10];

        create_menu();
        Sound_Play("files/sounds/tmp.wav");

        player = new Player(StartX,StartY,this);



        gameTimer = new Timer();
        GamePanel copy=this;

        gameTimer.schedule(new TimerTask(){

            @Override
            public void run() {

                if(state==1)
                {
                    Instant sec=Instant.now();
                    Duration timeElapsed = Duration.between(start, sec);
                    int tmp = (int) timeElapsed.toMillis();
                    if(tmp>=1000)
                    {
                        Points--;
                        start=Instant.now();
                    }
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
                            tmp1=Integer.valueOf(myReader.nextLine());
                            tmp2=Integer.valueOf(myReader.nextLine());
                            tmp3=Integer.valueOf(myReader.nextLine());
                            wallsB.add(new WallB(tmp1,tmp2,wallBI[tmp3]));
                            break;
                        case 2:
                            changers.add(new Gravity_Changer(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),grav));
                            break;
                        case 3:
                            jumps.add(new Wall_Jump(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),wallJI));
                            break;
                        case 4:
                            spikes.add(new Spike(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),spikeI));
                            break;
                        case 5:
                            mines.add(new Mine(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),mine_png,Boolean.valueOf(myReader.nextLine())));
                            break;
                        case 6:
                            tmp1=Integer.valueOf(myReader.nextLine());
                            tmp2=Integer.valueOf(myReader.nextLine());
                            tmp3=Integer.valueOf(myReader.nextLine());
                            turrets.add(new Turret(tmp1,tmp2,turret_png[tmp3],bullet_png,tmp3));
                            break;
                        case 7:
                            level_counter++;
                            spawns.add(new Spawn(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                            break;
                        case 8:
                            tmp1=Integer.valueOf(myReader.nextLine());
                            tmp2=Integer.valueOf(myReader.nextLine());
                            tmp3=Integer.valueOf(myReader.nextLine());
                            tiles.add(new Tile(tmp1,tmp2,universal_value,universal_value,tileset[tmp3]));
                            break;
                        case 9:
                            win_blocks.add(new Win_block(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),win_blockI));
                            break;
                    }
                }
                myReader.close();
                level_counter=0;
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
                if(level_counter==0)
                {
                    player = new Player(StartX,StartY,this);
                }
                else
                for(Spawn spawn:spawns)
                {
                    if(spawn.getIndex()==level_counter)player = new Player(spawn.getX(),spawn.getY(),this);
                }
                player.Restart=level_counter;
            } catch (FileNotFoundException e) {
                exit_failure();
            }
        }
    }
    public void paint(Graphics g)
    {
        hp_update();
        super.paint(g);

        Graphics2D gtd = (Graphics2D) g;

        switch (state)
        {
            case 0:
                for(Background background :backgrounds)background.draw(gtd);
                for(Tile tile:tiles)tile.draw(gtd);
                for(Letter letter:letters)
                {
                    letter.draw(gtd);
                }
                break;
            case 1:
                for(Tile tile:tiles)tile.draw(gtd);
                for(Wall wall:walls)wall.draw(gtd);
                for(Gravity_Changer gravity_changer:changers)gravity_changer.draw(gtd);
                for(WallB wallB:wallsB)wallB.draw(gtd);
                for(Wall_Jump Wjump:jumps)Wjump.draw(gtd);
                for(Spike spike:spikes)spike.draw(gtd);
                for(Mine mine:mines)mine.draw(gtd);
                for(Bullet bullet:bullets)bullet.draw(gtd);
                for(Turret turret:turrets)turret.draw(gtd);
                for(Win_block win_block:win_blocks)win_block.draw(gtd);
                for(Spawn spawn:spawns)spawn.draw(gtd);

                for(Letter show:Points_Show)show.draw(gtd);//musi być na wierzchu
                player.draw(gtd);
                break;
            case 2:
                for(Tile tile:tiles)tile.draw(gtd);
                for(Letter letter:letters)letter.draw(gtd);
                for(int i=0;i<10;i++)
                {
                    if(enter_name[i]!=null)enter_name[i].draw(gtd);
                }
                break;
            case 3:
                for(Tile tile:tiles)tile.draw(gtd);
                for(Letter letter:letters)
                {
                    letter.draw(gtd);
                }
                break;
        }
    }

    public void keyPressed(KeyEvent e)
    {
        switch (state)
        {
            case 0:
            {
                Sound_Play("files/sounds/select.wav");

                if(e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()== KeyEvent.VK_S)
                {
                    menu_handler++;
                    menu_update();
                }
                if(e.getKeyCode()==KeyEvent.VK_UP|| e.getKeyCode()== KeyEvent.VK_W)
                {

                    menu_handler--;
                    menu_update();
                }
                if(e.getKeyCode()==KeyEvent.VK_ENTER)
                {
                    menu_choice();
                }

                break;
            }
            case 1:
            {
                if( e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()== KeyEvent.VK_A)player.keyLeft = true;
                if(e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()== KeyEvent.VK_S)player.keyDown = true;
                if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()== KeyEvent.VK_D)player.keyRight = true;
                if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()== KeyEvent.VK_W)
                {
                    player.test=false;
                    if(!player.keyUP && !player.can_jump && player.djump) //tu wywolywany jest doublejump
                    {
                        Sound_Play("files/sounds/jump.wav");
                        player.jump();
                        player.djump=false;
                    }
                    player.keyUP = true;
                }
                if(e.getKeyChar() == ' ')player.dash();
                if(e.getKeyCode()== KeyEvent.VK_G)player.change_Gravity();
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    exit_success();
                }
                if(e.getKeyChar() == 'f'  || e.getKeyChar()=='F')player.change_HitBox_type();
                if(e.getKeyChar() == 'h'  || e.getKeyChar()=='H')player.debug1=!player.debug1;
                break;
            }
            case 2: {
                    int y_shift=15;
                if (name_iterator < 10) {
                    if (e.getKeyCode()== KeyEvent.VK_Q) {
                        nickname[name_iterator] = 'q';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift , 77, 137, A_letter['q' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()== KeyEvent.VK_W) {
                        nickname[name_iterator] = 'w';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['w' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()== KeyEvent.VK_E) {
                        nickname[name_iterator] = 'e';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['e' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()== KeyEvent.VK_R) {
                        nickname[name_iterator] = 'r';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['r' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()== KeyEvent.VK_T) {
                        nickname[name_iterator] = 't';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['t' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()== KeyEvent.VK_Y) {
                        nickname[name_iterator] = 'y';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['y' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()== KeyEvent.VK_U) {
                        nickname[name_iterator] = 'u';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['u' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()== KeyEvent.VK_I) {
                        nickname[name_iterator] = 'i';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['i' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()== KeyEvent.VK_O) {
                        nickname[name_iterator] = 'o';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['o' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()== KeyEvent.VK_P) {
                        nickname[name_iterator] = 'p';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['p' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()== KeyEvent.VK_A) {
                        nickname[name_iterator] = 'a';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['a' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()== KeyEvent.VK_S) {
                        nickname[name_iterator] = 's';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['s' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()== KeyEvent.VK_D) {
                        nickname[name_iterator] = 'd';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['d' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()== KeyEvent.VK_F) {
                        nickname[name_iterator] = 'f';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['f' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()== KeyEvent.VK_G) {
                        nickname[name_iterator] = 'g';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['g' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()==KeyEvent.VK_H) {
                        nickname[name_iterator] = 'h';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['h' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()==KeyEvent.VK_J) {
                        nickname[name_iterator] = 'j';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['j' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()==KeyEvent.VK_K) {
                        nickname[name_iterator] = 'k';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['k' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()==KeyEvent.VK_L) {
                        nickname[name_iterator] = 'l';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['l' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()==KeyEvent.VK_Z) {
                        nickname[name_iterator] = 'z';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['z' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()==KeyEvent.VK_X) {
                        nickname[name_iterator] = 'x';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['x' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()==KeyEvent.VK_C) {
                        nickname[name_iterator] = 'c';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['c' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()==KeyEvent.VK_V) {
                        nickname[name_iterator] = 'v';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['v' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()==KeyEvent.VK_B) {
                        nickname[name_iterator] = 'b';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['b' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()==KeyEvent.VK_N) {
                        nickname[name_iterator] = 'n';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['n' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyCode()==KeyEvent.VK_M) {
                        nickname[name_iterator] = 'm';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, A_letter['m' - 'a']);
                        name_iterator++;
                    }
                    if (e.getKeyChar() == '1') {
                        nickname[name_iterator] = '1';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, Letter_0['1' - '0']);
                        name_iterator++;
                    }
                    if (e.getKeyChar() == '2') {
                        nickname[name_iterator] = '2';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, Letter_0['2' - '0']);
                        name_iterator++;
                    }
                    if (e.getKeyChar() == '3') {
                        nickname[name_iterator] = '3';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, Letter_0['3' - '0']);
                        name_iterator++;
                    }
                    if (e.getKeyChar() == '4') {
                        nickname[name_iterator] = '4';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, Letter_0['4' - '0']);
                        name_iterator++;
                    }
                    if (e.getKeyChar() == '5') {
                        nickname[name_iterator] = '5';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, Letter_0['5' - '0']);
                        name_iterator++;
                    }
                    if (e.getKeyChar() == '6') {
                        nickname[name_iterator] = '6';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, Letter_0['6' - '0']);
                        name_iterator++;
                    }
                    if (e.getKeyChar() == '7') {
                        nickname[name_iterator] = '7';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, Letter_0['7' - '0']);
                        name_iterator++;
                    }
                    if (e.getKeyChar() == '8') {
                        nickname[name_iterator] = '8';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, Letter_0['8' - '0']);
                        name_iterator++;
                    }
                    if (e.getKeyChar() == '9') {
                        nickname[name_iterator] = '9';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, Letter_0['9' - '0']);
                        name_iterator++;
                    }
                    if (e.getKeyChar() == '0') {
                        nickname[name_iterator] = '0';
                        enter_name[name_iterator] = new Letter(name_iterator * universal_value, Place_in_results * 102+y_shift, 77, 137, Letter_0['0' - '0']);
                        name_iterator++;
                    }
                }

                if (e.getKeyChar() == ' ') ;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String tmp = "";
                    for (int i = 0; i < 10; i++) {
                        if (nickname[i] == 0) break;
                        tmp = tmp + nickname[i];
                    }
                    Result(tmp);
                    change_state(0);
                    create_menu();
                }
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (name_iterator > 0) name_iterator--;
                    enter_name[name_iterator] = null;
                    nickname[name_iterator] = 0;
                }
                break;
            }
            case 3:
                if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
                {
                    change_state(0);
                    create_menu();
                }
                break;
        }

    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == 'a' || e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()== KeyEvent.VK_A)player.keyLeft = false;
        if(e.getKeyChar() == 's' || e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()== KeyEvent.VK_S)player.keyDown = false;
        if(e.getKeyChar() == 'd' || e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()== KeyEvent.VK_D)player.keyRight = false;
        if(e.getKeyChar() == 'w' || e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()== KeyEvent.VK_W)
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
        state=2;
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
        Place_in_results=9;
        if(Points<0)Points=0;
        for(int i=0;i<10;i++)
        {
            if(Points>=listT[i])
            {
                Place_in_results=i;
                break;
            }
        }
        listT[Place_in_results]=Points;

        create_End();
        nickname=new char[max_nickname_length];
        enter_name = new Letter[max_nickname_length];
        name_iterator=0;


    }
    public void exit_failure()
    {
        //clear();
        //Instant end = Instant.now();
        //Duration timeElapsed = Duration.between(start, end);
        //System.out.println("Sorry "+timeElapsed.toMillis());
        player=new Player(StartX,StartY,this);
        change_state(0);
        create_menu();
    }
    public void Result(String N)
    {

        //Changing best record possible

        listN[Place_in_results]=N;


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
        itr=letters.iterator();
        while(itr.hasNext())
        {
            Letter letter=(Letter) itr.next();
            itr.remove();
        }
        itr=Points_Show.iterator();
        while(itr.hasNext())
        {
            Letter letter=(Letter) itr.next();
            itr.remove();
        }
        itr=win_blocks.iterator();
        while(itr.hasNext())
        {
            Win_block win_block=(Win_block) itr.next();
            itr.remove();
        }
        itr=backgrounds.iterator();
        while(itr.hasNext())
        {
            Background background = (Background) itr.next();
            itr.remove();
        }
    }
    public void create_End()
    {
        int y_shift=15;
        listN[Place_in_results]="";
        for(int j=0;j<10;j++)
        {
            for (int i = 0; i < listN[j].length(); i++) {
                if(listN[j].charAt(i)<'a' || listN[j].charAt(i)>'z')
                {
                    letters.add(new Letter(i*universal_value,y_shift+j*102,universal_value,universal_value,Letter_0[listN[j].charAt(i)-'0']));
                }
                else
                {
                    letters.add(new Letter(i*universal_value,j*102+y_shift,universal_value,universal_value,A_letter[listN[j].charAt(i)-'a']));
                }

            }
            String tmp=(listT[j]+"");
            for (int i = 0; i < tmp.length(); i++) {
                letters.add(new Letter(960 + i * universal_value, j * 102+y_shift, universal_value, universal_value,Letter_0[tmp.charAt(i) - '0']));
            }
        }


        for(int i=0;i<10;i++)
        {
            Tile tmp1=new Tile(0,i*102,960,102,result_false);
            Tile tmp2=new Tile(960,i*102,960,102,result_false);
            if(i==Place_in_results)
            {
                tmp1.set_graphic(result_true);
                tmp2.set_graphic(result_true);
            }
            tiles.add(tmp1);
            tiles.add(tmp2);
        }
    }
    public void show_results_no_input()
    {
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
        int y_shift=15;
        for(int j=0;j<10;j++)
        {
            for (int i = 0; i < listN[j].length(); i++) {
                if(listN[j].charAt(i)<'a' || listN[j].charAt(i)>'z')
                {
                    letters.add(new Letter(i*universal_value,j*102+y_shift,universal_value,universal_value,Letter_0[listN[j].charAt(i)-'0']));
                }
                else
                {
                    letters.add(new Letter(i*universal_value,j*102+y_shift,universal_value,universal_value,A_letter[listN[j].charAt(i)-'a']));
                }
            }
            String tmp=(listT[j]+"");
            for (int i = 0; i < tmp.length(); i++) {
                letters.add(new Letter(960 + i * universal_value, j * 102+y_shift, universal_value, universal_value,Letter_0[tmp.charAt(i) - '0']));
            }
        }
        for(int i=0;i<10;i++)
        {
            Tile tmp1=new Tile(0,i*102,960,102,result_false);
            Tile tmp2=new Tile(960,i*102,960,102,result_false);
            tiles.add(tmp1);
            tiles.add(tmp2);
        }
    }
    public void create_menu()
    {
       backgrounds.add(new Background(0,0,background_menu));
        for(int i=0;i<3;i++)
        {
            tiles.add(new Tile(1920/3,i*(1080/3),1920/3,1080/6,menuN[i]));
        }
        menu_update();
    }
    public void menu_update()
    {
        if(menu_handler>2)menu_handler=0;
        if(menu_handler<0)menu_handler=2;

        int i;
        i=0;
        for(Tile tile:tiles)
        {
            if(i==menu_handler){
                tile.set_graphic(menuS[i]);
            }
            else{
                tile.set_graphic(menuN[i]);

            }
            i++;
        }
    }
    public void menu_choice()
    {
        switch (menu_handler)
        {
            case 0:
                xlevel=0;
                ylevel=0;
                change_state(1);
                player.where.set_start();
                start = Instant.now();
                Points=start_points;
                new_level();
                break;
            case 1:
                change_state(3);
               show_results_no_input();
                break;
            case 2:
                System.exit(0);
                break;
        }

    }
    void change_state(int N)
    {
        state=N;
        clear();
    }

    void Sound_Play(String S)
    {
        URL soundbyte = null;

        {
            try {
                soundbyte = new File(S).toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        java.applet.AudioClip clip = java.applet.Applet.newAudioClip(soundbyte);
        clip.play();
    }
    void hp_update()
    {
        if(Points>0) {
            Iterator itr;
            itr = Points_Show.iterator();
            while (itr.hasNext()) {
                Letter letter = (Letter) itr.next();
                itr.remove();
            }
            String tmp = String.valueOf(Points);
            for (int i = 0; i < tmp.length(); i++) {
                Points_Show.add(new Letter(i * universal_value, 0, universal_value, universal_value, hp_numbers[tmp.charAt(i) - '0']));
            }
        }
        //else exit_failure();
    }
}