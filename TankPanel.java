import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import GOBJs.*;
public class TankPanel extends JPanel implements KeyListener, MouseMotionListener, MouseListener
{
    double x, y, dir, tdir, speed;
    boolean moving, fire, gfire, gfireable, firing;
    final int DRAG;
    ArrayList<GameObject> ent;
    Thread game, traction;
    boolean le, ri, dow, up;
    int mx, my;
    int fireable;
    //TP0
    public TankPanel ()
    {
        fireable = 0;
        moving = fire = false;
        le = ri = dow = up = false;
        DRAG = 3;
        x = y = 250;
        dir = tdir = speed = 0;
        setFocusable(true);
        addKeyListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        ent = new ArrayList<GameObject>(0);
        game = new Thread()
        {
            public void run () 
            {
                while (true)
                {
                    try
                    {
                        Thread.sleep(10);
                    } catch (Exception e) {}
                    repaint();
                }
            }
        };
        game.start();
        traction = new Thread()
        {
            public void run ()
            {
                while (true)
                {
                    try
                    {
                        Thread.sleep(75);
                    } catch (Exception e) {}
                    if (!moving)
                    {
                        if (speed > 0) speed /= DRAG;
                        if (speed < 0) speed /= DRAG;
                    }
                }
            }
        };
        traction.start();
    }
    //TP1
    public void keyTyped (KeyEvent k) {}
    public void keyPressed (KeyEvent k)
    {
        if (k.getKeyCode() == 65) le = true;//dir -= speed * 100;
        if (k.getKeyCode() == 68) ri = true;//dir += speed * 100;
        if (k.getKeyCode() == 87) up = true;
        if (k.getKeyCode() == 83) dow = true;
    }
    public void keyReleased (KeyEvent k)
    {
        if (k.getKeyCode() == 65) le = false;//dir -= speed * 100;
        if (k.getKeyCode() == 68) ri = false;//dir += speed * 100;
        if (k.getKeyCode() == 87) up = false;
        if (k.getKeyCode() == 83) dow = false;
    }
    public void mouseMoved (MouseEvent m)
    {
        mx = m.getX();
        my = m.getY();
    }
    public void mouseDragged (MouseEvent m)
    {
        mx = m.getX();
        my = m.getY();
    }
    public void mouseEntered (MouseEvent m) {}
    public void mouseExited (MouseEvent m) {}
    public void mouseClicked (MouseEvent m) {}
    public void mousePressed (MouseEvent m)
    {
        int c = m.getButton();
        if (fireable == 0 && c == MouseEvent.BUTTON1)
        {
            fire = true;
            fireable = 2000;
            new Thread()
            {
                public void run ()
                {
                    for (int i = 0; i < 2000; i++)
                    {
                        try
                        {
                            Thread.sleep(1);
                        } catch (Exception e) {}
                        fireable--;
                    }
                }
            }.start();
        }
        if (c == MouseEvent.BUTTON3)
        {
            firing = true;
            gfireable = true;
            firing = true;
            new Thread ()
            {
                public void run ()
                {
                    while (firing)
                    {
                        gfire = !gfire;
                        try
                        {
                            Thread.sleep(50);
                        } catch (Exception e) {}
                    }
                    gfire = false;
                }
            }.start();
        }
    }
    public void mouseReleased (MouseEvent m)
    {
        int c = m.getButton();
        if (c == MouseEvent.BUTTON3) firing = false;
    }
    //TP2
    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        g.setColor(new Color(175,140,66));
        g.fillRect(0,0,Data.WIDTH,Data.HEIGHT);
        Graphics2D g2d = (Graphics2D)g;
        draw(g2d);
        calc();
    }
    //TP3
    public void draw (Graphics2D g)
    {
        double rad = Math.toRadians(dir);
        double trad = Math.toRadians(tdir);
        int x = (int)this.x, y = (int)this.y;
        AffineTransform old = g.getTransform();
        g.rotate(rad,x,y);
        g.setColor(Color.green);
        g.fillPolygon(new int[]{x-20,x,x+20}, new int[]{y-60,y-70,y-60},3);
        g.setColor(Color.gray.darker().darker());
        g.fillRect(x-40,y-50,30,100);
        g.fillRect(x+10,y-50,30,100);
        g.setColor(Color.black);
        g.fillRect(x-32,y-42,64,84);
        g.setColor(Color.green.darker());
        g.fillRect(x-30,y-40,60,80);
        g.setColor(Color.black);
        g.fillOval(x-27,y-27,54,54);
        g.setColor(Color.green.darker());
        g.fillOval(x-25,y-25,50,50);
        g.setTransform(old);
        g.rotate(trad,x,y);
        g.setColor(Color.black);
        g.fillRect(x-7,y-77,14,54);
        g.setColor(Color.green.darker());
        g.fillRect(x-5,y-75,10,50);
        g.setColor(Color.black);
        g.fillRect(x+14,y-27,14,26);
        g.setColor(Color.gray.darker());
        g.fillRect(x+16,y-25,10,25);
        g.setColor(Color.yellow.darker());
        g.fillRect(x+16,y,10,25);
        g.rotate(Math.toRadians(-45),x+21,y+25);
        if (gfire)
        {
            g.setColor(new Color(250,125,0));
            g.fillOval(x+42,y-27,32,32);
        }
        g.setTransform(old);
        for (int i = 0; i < ent.size(); i++)
        {
            GameObject go = ent.get(i);
            g.rotate(Math.toRadians(go.dir+90),go.x,go.y);
            go.draw(g);
            go.move(true);
            g.setTransform(old);
            if (go.outOfBounds()) ent.remove(go);
        }
        g.setColor(Color.green.darker());
        g.fillArc(10,10,50,50,90,(int)((2000.0-fireable)/2000*360));
    }
    //TP4
    public void calc ()
    {
        double rad = Math.toRadians(dir);
        double trad = Math.toRadians(tdir);
        tdir = Math.toDegrees(Math.atan2((my - y),(mx - x))) + 90;
        this.x += Math.cos(rad - Math.PI/2)*speed;
        this.y += Math.sin(rad - Math.PI/2)*speed;
        if (x < 0) this.x = 0;
        if (x > 500) this.x = 500;
        if (y < 0) this.y = 0;
        if (y > 500) this.y = 500;
        if (up)
        {
            moving = true;
            speed += 0.2;
            if (speed > 1) speed = 1;
        }
        if (dow)
        {
            moving = true;
            speed -= 0.2;
            if (speed < -1) speed = -1;
        }
        if (ri)
        {
            dir += speed;
        }
        if (le)
        {
            dir -= speed;
        }
        if (speed < 0.0001 && speed > 0 || speed > -0.0001 && speed < 0) speed = 0;
        if (dir > 360) dir = 0;
        if (dir < 0) dir = 360;
        moving = false;
        if (fire)
        {
            double bsx = Math.cos(Math.toRadians(tdir-90))*75+x;
            double bsy = Math.sin(Math.toRadians(tdir-90))*75+y;
            ent.add(new Bullet((int)bsx,(int)bsy,5,tdir));
            fire = false;
        }
    }
}