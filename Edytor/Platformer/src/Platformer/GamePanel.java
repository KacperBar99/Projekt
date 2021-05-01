package Platformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Timer;

public class GamePanel extends javax.swing.JPanel implements ActionListener {

    Cursor cursor;
    Timer gameTimer;
    ArrayList <Wall> walls = new ArrayList<>();
    ArrayList <Gravity_Changer> changers = new ArrayList<>();
    ArrayList <WallB> wallsB = new ArrayList<>();
    ArrayList <Wall_Jump> jumps = new ArrayList<>();

    public GamePanel()
    {
        String username = System.getProperty("user.name");
        JFileChooser fs = new JFileChooser(new File("C:\\Users\\" + username + "\\OneDrive\\Pulpit"));

        fs.setDialogTitle("Otwórz...");
        int result = fs.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File myObj = new File(String.valueOf(fs.getSelectedFile()));
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();

                    switch (Integer.valueOf(data)) {
                        case 0:
                            walls.add(new Wall(Integer.valueOf(myReader.nextLine()), Integer.valueOf(myReader.nextLine())));
                            break;
                        case 1:
                            wallsB.add(new WallB(Integer.valueOf(myReader.nextLine()), Integer.valueOf(myReader.nextLine())));
                            break;
                        case 2:
                            changers.add(new Gravity_Changer(Integer.valueOf(myReader.nextLine()), Integer.valueOf(myReader.nextLine())));
                            break;
                    }
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        cursor = new Cursor(400,300,this);
        gameTimer = new Timer();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getModifiers() == MouseEvent.BUTTON1_MASK && e.getClickCount() == 1) {
                    cursor.put();
                }
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK && e.getClickCount() == 1) {
                    cursor.delete();
                }


            }
        });



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
        for(Wall_Jump Wjump:jumps)Wjump.draw(gtd);
        cursor.draw(gtd);

        int gridSize=64;
        gtd.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(1f);
        gtd.setStroke(stroke);
        for (int i=0; i<getWidth(); i+=gridSize){
            gtd.drawLine(i, 0, i, getHeight());
            gtd.drawLine(0, i, getWidth(), i);
        }


    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == ' ')cursor.put();
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)cursor.exit();
        if(e.getKeyChar() =='p' )cursor.change_Id(true);
        if(e.getKeyChar() == 'l')cursor.change_Id(false);
    }

    public void keyReleased(KeyEvent e) {

    }

    @Override public void actionPerformed(ActionEvent e) {

    }
    
}