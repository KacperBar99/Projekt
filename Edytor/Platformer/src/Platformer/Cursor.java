package Platformer;

import java.awt.*;
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

    Rectangle hitBox;

    public Cursor(int x, int y, GamePanel panel)
    {
        Id=0;
        Id_max=4;

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
        for(KillBox box:panel.boxes)
        {
            Taken[box.getX()/64][box.getY()/64]=3;
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
                    panel.walls.add(new Wall(x, y));
                    Taken[x/64][y/64] = Id;

                    break;
                case 1:
                    panel.wallsB.add(new WallB(x, y));
                    Taken[x/64][y/64] = Id;

                    break;
                case 2:
                    panel.changers.add(new Gravity_Changer(x, y));
                    Taken[x/64][y/64] = Id;

                    break;
                case 3:
                    panel.boxes.add(new KillBox(x, y));
                    Taken[x/64][y/64] = Id;

                    break;
                default:
                    x = 0;
                    y = 0;
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
                 itr = panel.walls.iterator();
                while(itr.hasNext())
                {
                    KillBox box=(KillBox) itr.next();
                    if(box.getX()==x && box.getY()==y)itr.remove();
                }
                break;
            default :
                System.out.println("Test");
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
    public void set()
    {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        x = (int) b.getX();
        y = (int) b.getY();
        x=(x/64)*64;
        y=(y/64)*64;
    }

    public void draw(Graphics2D gtd)
    {
        change_type(gtd);
        gtd.fillRect(x,y,width,height);
    }
    public void exit()
    {
        try{
            FileWriter myWriter = new FileWriter("level.txt");
            for(Wall wall: panel.walls)
            {
                myWriter.write(0+"\n");
                myWriter.write(wall.getX()+"\n");
                myWriter.write(wall.getY()+"\n");
            }
            for(WallB wallB: panel.wallsB)
            {
                myWriter.write(1+"\n");
                myWriter.write(wallB.getX()+"\n");
                myWriter.write(wallB.getY()+"\n");
            }
            for(Gravity_Changer changer: panel.changers)
            {
                myWriter.write(2+"\n");
                myWriter.write(changer.getX()+"\n");
                myWriter.write(changer.getY()+"\n");
            }
            for(KillBox box:panel.boxes)
            {
                myWriter.write(3+"\n");
                myWriter.write(box.getX()+"\n");
                myWriter.write(box.getY()+"\n");
            }
            myWriter.close();
        }catch (IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
        System.exit(0);
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
               gtd.setColor(Color.red);
                hitBox=new Rectangle(x,y,width,height);
                break;
            default:
                gtd.setColor(Color.gray);
                hitBox=new Rectangle(x,y,width,height);
        }
    }
}