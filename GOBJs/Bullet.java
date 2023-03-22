package GOBJs;

import java.awt.*;
public class Bullet extends GameObject
{
    public Bullet (int x, int y, double speed, double dir)
    {
        super(x,y,speed,dir);
    }
    public void draw (Graphics2D g)
    {
        int x = (int)this.x;
        int y = (int)this.y;
        g.setStroke(new BasicStroke(5));
        int xp[] = {x,x+5,x+5,x-5,x-5};
        int yp[] = {y-10,y-5,y+10,y+10,y-5};
        g.setColor(Color.black);
        g.drawPolygon(xp,yp,xp.length);
        g.setColor(Color.gray.darker().darker());
        g.fillPolygon(xp,yp,xp.length);
    }
}