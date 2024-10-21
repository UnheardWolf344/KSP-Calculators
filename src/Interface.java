import javax.swing.*;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface extends JFrame implements ActionListener {
    JMenuItem Kerbol;
    JMenuItem[] starArr;

    JMenuItem Moho;
    JMenuItem Eve;
    JMenuItem Kerbin;
    JMenuItem Duna;
    JMenuItem Dres;
    JMenuItem Jool;
    JMenuItem Sarnus;
    JMenuItem[] planetArr;

    JMenuItem Gilly;
    JMenuItem Mun;
    JMenuItem Minmus;
    JMenuItem Ike;
    JMenuItem Laythe;
    JMenuItem Vall;
    JMenuItem Tylo;
    JMenuItem Bop;
    JMenuItem Pol;
    JMenuItem[] moonArr;

    JPanel panel;
    JMenuItem[] bodies;
    JMenuBar menuBar;
    JTextPane bodySelection;
    JButton newHohmannTransfer;

    JTextField periapsisTF;
    JTextField apoapsisTF;
    JTextField TapoapsisTF;
    JTextField TperiapsisTF;
    JLabel apoapsisL;
    JLabel periapsisL;
    JLabel TperiapsisL;
    JLabel TapoapsisL;

    Caret blank;

    // visviva params
    String body;
    double ap, pe, tap, tpe;

    Interface() {
        super("Hohmann Calculator");
        setSize(500, 400);
        setResizable(false);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        blank = new DefaultCaret() {

            @Override
            public void paint(Graphics g) {
            }

            @Override
            public boolean isVisible() {
                return false;
            }

            @Override
            public boolean isSelectionVisible() {
                return false;
            }
        };

        menu();
        instructions();
    }

    void menu() {
        menuBar = new JMenuBar();
        menuBar.setToolTipText("Pick the center gravitational body!");

        JMenu stars = new JMenu("Stars");
        JMenu planets = new JMenu("Planets");
        JMenu moons = new JMenu("Moons");

        {
            Kerbol = new JMenuItem("Kerbol");
            starArr = new JMenuItem[]{Kerbol};
            for (JMenuItem i : starArr) {
                stars.add(i);
                i.addActionListener(this);
            }
        }

        {
            Moho = new JMenuItem("Moho");
            Eve = new JMenuItem("Eve");
            Kerbin = new JMenuItem("Kerbin");
            Duna = new JMenuItem("Duna");
            Dres = new JMenuItem("Dres");
            Jool = new JMenuItem("Jool");
            Sarnus = new JMenuItem("Sarnus");
            planetArr = new JMenuItem[]{Moho, Eve, Kerbin, Duna, Dres, Jool, Sarnus};
            for (JMenuItem i : planetArr) {
                planets.add(i);
                i.addActionListener(this);
            }
        }

        {
            Gilly = new JMenuItem("Gilly");
            Mun = new JMenuItem("Mun");
            Minmus = new JMenuItem("Minmus");
            Ike = new JMenuItem("Ike");
            Laythe = new JMenuItem("Laythe");
            Vall = new JMenuItem("Vall");
            Tylo = new JMenuItem("Tylo");
            Bop = new JMenuItem("Bop");
            Pol = new JMenuItem("Pol");
            moonArr = new JMenuItem[]{Gilly, Mun, Minmus, Ike, Laythe, Vall, Tylo, Bop, Pol};
            for (JMenuItem i : moonArr) {
                moons.add(i);
                i.addActionListener(this);
            }
        }

        bodies = new JMenuItem[starArr.length + planetArr.length + moonArr.length];
        int i = 0;
        for (JMenuItem jMenuItem : starArr) {
            bodies[i] = jMenuItem;
            i++;
        }
        for (JMenuItem jMenuItem : planetArr) {
            bodies[i] = jMenuItem;
            i++;
        }
        for (JMenuItem jMenuItem : moonArr) {
            bodies[i] = jMenuItem;
            i++;
        }

        menuBar.add(planets);
        menuBar.add(moons);
        menuBar.add(stars);
        revalidate();
    }

    void newHohmannTransfer () {
        bodySelection.setText("Choose a body!");
        setJMenuBar(menuBar);
        body = "";
        revalidate();
        ungetParams();
    }

    void bodySelected (String selectedBody) {
        setJMenuBar(null);
        bodySelection.setText(selectedBody);
        body = selectedBody;
        revalidate();
        getParams();
    }

    void instructions () {
        panel = new JPanel(new GridBagLayout());
        bodySelection = new JTextPane();
        bodySelection.setFont(new Font("Roboto", Font.BOLD, 24));
        bodySelection.setMargin(new Insets(5, 5, 5, 5));
        bodySelection.setCaret(blank);
        bodySelection.setEditable(false);
        bodySelection.setBackground(panel.getBackground());
        bodySelection.setBounds(50, 50, 50, 50);

        newHohmannTransfer = new JButton("New Hohmann Transfer");
        newHohmannTransfer.addActionListener(this);

//        bodySelection.setPreferredSize(new Dimension(200, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor= GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(bodySelection ,     gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.ABOVE_BASELINE;
        panel.add(newHohmannTransfer, gbc);
        add(panel);
        newHohmannTransfer();
    }

    void getParams () {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor  = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx   = 0;
        gbc.gridy   = 2;
        apoapsisL   = new JLabel("Enter your current orbit's apoapsis here: ");
        apoapsisTF  = new JTextField();
        periapsisTF = new JTextField();



        panel.add(apoapsisL, gbc);

        gbc.gridx = 1;
        apoapsisTF.setEditable(true);
        apoapsisTF.setSize(20, 20);

        panel.add(apoapsisTF, gbc);
        revalidate();
    }

    void ungetParams () {
        JComponent[] paramgetters = {apoapsisL, periapsisL, TapoapsisL, TperiapsisL, apoapsisTF, periapsisTF, TapoapsisTF, TperiapsisTF};
        for (JComponent c : paramgetters) {
            if (c != null)
                panel.remove(c);
        }
        revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean exit = false;
        while (!exit) {
            for (JMenuItem i : bodies) {
                if(e.getActionCommand().equals(i.getText())) {
                    System.out.println(i.getText() + " was tapped");
                    bodySelected(i.getText());
                    exit = true;
                    break;
                }
            }

            if (e.getActionCommand().equals("New Hohmann Transfer")) {
                System.out.println("New Hohmann Transfer" + " was tapped");
                exit = true;
                newHohmannTransfer();
            }
        }
    }
}
