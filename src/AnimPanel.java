import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class AnimPanel extends JPanel implements ActionListener {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    // bufor
    Image image;
    // wykreslacz ekranowy
    Graphics2D device;
    // wykreslacz bufora
    Graphics2D buffer;
    //Odpowiada za prędkość obiektów
    private int delay;

    Timer timer;
    protected Random rand = new Random();
    public static ArrayList<Figura> lista = new ArrayList<>();

    void addKwadrat()
    {
        delay = rand.nextInt(51); //Kwadraty z reguły będą szybsze od elips
        Figura fig = new Kwadrat(buffer, delay, getWidth(), getHeight());
        lista.add(fig);
        timer.addActionListener(fig);
        new Thread(fig).start();
    }

    void addElipsa()
    {
        delay = rand.nextInt(101);
        Figura fig = new Elipsa(buffer, delay, getWidth(), getHeight());
        lista.add(fig);
        timer.addActionListener(fig);
        new Thread(fig).start();
    }

    public AnimPanel()
    {
        super();
        delay = rand.nextInt(101);
        setBackground(Color.WHITE);
        timer = new Timer(delay, this);
    }

    public void initialize()
    {
        int width = getWidth();
        int height = getHeight();

        image = createImage(width, height);
        buffer = (Graphics2D) image.getGraphics();
        buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        device = (Graphics2D) getGraphics();
        device.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    void addFig()
    {
        int numer = rand.nextInt();
        delay = rand.nextInt(101);
        Figura fig = (numer % 2 == 0) ? new Kwadrat(buffer, delay, getWidth(), getHeight())
                : new Elipsa(buffer, delay, getWidth(), getHeight());
        lista.add(fig);
        timer.addActionListener(fig);
        new Thread(fig).start();
    }

    //Funkcja sprawdzająca kolizje pomiędzy obiektami

    /*void checkCollision()
    {
        for(Figura i : lista)
        {
            for(Figura j : lista)
            {
                Rectangle bounds = i.area.getBounds();
                Rectangle bounds2 = j.area.getBounds();

                int cx = bounds.x + bounds.width / 2;
                int cy = bounds.y + bounds.height / 2;

                int cx2 = bounds2.x + bounds2.width / 2;
                int cy2 = bounds2.y + bounds2.height / 2;

                if(cx + bounds.width/2 == cx2 - bounds2.width/2)
                {
                    //Zderzenie z prawej strony
                    i.dx = -i.dx;
                    j.dx=-j.dx;

                }

                if(cx2 + bounds2.width/2 == cx - bounds.width/2)
                {
                    //Zderzenie z lewej strony
                    i.dx = -i.dx;
                    j.dx=-j.dx;
                }

                if(cy + bounds.height/2 == cy2 - bounds2.height/2)
                {
                    //Zderzenie od góry
                    i.dy = -i.dy;
                    j.dy = -j.dy;
                }

                if(cy2 + bounds2.height/2 == cy - bounds.height/2)
                {
                    //Zderzenie od dołu
                    i.dy = -i.dy;
                    j.dy = -j.dy;
                }
            }
        }
    }*/

    void animate()
    {
        if(timer.isRunning())
            timer.stop();
        else
            timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        device.drawImage(image, 0, 0, null);
        buffer.clearRect(0, 0, getWidth(), getHeight());
    }
}
