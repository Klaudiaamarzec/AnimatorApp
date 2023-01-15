import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Elipsa extends Figura
{
    /**
     * @param buf
     * @param del
     * @param w
     * @param h
     */

    public Elipsa(Graphics2D buf, int del, int w, int h)
    {
        super(buf,del,w,h);
        shape = new Ellipse2D.Float(0,0,20,20);
        aft = new AffineTransform();
        area = new Area(shape);
    }
}
