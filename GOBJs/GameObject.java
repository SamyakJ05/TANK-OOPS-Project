package GOBJs;

import java.awt.*;
public class GameObject
{
    public double x, y, speed, dir;
    public GameObject (int x, int y, double speed, double dir)
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.dir = dir-90;
    }
    public void turn (int dir)
    {
        this.dir += dir;
    }
    public void move (boolean ud)
    {
        double xdist = Math.cos(Math.toRadians(dir))*speed, ydist = Math.sin(Math.toRadians(dir))*speed;
        if (ud)
        {
            x += xdist;
            y += ydist;
        }
        else
        {
            x -= xdist;
            y -= ydist;
        }
    }
    public void draw (Graphics2D g)
    {
        int x = (int)this.x;
        int y = (int)this.y;
        g.setColor(Color.black);
        g.fillOval(x-10,y-10,20,20);
    }
    public boolean outOfBounds ()
    {
        if (x > Data.WIDTH || y > Data.HEIGHT || x < 0 || y < 0) return true;
        else return false;
    }
}