package Platformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.PKCS12Attribute;
import java.util.*;
import java.util.Timer;

public class GamePanel extends javax.swing.JPanel implements ActionListener {

    Cursor cursor;
    Timer gameTimer;
    ArrayList <Wall> walls = new ArrayList<>();
    ArrayList <Gravity_Changer> changers = new ArrayList<>();
    ArrayList <WallB> wallsB = new ArrayList<>();
    ArrayList <Wall_Jump> jumps = new ArrayList<>();
    ArrayList <Spike> spikes = new ArrayList<>();
    ArrayList <Mine> mines = new ArrayList<>();
    ArrayList <Turret> turrets = new ArrayList<>();
    ArrayList <Player_spawn> spawns =new ArrayList<>();
    ArrayList <Tile> tiles =new ArrayList<>();
    Toolkit t=Toolkit.getDefaultToolkit();
    Image wallI[] = new Image[10];
    Image grav = t.getImage("files/Tiles/gravity.png");
    Image wallBI[] = new Image[10];
    Image wallJI=t.getImage("files/Tiles/gravity.png");
    Image spikeI=t.getImage("files/Tiles/gravity.png");
    Image mine_png=t.getImage("files/Tiles/mine.png");
    Image turret_png =t.getImage("files/Tiles/turret.png");
    Image tileset[] = new Image[10];


    public GamePanel()
    {
        for(int i=0;i<10;i++)
        {
            wallI[i]=t.getImage("files/Tiles/Wall/"+(i+1)+".png");
            wallBI[i]=t.getImage("files/Tiles/Wall/"+(i+1)+".png");
            tileset[i]=t.getImage("files/Tiles/background/"+(i+1)+".png");
        }
        String username = System.getProperty("user.name");
        JFileChooser fs = new JFileChooser(new File("C:\\Users\\" + username + "\\OneDrive\\Pulpit"));

        fs.setDialogTitle("Otwórz...");
        int result = fs.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                int tmp1;
                int tmp2;
                int tmp3;
                File myObj = new File(String.valueOf(fs.getSelectedFile()));
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();

                    switch (Integer.valueOf(data)) {
                        case 0:
                             tmp1=Integer.valueOf(myReader.nextLine());
                             tmp2=Integer.valueOf(myReader.nextLine());
                             tmp3=Integer.valueOf(myReader.nextLine());
                            walls.add(new Wall(tmp1,tmp2,wallI[tmp3],tmp3));
                            break;
                        case 1:
                            tmp1=Integer.valueOf(myReader.nextLine());
                            tmp2=Integer.valueOf(myReader.nextLine());
                            tmp3=Integer.valueOf(myReader.nextLine());
                            wallsB.add(new WallB(tmp1,tmp2,wallBI[tmp3],tmp3));
                            break;
                        case 2:
                            changers.add(new Gravity_Changer(Integer.valueOf(myReader.nextLine()), Integer.valueOf(myReader.nextLine()),grav));
                            break;
                        case 3:
                            jumps.add(new Wall_Jump(Integer.valueOf(myReader.nextLine()), Integer.valueOf(myReader.nextLine()),wallJI));
                            break;
                        case 4:
                            spikes.add(new Spike(Integer.valueOf(myReader.nextLine()), Integer.valueOf(myReader.nextLine()),spikeI));
                            break;
                        case 5:
                            mines.add(new Mine(Integer.valueOf(myReader.nextLine()), Integer.valueOf(myReader.nextLine()),mine_png));
                            break;
                        case 6:
                            turrets.add(new Turret(Integer.valueOf(myReader.nextLine()), Integer.valueOf(myReader.nextLine()),turret_png));
                            break;
                        case 7:
                            spawns.add(new Player_spawn(Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine()),Integer.valueOf(myReader.nextLine())));
                            break;
                        case 8:
                            tmp1=Integer.valueOf(myReader.nextLine());
                            tmp2=Integer.valueOf(myReader.nextLine());
                            tmp3=Integer.valueOf(myReader.nextLine());
                            tiles.add(new Tile(tmp1,tmp2,tileset[tmp3],tmp3));
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
        for(Tile tile:tiles)tile.draw(gtd);
        for(Wall wall:walls)wall.draw(gtd);
        for(Gravity_Changer gravity_changer:changers)gravity_changer.draw(gtd);
        for(WallB wallB:wallsB)wallB.draw(gtd);
        for(Wall_Jump Wjump:jumps)Wjump.draw(gtd);
        for(Spike spike:spikes)spike.draw(gtd);
        for(Mine mine:mines)mine.draw(gtd);
        for(Turret turret:turrets)turret.draw(gtd);
        for(Player_spawn spawn:spawns)spawn.draw(gtd);

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
        if(e.getKeyChar() == 'u')cursor.change_ID(true);
        if(e.getKeyChar() == 'h')cursor.change_ID(false);
        if(e.getKeyChar() == 'c')cursor.clear();
    }

    public void keyReleased(KeyEvent e) {

    }

    @Override public void actionPerformed(ActionEvent e) {

    }
    
}