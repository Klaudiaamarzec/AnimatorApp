import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.Random;

/**
 * @author tb
 *
 */
public abstract class Figura implements Runnable, ActionListener/*, Shape*/
{

    // wspolny bufor
    protected Graphics2D buffer;
    protected int x,y, dlugosc, szerokosc; //NOWE ZMIENNE
    protected Area area;
    // do wykreslania
    protected Shape shape;
    // przeksztalcenie obiektu
    protected AffineTransform aft;

    // przesuniecie
    protected int dx, dy;
    // rozciaganie
    protected double sf;
    // kat obrotu
    protected double an;
    private final int delay;
    private final int width;
    private final int height;
    private final Color clr;

    protected static final Random rand = new Random();

    public Figura(Graphics2D buf, int del, int w, int h) {
        delay = del;
        buffer = buf;
        width = w;
        height = h;

        dx = 1 + rand.nextInt(10);
        dy = 1 + rand.nextInt(10);
        sf = 1 + 0.05 * rand.nextDouble();
        an = 0.3 * rand.nextDouble();

        clr = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    }

    @Override
    public void run() {
        // przesuniecie na srodek
        aft.translate(75, 75); //100 100
        area.transform(aft);
        shape = area;

        while (true) {
            // przygotowanie nastepnego kadru
            shape = nextFrame();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
            }
        }
    }

    protected Shape nextFrame()
    {
        area = new Area(area);
        aft = new AffineTransform();
        Rectangle bounds = area.getBounds();
        int cx = bounds.x + bounds.width / 2;
        int cy = bounds.y + bounds.height / 2;

        // odbicie

        if(bounds.y <= 0 || bounds.y + bounds.height >= height)
            dy=-dy;

        if(bounds.x <= 0 || bounds.x + bounds.width >= width)
            dx=-dx;

        // zwiekszenie lub zmniejszenie
        if (bounds.height > height / 3 || bounds.height < 10)
            sf = 1 / sf;
        // konstrukcja przeksztalcenia
        aft.translate(cx, cy);
        aft.scale(sf, sf);
        aft.rotate(an);
        aft.translate(-cx, -cy);
        aft.translate(dx, dy);
        // przeksztalcenie obiektu
        area.transform(aft);
        x=cx;
        y=cy;
        szerokosc = bounds.width;
        dlugosc= bounds.height;

        return area;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        // wypelnienie obiektu
        buffer.setColor(clr.brighter());
        buffer.fill(shape);
        // wykreslenie ramki
        buffer.setColor(clr.darker());
        buffer.draw(shape);
    }

}
