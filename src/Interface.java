import javax.swing.*;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Interface extends JFrame {
    //<editor-fold desc="public variables">
    JMenuItem Kerbol;
    JMenuItem[] starArr;
    int numMenus;

    JMenuItem Moho;
    JMenuItem Eve;
    JMenuItem Kerbin;
    JMenuItem Duna;
    JMenuItem Dres;
    JMenuItem Jool;
    JMenuItem Eeloo;
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
    JButton calculate;

    JTextField burn1;
    JTextField burn2;
    JTextField totdv;

    JLabel Lburn1;
    JLabel Lburn2;
    JLabel Ltotdv;
    //</editor-fold>

    Caret blank = new DefaultCaret() {

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

    Interface() {
        super("Hohmann Transfer Calculator");
        setSize(500, 400);
        setResizable(false);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initOrbitalParams();
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
                i.addActionListener(mItemSelect);
            }
        }

        {
            Moho = new JMenuItem("Moho");
            Eve = new JMenuItem("Eve");
            Kerbin = new JMenuItem("Kerbin");
            Duna = new JMenuItem("Duna");
            Dres = new JMenuItem("Dres");
            Jool = new JMenuItem("Jool");
            Eeloo = new JMenuItem("Eeloo");
            planetArr = new JMenuItem[]{Moho, Eve, Kerbin, Duna, Dres, Jool, Eeloo};
            for (JMenuItem i : planetArr) {
                planets.add(i);
                i.addActionListener(mItemSelect);
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
                i.addActionListener(mItemSelect);
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
        numMenus = menuBar.getMenuCount();

        revalidate();
    }

    void newHohmannTransfer () {
        bodySelection.setText("Choose a body!");
        setJMenuBar(menuBar);
        revalidate();
        ungetOrbitalParams();
    }

    void bodySelected (String selectedBody) {
        setJMenuBar(null);
        bodySelection.setText(selectedBody);
        revalidate();
        getOrbitalParams();
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
        newHohmannTransfer.addActionListener(buttonPress);
        newHohmannTransfer.setFocusPainted(false); //makes the dotted square not appear

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor= GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = -1;
        panel.add(bodySelection ,     gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.ABOVE_BASELINE;
        panel.add(newHohmannTransfer, gbc);
        add(panel);
        newHohmannTransfer();
    }

    void initOrbitalParams() {
        // current apoapsis
        apoapsisL = new JLabel("Current orbit's apoapsis: ");

        apoapsisTF = new JTextField();
        apoapsisTF.setToolTipText("Apoapsis");
        apoapsisTF.setEditable(true);
        apoapsisTF.addActionListener(orbitValueInput);


        //current periapsis
        periapsisL = new JLabel("Current orbit's periapsis: ");

        periapsisTF = new JTextField();
        periapsisTF.setToolTipText("Periapsis");
        periapsisTF.setEditable(true);
        periapsisTF.addActionListener(orbitValueInput);


        //target apoapsis
        TapoapsisL = new JLabel("  Target orbit's apoapsis: ");

        TapoapsisTF = new JTextField();
        TapoapsisTF.setToolTipText("Target Apoapsis");
        TapoapsisTF.setEditable(true);
        TapoapsisTF.addActionListener(orbitValueInput);


        // target periapsis
        TperiapsisL = new JLabel("  Target orbit's periapsis: ");

        TperiapsisTF = new JTextField();
        TperiapsisTF.setToolTipText("Target Periapsis");
        TperiapsisTF.setEditable(true);
        TperiapsisTF.addActionListener(orbitValueInput);

        calculate = new JButton("Calculate!");
    }

    void getOrbitalParams() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;


        // current apoapsis
        gbc.anchor  = GridBagConstraints.ABOVE_BASELINE;
        gbc.gridx   = 0;
        gbc.gridy   = 4;
        panel.add(apoapsisL, gbc);

        gbc.gridx = 1;
        panel.add(apoapsisTF, gbc);

        // current periapsis
        gbc.gridx   = 0;
        gbc.gridy   = 5;
        panel.add(periapsisL, gbc);

        gbc.gridx = 1;
        panel.add(periapsisTF, gbc);

        // target apoapsis
        gbc.gridx   = 0;
        gbc.gridy   = 8;
        panel.add(TapoapsisL, gbc);

        gbc.gridx = 1;
        panel.add(TapoapsisTF, gbc);

        // target periapsis
        gbc.gridx   = 0;
        gbc.gridy   = 9;
        panel.add(TperiapsisL, gbc);

        gbc.gridx = 1;
        panel.add(TperiapsisTF, gbc);

        //button
        calculate.setFocusPainted(false);
        calculate.addActionListener(buttonPress);
        gbc.gridx= 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.ABOVE_BASELINE;
        panel.add(calculate, gbc);

        revalidate();
    }

    void ungetOrbitalParams() {
        JComponent[] Lparamgetters = {apoapsisL, periapsisL, TapoapsisL, TperiapsisL, Lburn1, Lburn2, Ltotdv, burn1, burn2, totdv};
        JTextField[] TFparamgetters = {apoapsisTF, periapsisTF, TapoapsisTF, TperiapsisTF};

        for (JComponent c : Lparamgetters) {
            if (c != null) {
                panel.remove(c);
            }
        }

        if (calculate.isVisible())
            panel.remove(calculate);

        for (JTextField tf : TFparamgetters) {
            if (tf != null) {
                tf.setText("");
                panel.remove(tf);
            }
        }

        revalidate();
    }

    void calculate(String body, long ap, long pe, long tap, long tpe) {
        double[] ans = HohmannTransfer.visviva(body, ap, pe, tap, tpe);

        DecimalFormat df = new DecimalFormat(("#." + String.format("%0" + 3 + "d", 0).replace("0", "#")));

        burn1 = new JTextField(df.format(ans[0]) + "m/s");
        burn2 = new JTextField(df.format(ans[1]) + "m/s");
        totdv = new JTextField(df.format(ans[2]) + "m/s");

        burn1.setBorder(null);
        burn1.setCaret(blank);
        burn1.setEditable(false);

        burn2.setBorder(null);
        burn2.setCaret(blank);
        burn2.setEditable(false);

        totdv.setBorder(null);
        totdv.setCaret(blank);
        totdv.setEditable(false);

        Lburn1 = new JLabel("delta-v of the first burn: ");
        Lburn2 = new JLabel("delta-v of the second burn: ");
        Ltotdv = new JLabel("Total delta-v of the maneuver: ");
        Lburn1.setHorizontalAlignment(SwingConstants.RIGHT);
        Lburn2.setHorizontalAlignment(SwingConstants.RIGHT);
        Ltotdv.setHorizontalAlignment(SwingConstants.RIGHT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.5;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;

        gbc.gridx = 0;


        gbc.gridy = 15;
        panel.add(Lburn1, gbc);

        gbc.gridy = 16;
        panel.add(Lburn2, gbc);

        gbc.gridy = 17;
        panel.add(Ltotdv, gbc);

        // --------- nums ---------

        gbc.gridx = 1;

        gbc.gridy = 15;
        panel.add(burn1, gbc);

        gbc.gridy = 16;
        panel.add(burn2, gbc);

        gbc.gridy = 17;
        panel.add(totdv, gbc);

        revalidate();

    }

    //<editor-fold desc="actions">

    public Action mItemSelect = new AbstractAction() {
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
            }
        }
    };

    public Action buttonPress = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("New Hohmann Transfer")) {
                System.out.println("New Hohmann Transfer was tapped");
                newHohmannTransfer();
            }

            if (e.getActionCommand().equals("Calculate!")) {
                System.out.println("calculating");
                ArrayList<String> exal = new ArrayList<>();
                String body = bodySelection.getText();
                long pe = 0, ap = 0, tap = 0, tpe = 0;
                try {
                    pe = Long.parseLong(periapsisTF.getText());
                } catch (NumberFormatException exc) {
                    exal.add("pe");
                }
                try {
                    ap = Long.parseLong(apoapsisTF.getText());
                } catch (NumberFormatException exc) {
                    exal.add("ap");
                }
                try {
                    tap = Long.parseLong(TapoapsisTF.getText());
                } catch (NumberFormatException exc) {
                    exal.add("tap");
                }
                try {
                    tpe = Long.parseLong(TperiapsisTF.getText());
                } catch (NumberFormatException exc) {
                    exal.add("tpe");
                }
                if (!exal.isEmpty()) {
                    StringBuilder sb = new StringBuilder("The following inputs are not numbers: ");
                    for (String s : exal) {
                        sb.append(s).append(tpe);
                    }
                    System.out.println(sb);
                } else if (pe > ap || tpe > tap) {
                    System.out.println("The periapsides need to be lower than the apoapsides!");
                } else {
                    calculate(body, ap, pe, tap, tpe);
                }
            }
        }
    };

    public Action orbitValueInput = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.print("next value");
        }
    };

    //</editor-fold>
}
