import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
public class TankGame
{
    JFrame frame;
    public static void main (String[] args)
    {
        try
        {
            new TankGame();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
    public TankGame ()
    {
        frame = new JFrame("Tank");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel pane = new TankPanel();
        pane.setPreferredSize(new Dimension(Data.WIDTH,Data.HEIGHT));
        frame.getContentPane().add(pane,BorderLayout.CENTER);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}