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
    Image show;


    Rectangle hitBox;

    public Cursor(int x, int y, GamePanel panel)
    {
        Id2=0;
        Id2_max=panel.tileset_size-1;
        Id=0;
        Id_max=9;

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
        for(Tile tile:panel.tiles)
        {
            if(Taken[tile.getX()/64][tile.getY()/64]!=5)
            Taken[tile.getX()/64][tile.getY()/64]=8;
        }
        for(Win_block win_block:panel.win_blocks)
        {
            Taken[win_block.getX()/64][win_block.getY()/64]=9;
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
                    panel.walls.add(new Wall(x,y,panel.wallI[Id2],Id2));
                    Taken[x/64][y/64] = Id;
                    break;
                case 1:
                    panel.wallsB.add(new WallB(x,y,panel.wallBI[Id2],Id2));
                    Taken[x/64][y/64] = Id;
                    break;
                case 2:
                    panel.changers.add(new Gravity_Changer(x,y,panel.grav));
                    Taken[x/64][y/64] = Id;
                    break;
                case 3:
                    panel.jumps.add(new Wall_Jump(x,y, panel.wallJI));
                    Taken[x/64][y/64] = Id;
                    break;
                case 4:
                    panel.spikes.add(new Spike(x,y, panel.spikeI));
                    Taken[x/64][y/64]=Id;
                    break;
                case 5:
                    if(Id2==0)
                    {
                        panel.mines.add(new Mine(x,y,panel.mine_png,true));
                    }
                    else
                    {
                        panel.mines.add(new Mine(x,y,panel.mine_png,false));
                    }
                    panel.tiles.add(new Tile(x,y, panel.tileset[Id2],Id2));
                    Taken[x/64][y/64]=Id;
                    break;
                case 6:
                    panel.turrets.add(new Turret(x,y,panel.turret_png));
                    Taken[x/64][y/64]=Id;
                    break;
                case 7:
                    panel.spawns.add(new Player_spawn(x,y,panel.spawns.size()+1));
                    Taken[x/64][y/64]=Id;
                    break;
                case 8:
                    panel.tiles.add(new Tile(x,y,panel.tileset[Id2],Id2));
                    Taken[x/64][y/64] = Id;
                    break;
                case 9:
                    panel.win_blocks.add(new Win_block(x,y,panel.winblockI));
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
                    if(spike.getX()==x && spike.getY()==y) itr.remove();
                }
                break;
            case 5:
                itr=panel.mines.iterator();
                while(itr.hasNext())
                {
                    Mine mine = (Mine) itr.next();
                    if(mine.getX()==x && mine.getY()==y)itr.remove();
                }
                itr=panel.tiles.iterator();
                while (itr.hasNext())
                {
                    Tile tile = (Tile) itr.next();
                    if(tile.getX()==x && tile.getY()==y)itr.remove();
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
            case 8:
                itr=panel.tiles.iterator();
                while (itr.hasNext())
                {
                    Tile tile = (Tile) itr.next();
                    if(tile.getX()==x && tile.getY()==y)itr.remove();
                }
                break;
            case 9:
                itr=panel.win_blocks.iterator();
                while(itr.hasNext())
                {
                    Win_block win_block = (Win_block) itr.next();
                    if(win_block.getX()==x && win_block.getY()==y)itr.remove();
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
            if(Id==0) Id=Id_max;
            else Id--;
        }
    }
    public void change_ID(boolean up)
    {
        if(up)
        {
            if(Id==0) {
                if (Id2 >= Id2_max) Id2 = 0;
                else Id2++;
            }
            else if (Id==8)
            {
                if (Id2 >= panel.background_size-1) Id2 = 0;
                else Id2++;
            }
            else if(Id==5)
            {
                if(Id2==0)Id2=1;
                else Id2=0;
            }
        }
        else
        {
            if(Id==0) {
                if (Id2 <= 0) Id2 = Id2_max;
                else Id2--;
            }
            else if (Id==8)
            {
                if (Id2 <= 0) Id2 = panel.background_size-1;
                else Id2--;
            }
            else if (Id==5)
            {
                if(Id2==1)Id2=0;
                else Id2=1;
            }
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
        if(Id==7 || Id==5)
        {
            if(Id==5 && Id2==0)gtd.drawImage(show,x,y,null);
            else
            gtd.fillRect(x,y,width,height);
        }
        else
        gtd.drawImage(show,x,y,null);
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
                    myWriter.write(wall.getId()+"\n");
                }
                for (WallB wallB : panel.wallsB) {
                    myWriter.write(1 + "\n");
                    myWriter.write(wallB.getX() + "\n");
                    myWriter.write(wallB.getY() + "\n");
                    myWriter.write(wallB.getid()+"\n");
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
                    myWriter.write(mine.getH()+"\n");
                }
                for(Turret turret:panel.turrets)
                {
                    myWriter.write(6+"\n");
                    myWriter.write(turret.getX() + "\n");
                    myWriter.write(turret.getY() + "\n");
                }
                for(Player_spawn spawn:panel.spawns)
                {
                    myWriter.write(7+"\n");
                    myWriter.write(spawn.getX() + "\n");
                    myWriter.write(spawn.getY() + "\n");
                    myWriter.write(spawn.getN()+"\n");
                }
                for(Tile tile:panel.tiles)
                {
                    myWriter.write(8 + "\n");
                    myWriter.write(tile.getX() + "\n");
                    myWriter.write(tile.getY() + "\n");
                    myWriter.write(tile.geti()+"\n");
                }
                for(Win_block win_block:panel.win_blocks)
                {
                    myWriter.write("9"+"\n");
                    myWriter.write(win_block.getX()+"\n");
                    myWriter.write(win_block.getY()+"\n");
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
        System.out.println("Id: "+Id);

        switch (Id)
        {
            case 0:
                show=panel.wallI[Id2];
                gtd.setColor(Color.WHITE);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 1:
                show=panel.wallBI[Id2];
                gtd.setColor(Color.red);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 2:
                show=panel.grav;
                gtd.setColor(Color.blue);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 3:
                show=panel.wallJI;
               gtd.setColor(Color.green);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 4:
                show=panel.spikeI;
                gtd.setColor(Color.cyan);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 5:
                show=panel.mine_png;
                gtd.setColor(Color.red);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 6:
                show=panel.turret_png;
                gtd.setColor(Color.magenta);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 7: //spawn
                gtd.setColor(Color.gray);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 8: //background
                show=panel.tileset[Id2];
                gtd.setColor(Color.yellow);
                hitBox=new Rectangle(x,y,width,height);
                break;
            case 9://win block
                show=panel.winblockI;
                gtd.setColor(Color.ORANGE);
                hitBox=new Rectangle(x,y,width,height);
                break;
            default:
                gtd.setColor(Color.DARK_GRAY);
                hitBox=new Rectangle(x,y,width,height);
        }
    }
    void clear()
    {
        Iterator itr;
        itr = panel.walls.iterator();
        while(itr.hasNext())
        {
            Wall wall=(Wall) itr.next();
            itr.remove();
        }
        itr = panel.wallsB.iterator();
        while(itr.hasNext())
        {
            WallB wallb=(WallB) itr.next();
            itr.remove();
        }
        itr = panel.changers.iterator();
        while(itr.hasNext())
        {
            Gravity_Changer changer=(Gravity_Changer) itr.next();
            itr.remove();
        }
        itr = panel.jumps.iterator();
        while(itr.hasNext())
        {
            Wall_Jump Wjump=(Wall_Jump) itr.next();
            itr.remove();
        }
        itr=panel.spikes.iterator();
        while(itr.hasNext())
        {
            Spike spike=(Spike) itr.next();
            itr.remove();
        }
        itr=panel.mines.iterator();
        while(itr.hasNext())
        {
            Mine mine = (Mine) itr.next();
            itr.remove();
        }
        itr=panel.turrets.iterator();
        while (itr.hasNext())
        {
            Turret turret = (Turret) itr.next();
            itr.remove();
        }
        itr=panel.spawns.iterator();
        while (itr.hasNext())
        {
            Player_spawn spawn = (Player_spawn) itr.next();
            itr.remove();
        }
        itr=panel.tiles.iterator();
        while (itr.hasNext())
        {
            Tile tile = (Tile) itr.next();
            itr.remove();
        }
        itr=panel.win_blocks.iterator();
        while(itr.hasNext())
        {
            Win_block win_block = (Win_block) itr.next();
            itr.remove();
        }
        for(int i=0;i<30;i++)
        {
            for(int j=0;j<20;j++)
            {
                Taken[i][j]=-1;
            }
        }
    }
    void fill()
    {
        for(int i=0;i<30;i++)
        {
            for(int j=0;j<20;j++)
            {
                if(Taken[i][j]==-1)
                {
                    Taken[i][j]=8;
                    panel.tiles.add(new Tile(i*64,j*64,panel.tileset[Id2],Id2));
                }
            }
        }
    }
}