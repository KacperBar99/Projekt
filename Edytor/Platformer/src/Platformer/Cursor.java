package Platformer;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.sql.SQLOutput;

public class Cursor {

    int Id_max;
    int Id;
    GamePanel panel;

    int x;
    int y;
    int width;
    int height;


    Rectangle hitBox;




    public Cursor(int x, int y, GamePanel panel)
    {
        Id=0;
        Id_max=4;

        this.panel=panel;
        this.x=x;
        this.y=y;


        width=49;
        height=49;
        hitBox = new Rectangle(x,y,width,height);

    }
    public void put()
    {

        panel.walls.add(new Wall(x,y,width,height));
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
        change_type();
        gtd.fillRect(x,y,width,height);
    }
    public void exit()
    {
        try{
            FileWriter myWriter = new FileWriter("level.txt");
            for(Wall wall: panel.walls)
            {
                myWriter.write(wall.getID()+"\n");
                myWriter.write(wall.getX()+"\n");
                myWriter.write(wall.getY()+"\n");
                myWriter.write(wall.getWidth()+"\n");
                myWriter.write(wall.getHeight()+"\n");

            }
            for(WallB wallB: panel.wallsB)
            {

            }
            for(Gravity_Changer changer: panel.changers)
            {

            }
            for(KillBox box:panel.boxes)
            {

            }
            myWriter.close();
        }catch (IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
        System.exit(0);
    }
    public void change_type()
    {
        switch (Id)
        {
            case 0:
                width=64;
                height=64;
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 1:
                width=32;
                height=32;
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 2:
                width=16;
                height=16;
                hitBox=new Rectangle(x,y,width,height);
                break;
            default:
                width=64;
                height=32;
                hitBox=new Rectangle(x,y,width,height);
        }
    }
}