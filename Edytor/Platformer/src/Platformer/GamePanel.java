package Platformer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends javax.swing.JPanel implements ActionListener {

    Cursor cursor;
    Timer gameTimer;
    ArrayList <Wall> walls = new ArrayList<>();
    ArrayList <Gravity_Changer> changers = new ArrayList<>();
    ArrayList <WallB> wallsB = new ArrayList<>();
    ArrayList <KillBox> boxes = new ArrayList<>();

    public GamePanel()
    {
        cursor = new Cursor(400,300,this);
        gameTimer = new Timer();


        //walls.add(new Wall(i,800,90,50));

        gameTimer.schedule(new TimerTask(){

            @Override
            public void run() {
                cursor.set();
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
        cursor.draw(gtd);

        int gridSize=64;
        gtd.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(1f);
        gtd.setStroke(stroke);
        for (int i=0; i<getWidth(); i+=gridSize){
            gtd.drawLine(i, 0, i, getHeight());
            gtd.drawLine(0, i, getWidth(), i);
        }
        //System.out.println(getWidth());

    }


    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == ' ')cursor.put();
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)System.exit(0);
        if(e.getKeyChar() =='p' )cursor.change_Id(true);
        if(e.getKeyChar() == 'l')cursor.change_Id(false);

    }

    public void keyReleased(KeyEvent e) {

    }

    @Override public void actionPerformed(ActionEvent e) {

    }
    
}