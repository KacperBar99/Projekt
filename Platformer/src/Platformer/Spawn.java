package Platformer;

public class Spawn {
    int x;
    int y;
    int index;

    Spawn(int _x,int _y,int i)
    {
        x=_x;
        y=_y;
        index=i;
    }
    int getX(){return x;}
    int getY(){return y;}
    int getIndex(){return index;}
}
