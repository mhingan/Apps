package org.example.ex1_thread;

import javax.swing.*;        // pentru componentele UI
import java.awt.*;           // pentru desenare si layout
import java.awt.event.*;     // pentru evenimente
import java.io.*;            // pentru scriere in fisier
import java.util.Random;     // pentru generare valori random

// Clasa principala care extinde JFrame - fereastra aplicatiei
public class MyApp extends JFrame {

    // Firul de executie pentru masurarea timpului
    TimerThread timerThread = new TimerThread();

    // Componentele UI
    private final JPanel zonaDesenare = new JPanel();   // zona unde se deseneaza
    private final JPanel zona2 = new JPanel();          // zona cu butoane si input
    private final ButtonGroup buttonGroup = new ButtonGroup(); // grup radio buttons
    private final JRadioButton butonPatrat  = new JRadioButton("Patrat");
    private final JRadioButton butonCerc    = new JRadioButton("Cerc");
    private final JRadioButton butonText    = new JRadioButton("Text");
    private final JLabel labelX             = new JLabel("X:");
    private final JTextField textX          = new JTextField();   // input X
    private final JLabel labelY             = new JLabel("Y:");
    private final JTextField textY          = new JTextField();   // input Y
    private final JLabel historyLabel       = new JLabel("Istoric");
    private final JTextArea historyText     = new JTextArea();    // zona cu istoric
    private final JButton butonAfiseaza     = new JButton("Afiseaza");
    private final JButton butonSalveaza     = new JButton("Salveaza");
    private final JLabel emptyLabel         = new JLabel(" ");    // afisare erori / mesaje
    private final Random rand               = new Random();       // generator random

    // Variabila pentru a tine ultima valoare de timp (optional)
    private long lastTime = -1;

    // Constructorul aplicatiei
    public MyApp() {
        setTitle("My App");                            // titlul ferestrei
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // inchide aplicatia cand apesi X
        setSize(500, 800);                             // dimensiuni fereastra
        setLayout(null);                               // layout absolut (cu coordonate)

        setZonaDesenare();                             // configurare zona de desenare
        setZona2();                                    // configurare zona de control

        add(zonaDesenare);                             // adaugam in fereastra
        add(zona2);

        timerThread.start();                           // pornim cronometrul

        setVisible(true);                              // facem fereastra vizibila
    }

    // Configurare zona de desenare
    private void setZonaDesenare(){
        zonaDesenare.setLayout(null);                  // layout liber
        Label zonaDesenareLabel = new Label("Zona Desenare");
        zonaDesenareLabel.setBounds(0, 0, 100, 30);
        add(zonaDesenareLabel);
        zonaDesenare.setBounds(0, 30, 500, 370);        // pozitie si dimensiune
    }

    // Configurare zona de control (butoane, input, istoric, etc.)
    private void setZona2(){
        zona2.setLayout(null);
        Label zona2Label = new Label("Forma:");
        zona2Label.setBounds(0, 420, 100, 30);
        add(zona2Label);
        zona2.setBounds(0, 450, 500, 350);

        // Adaugam butoanele radio in zona2
        butonPatrat.setBounds(0,0,100,30);
        butonCerc.setBounds(0,30,100,30);
        butonText.setBounds(0,60,100,30);
        buttonGroup.add(butonPatrat);  // doar unul poate fi selectat
        buttonGroup.add(butonCerc);
        buttonGroup.add(butonText);
        zona2.add(butonPatrat);
        zona2.add(butonCerc);
        zona2.add(butonText);

        // Campuri pentru X si Y
        labelX.setBounds(10, 90, 100, 30);
        textX.setBounds(40, 90, 100, 30);
        zona2.add(labelX);
        zona2.add(textX);

        labelY.setBounds(10, 120, 100, 30);
        textY.setBounds(40, 120, 100, 30);
        zona2.add(labelY);
        zona2.add(textY);

        // Eticheta si zona de text pentru istoric
        historyLabel.setBounds(10, 150, 100, 30);
        historyText.setBounds(40, 180, 420, 70);
        zona2.add(historyLabel);
        zona2.add(historyText);

        // Eticheta pentru mesaje
        emptyLabel.setBounds(10, 260, 480, 30);
        zona2.add(emptyLabel);

        // Butoane de afisare si salvare
        butonAfiseaza.setBounds(40, 300, 100, 30);
        butonSalveaza.setBounds(220, 300, 100, 30);
        zona2.add(butonAfiseaza);
        zona2.add(butonSalveaza);

        // Eveniment pentru butonul "Afiseaza"
        butonAfiseaza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graphics g = null;
                try {
                    int x = Integer.parseInt(textX.getText().trim()); // citim X
                    int y = Integer.parseInt(textY.getText().trim()); // citim Y

                    int w = zonaDesenare.getWidth();
                    int h = zonaDesenare.getHeight();

                    // dimensiunea formei
                    int size;
                    if (butonText.isSelected()) {
                        size = 50; // font fix pentru text
                    } else {
                        size = 10 + rand.nextInt(Math.min(w, h) - 10); // random
                    }

                    // verificam daca forma iese din zona de desen
                    if (x < 0 || y < 0 || x + size > w || y + size > h) {
                        throw new DepasireZonaDesenareException(); // aruncam exceptie personalizata
                    }

                    // afisam timpul scurs de la ultima apasare
                    long elapsed = timerThread.getElapsed();
                    if (elapsed > 0) {
                        historyText.append("Timp de la ultima afisare: " + elapsed + " ms\n");
                    }
                    timerThread.reset(); // resetam timerul

                    // desenam forma
                    g = zonaDesenare.getGraphics();
                    g.clearRect(0, 0, w, h); // curatam zona

                    if (butonPatrat.isSelected()) {
                        g.drawRect(x, y, size, size);
                        historyText.append("Patrat - X:" + x + " Y:" + y + " size:" + size + "\n");
                    }
                    else if (butonCerc.isSelected()) {
                        g.drawOval(x, y, size, size);
                        historyText.append("Cerc - X:" + x + " Y:" + y + " size:" + size + "\n");
                    }
                    else if (butonText.isSelected()) {
                        g.setFont(new Font("Arial", Font.BOLD, size));
                        g.drawString("Salut!", x, y + size);
                        historyText.append("Salut! - X:" + x + " Y:" + y + " size:" + size + "\n");
                    }
                }
                catch (NumberFormatException ex) {
                    emptyLabel.setForeground(Color.RED);
                    emptyLabel.setText("Eroare convertire: introduceti doar numere intregi.");
                }
                catch (DepasireZonaDesenareException ex) {
                    emptyLabel.setForeground(Color.RED);
                    emptyLabel.setText(ex.getMessage());
                }
                finally {
                    if (g != null) g.dispose(); // eliberam resursa grafica
                }
            }
        });

        // Eveniment pentru butonul "Salveaza"
        butonSalveaza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("thread_ex1.txt"))) {
                    bw.write(historyText.getText()); // scriem istoric in fisier
                    emptyLabel.setForeground(Color.GREEN);
                    emptyLabel.setText("Date scrise cu succes in fisier.");
                }
                catch (IOException ioE) {
                    emptyLabel.setForeground(Color.RED);
                    emptyLabel.setText(ioE.getMessage()); // afisam eroarea
                }
            }
        });
    }
}
