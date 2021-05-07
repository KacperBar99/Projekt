package Platformer;

import javax.lang.model.type.NullType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.sql.SQLOutput;
import java.util.Iterator;

public class Cursor {

    int Id_max;
    int Id;
    GamePanel panel;

    int x;
    int y;
    int width;
    int height;
    int Taken[][];
    int Id2;
    int Id2_max;


    Rectangle hitBox;

    public Cursor(int x, int y, GamePanel panel)
    {
        Id2=0;
        Id2_max=10;
        Id=0;
        Id_max=7;

        this.panel=panel;
        this.x=x;
        this.y=y;
        Taken = new int[30][20];

        for(int i=0;i<30;i++)
        {
            for(int j=0;j<20;j++)
            {
                Taken[i][j]=-1;
            }
        }
        for(Wall wall: panel.walls)
        {
            Taken[wall.getX()/64][wall.getY()/64]=0;
        }
        for(WallB wallB:panel.wallsB)
        {
            Taken[wallB.getX()/64][wallB.getY()/64]=1;
        }
        for(Gravity_Changer changer:panel.changers)
        {
            Taken[changer.getX()/64][changer.getY()/64]=2;
        }
        for(Wall_Jump Wjump:panel.jumps)
        {
            Taken[Wjump.getX()/64][Wjump.getY()/64]=3;
        }
        for(Spike spike:panel.spikes)
        {
            Taken[spike.getX()/64][spike.getY()/64]=4;
        }
        for(Mine mine:panel.mines)
        {
            Taken[mine.getX()/64][mine.getY()/64]=5;
        }
        for(Turret turret:panel.turrets)
        {
            Taken[turret.getX()/64][turret.getY()/64]=6;
        }
        for(Player_spawn spawn:panel.spawns)
        {
            Taken[spawn.getX()/64][spawn.getY()/64]=7;
        }



        width=64;
        height=64;
        hitBox = new Rectangle(x,y,width,height);

    }

    public void put()
    {
        if(Taken[x/64][y/64]==-1 ) {
            switch (Id) {
                case 0:
                    panel.walls.add(new Wall(x,y,panel.wallI[Id2]));
                    Taken[x/64][y/64] = Id;

                    break;
                case 1:
                    panel.wallsB.add(new WallB(x,y,panel.grav));
                    Taken[x/64][y/64] = Id;

                    break;
                case 2:
                    panel.changers.add(new Gravity_Changer(x,y,panel.grav));
                    Taken[x/64][y/64] = Id;

                    break;
                case 3:
                    panel.jumps.add(new Wall_Jump(x,y,panel.grav));
                    Taken[x/64][y/64] = Id;

                    break;
                case 4:
                    panel.spikes.add(new Spike(x,y,panel.grav));
                    Taken[x/64][y/64]=Id;
                    break;
                case 5:
                    panel.mines.add(new Mine(x,y,panel.grav));
                    Taken[x/64][y/64]=Id;
                    break;
                case 6:
                    panel.turrets.add(new Turret(x,y,panel.grav));
                    Taken[x/64][y/64]=Id;
                    break;
                case 7:
                    panel.spawns.add(new Player_spawn(x,y,panel.spawns.size()+1));
                    Taken[x/64][y/64]=Id;
                    break;
            }
        }
    }

    public void delete()
    {

        Iterator itr;
        switch (Taken[x/64][y/64])
        {
            case 0:
                itr = panel.walls.iterator();
                while(itr.hasNext())
                {
                    Wall wall=(Wall) itr.next();
                    if(wall.getX()==x && wall.getY()==y)itr.remove();
                }
                break;
            case 1:
                 itr = panel.wallsB.iterator();
                while(itr.hasNext())
                {
                    WallB wallb=(WallB) itr.next();
                    if(wallb.getX()==x && wallb.getY()==y)itr.remove();
                }
                break;
            case 2:
                 itr = panel.changers.iterator();
                while(itr.hasNext())
                {
                    Gravity_Changer changer=(Gravity_Changer) itr.next();
                    if(changer.getX()==x && changer.getY()==y)itr.remove();
                }
                break;
            case 3:
                 itr = panel.jumps.iterator();
                while(itr.hasNext())
                {
                    Wall_Jump Wjump=(Wall_Jump) itr.next();
                    if(Wjump.getX()==x && Wjump.getY()==y)itr.remove();
                }
                break;
            case 4:
                itr=panel.spikes.iterator();
                while(itr.hasNext())
                {
                    Spike spike=(Spike) itr.next();
                    if(spike.getX()==x && spike.getY()==y)itr.remove();
                }
                break;
            case 5:
                itr=panel.mines.iterator();
                while(itr.hasNext())
                {
                    Mine mine = (Mine) itr.next();
                    if(mine.getX()==x && mine.getY()==y)itr.remove();
                }
                break;
            case 6:
                itr=panel.turrets.iterator();
                while (itr.hasNext())
                {
                    Turret turret = (Turret) itr.next();
                    if(turret.getX()==x && turret.getY()==y)itr.remove();
                }
                break;
            case 7:
                itr=panel.spawns.iterator();
                while (itr.hasNext())
                {
                    Player_spawn spawn = (Player_spawn) itr.next();
                    if(spawn.getX()==x && spawn.getY()==y)itr.remove();
                }
                break;

        }
       Taken[x/64][y/64]=-1;
    }

    public void change_Id(boolean up)
    {
        if(up)
        {
            if(Id==Id_max)Id=0;
            else Id++;
        }
        else
        {
            if(Id==0)Id=Id_max;
            else Id--;
        }
    }
    public void change_ID(boolean up)
    {
        if(up)
        {
            if(Id2==Id2_max)Id2=0;
            else Id2++;
        }
        else
        {
            if(Id2==0)Id2=Id2_max;
            else Id2--;
        }
    }
    public void set()
    {

        panel.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {

                x = e.getX();
                y = e.getY();
            }
        });
        x=(x/64)*64;
        y=(y/64)*64;
    }

    public void draw(Graphics2D gtd)
    {
        change_type(gtd);
        gtd.fillRect(x,y,width,height);
    }
    public void exit() {
        String username = System.getProperty("user.name");
        JFileChooser fs = new JFileChooser(new File("C:\\Users\\" + username + "\\OneDrive\\Pulpit"));

        fs.setDialogTitle("Zapisz jako...");
        int result = fs.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fs.getSelectedFile();
            try {
                FileWriter myWriter = new FileWriter(file.getPath() + ".txt");

                for (Wall wall : panel.walls) {
                    myWriter.write(0 + "\n");
                    myWriter.write(wall.getX() + "\n");
                    myWriter.write(wall.getY() + "\n");
                    myWriter.write(wall.getGraphic()+"\n");
                }
                for (WallB wallB : panel.wallsB) {
                    myWriter.write(1 + "\n");
                    myWriter.write(wallB.getX() + "\n");
                    myWriter.write(wallB.getY() + "\n");
                }
                for (Gravity_Changer changer : panel.changers) {
                    myWriter.write(2 + "\n");
                    myWriter.write(changer.getX() + "\n");
                    myWriter.write(changer.getY() + "\n");
                }
                for (Wall_Jump jump : panel.jumps) {
                    myWriter.write(3 + "\n");
                    myWriter.write(jump.getX() + "\n");
                    myWriter.write(jump.getY() + "\n");
                }
                for (Spike spike : panel.spikes)
                {
                    myWriter.write(4+"\n");
                    myWriter.write(spike.getX() + "\n");
                    myWriter.write(spike.getY() + "\n");
                }
                for(Mine mine : panel.mines)
                {
                    myWriter.write(5+"\n");
                    myWriter.write(mine.getX() + "\n");
                    myWriter.write(mine.getY() + "\n");
                }
                for(Turret turret:panel.turrets)
                {
                    myWriter.write(6+"\n");
                    myWriter.write(turret.getX() + "\n");
                    myWriter.write(turret.getY() + "\n");
                }
                int tmp=0;
                for(Player_spawn spawn:panel.spawns)
                {
                    myWriter.write(7+"\n");
                    myWriter.write(spawn.getX() + "\n");
                    myWriter.write(spawn.getY() + "\n");
                    myWriter.write(spawn.getN()+"\n");
                }
                myWriter.flush();
                myWriter.close();
            } catch (IOException e) {
                System.out.println("Error");
                e.printStackTrace();
            }
            System.exit(0);
        }
    }
    public void change_type(Graphics2D gtd)
    {
        switch (Id)
        {
            case 0:
                hitBox=new Rectangle(x,y,width,height);
                gtd.setColor(Color.WHITE);
                break;
            case 1:
                gtd.setColor(Color.black);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 2:
                gtd.setColor(Color.blue);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 3:
               gtd.setColor(Color.green);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 4:
                gtd.setColor(Color.cyan);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 5:
                gtd.setColor(Color.red);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 6:
                gtd.setColor(Color.magenta);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 7:
                gtd.setColor(Color.gray);
                hitBox=new Rectangle(x,y,width,height);
                break;
            default:
                gtd.setColor(Color.DARK_GRAY);
                hitBox=new Rectangle(x,y,width,height);
        }
    }
}