import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.Serial;

public class AnimatorApp extends JFrame
{

    /**
     *
     */
    ImageIcon play = new ImageIcon("play.png");
    JButton btnAnimate = new JButton(play);
    JButton btnRandom = new JButton("RANDOM");
    JButton btnEl = new JButton("ELIPSA");
    JButton btnKw = new JButton("KWADRAT");
    JMenuBar menu = new JMenuBar();
    JMenu mnChoose = new JMenu("CHOOSE");
    JMenuItem mnYou = new JMenuItem("You decide", 'Y');
    JMenuItem mnAut = new JMenuItem("Random", 'R');
    AnimPanel kanwa = new AnimPanel();
    JPanel buttonPanel = new JPanel();

    JToolBar pasek = new JToolBar();

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                final AnimatorApp frame = new AnimatorApp();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void youDecide()
    {
        buttonPanel.remove(btnRandom);
        buttonPanel.add(btnKw);
        buttonPanel.add(btnEl);
        repaint();
        setVisible(true);
    }

    public void random()
    {
        buttonPanel.remove(btnEl);
        buttonPanel.remove(btnKw);
        Dimension sizeRandom = new Dimension(110,23);
        btnRandom.setPreferredSize(sizeRandom);
        buttonPanel.add(btnRandom);
        repaint();
        setVisible(true);
    }

    /**
     * Create the frame
     */

    public AnimatorApp()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int ww = 600, wh = 460;
        setBounds((screen.width-ww)/2, (screen.height-wh)/2, ww, wh);
        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBounds(0, 350,getWidth(),getHeight());
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setJMenuBar(menu);
        menu.add(mnChoose);
        mnYou.addActionListener(e -> youDecide());

        pasek.add(btnAnimate);
        buttonPanel.add(pasek);

        mnYou.setAccelerator(KeyStroke.getKeyStroke("Y"));

        //Jak jest zatrzymana animacja i bedziemy chcieli zmienic opcje z menu to animacja znika - zostaje tylko biale tlo,
        // do momentu ponownego uruchomienia animacji !!!!!!!!!!!!!!!!

        mnAut.addActionListener(e -> {
            random();
            requestFocusInWindow();
        });

        mnAut.setAccelerator(KeyStroke.getKeyStroke("R"));

        mnChoose.add(mnYou);
        mnChoose.add(mnAut);

        contentPane.add(kanwa);

        kanwa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                //Kliknięcie na figure powoduje jej uziemienie
                System.out.println("Słucha");

                for(Figura o : AnimPanel.lista)
                {
                    if((e.getX() >= o.x - o.szerokosc/2) && (e.getX() <= o.x + o.szerokosc/2) && (e.getY() >= o.y - o.dlugosc/2) && (e.getY() <= o.y + o.dlugosc/2))
                    {
                        o.dx=0;
                        o.dy=0;
                        o.sf=1;
                        o.an=0;
                    }
                }
            }
        });

        SwingUtilities.invokeLater(() -> kanwa.initialize());

        btnRandom.addActionListener(e -> kanwa.addFig());

        btnKw.addActionListener(e -> kanwa.addKwadrat());

        btnEl.addActionListener(e ->  kanwa.addElipsa()
        );

        btnAnimate.addActionListener(e -> kanwa.animate());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                SwingUtilities.invokeLater(() -> kanwa.initialize());
            }
        });

    }
}
