package Platformer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends javax.swing.JPanel implements ActionListener {


    Player player;
    Timer gameTimer;
    ArrayList <Wall> walls = new ArrayList<>();
    ArrayList <Gravity_Changer> changers = new ArrayList<>();
    ArrayList <WallB> wallsB = new ArrayList<>();
    ArrayList <KillBox> boxes = new ArrayList<>();

    public GamePanel()
    {
        player = new Player(400,300,this);
        makeWalls();
        makeChangers();
        makeWallsB();
        makeBoxes();
        gameTimer = new Timer();

        gameTimer.schedule(new TimerTask(){

            @Override
            public void run() {
                player.set();
                repaint();

            }
        },0,17);
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D gtd = (Graphics2D) g;
        for(Wall wall:walls)wall.draw(gtd);
        for(Gravity_Changer gravity_changer:changers)gravity_changer.draw(gtd);
        for(WallB wallB:wallsB)wallB.draw(gtd);
        for(KillBox box:boxes)box.draw(gtd);
        player.draw(gtd);
    }
    public void makeBoxes()
    {
        for(int i=0;i<1080;i+=50)
        {
            boxes.add(new KillBox(1050,i,50,50));
        }
    }
    public void makeWalls()
    {
        for(int i=50;i<600;i+=150)
        {
            walls.add(new Wall(i,0,90,50));
            walls.add(new Wall(i,800,90,50));
        }
        for(int i=0;i<=800;i+=50)
        {
            walls.add(new Wall(100,i,50,50));
        }

    }
    public void makeChangers()
    {
        for(int i=0;i<600;i+=150)
        {
            changers.add(new Gravity_Changer(i,800));
            changers.add(new Gravity_Changer(i,0));
        }
    }
    public void makeWallsB()
    {
        for(int i=600;i<1000;i+=50)
        {
            wallsB.add(new WallB(i,800,50,50));
            wallsB.add(new WallB(i,0,50,50));
        }
        for(int i=0;i<=800;i+=50)
        {
            wallsB.add(new WallB(600,i,50,50));
        }
    }


    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'a' )player.keyLeft = true;
        if(e.getKeyChar() == 's' )player.keyDown = true;
        if(e.getKeyChar() == 'd' )player.keyRight = true;
        if(e.getKeyChar() == 'w' )player.keyUP = true;
        if(e.getKeyChar() == ' ')player.dash();
        if(e.getKeyChar() == 'g')player.change_Gravity();
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)System.exit(0);
        if(e.getKeyChar() == 'f')player.change_HitBox_type();
        if(e.getKeyChar() =='p' )player.change_Id(true);
        if(e.getKeyChar() == 'l')player.change_Id(false);
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == 'a')player.keyLeft = false;
        if(e.getKeyChar() == 's' )player.keyDown = false;
        if(e.getKeyChar() == 'd' )player.keyRight = false;
        if(e.getKeyChar() == 'w' )player.keyUP = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}