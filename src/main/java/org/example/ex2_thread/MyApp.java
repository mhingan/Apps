package org.example.ex2_thread;

import javax.swing.*;        // pentru componentele UI
import java.awt.*;           // pentru desenare
import java.io.*;            // pentru scriere in fisier
import java.util.Random;     // pentru generare random

// Clasa principala care extinde JFrame (fereastra principala)
public class MyApp extends JFrame {

    TimerThread timerThread = new TimerThread(); // fir de executie pentru masurarea timpului

    // Componente UI
    JPanel zonaDeDesenare = new JPanel();
    JPanel zona2 = new JPanel();

    ButtonGroup buttonGroup = new ButtonGroup();         // grup de radio buttons
    JRadioButton radioPatrat = new JRadioButton("Patrat");
    JRadioButton radioCerc = new JRadioButton("Cerc");

    JLabel historyLabel = new JLabel("Istoric");
    JTextArea historyText = new JTextArea();             // zona de istoric

    JButton saveButton = new JButton("Salveaza");        // buton salvare
    JLabel emptyLabel = new JLabel("");                  // afiseaza mesaje/erori

    Random rand = new Random();                          // generator valori random

    private long lastTime = -1;                          // (nu e folosit in codul actual)

    // Constructorul aplicatiei
    public MyApp() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // inchide fereastra la apasarea X
        this.setTitle("My App");                              // titlu fereastra
        this.setSize(400, 600);                               // dimensiuni
        this.setLayout(null);                                 // layout liber

        desenareZona1();      // configurare zona de desen
        desenareZona2();      // configurare zona cu butoane si istoric

        timerThread.start();  // pornim firul de timp

        this.setVisible(true); // facem fereastra vizibila
    }

    // Configurarea panoului de desen
    public void desenareZona1(){
        this.add(zonaDeDesenare);
        zonaDeDesenare.setBounds(0, 0, 400, 300); // dimensiune si pozitie
        zonaDeDesenare.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // contur
    }

    // Configurarea panoului cu optiuni, istoric si salvare
    public void desenareZona2(){
        this.add(zona2);
        zona2.setLayout(null);
        zona2.setBounds(0, 300, 400, 300);
        zona2.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Eticheta
        JLabel optinuiLabel = new JLabel("Optiuni: ");
        optinuiLabel.setBounds(0, 0, 70, 20);
        zona2.add(optinuiLabel);

        // Radio button pentru cerc
        radioCerc.setBounds(75, 0, 70, 20);
        zona2.add(radioCerc);

        // Radio button pentru patrat
        radioPatrat.setBounds(150, 0, 70, 20);
        zona2.add(radioPatrat);

        buttonGroup.add(radioPatrat);  // le grupam astfel incat doar unul sa fie selectat
        buttonGroup.add(radioCerc);

        // Cand selectam "Cerc"
        radioCerc.addActionListener(e -> {
            int panelW = zonaDeDesenare.getWidth();
            int panelH = zonaDeDesenare.getHeight();

            int size = 1 + rand.nextInt(Math.min(panelW, panelH)); // dimensiune random
            int x = rand.nextInt(panelW); // pozitie X
            int y = rand.nextInt(panelH); // pozitie Y

            try {
                if (x + size > panelW || y + size > panelH) {
                    throw new DepasireZonaError("Depasire zona de desenare");
                }

                long elapsed = timerThread.getElapsed();
                if (elapsed > 0) {
                    historyText.append("Timp de la ultima afisare: " + elapsed + " ms\n");
                }
                timerThread.reset();

                Graphics g = zonaDeDesenare.getGraphics();
                g.clearRect(0, 0, panelW, panelH); // stergem zona inainte
                g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                g.drawOval(x, y, size, size);
                g.dispose();

                historyText.append(String.format("Cerc – x:%d y:%d size:%d%n", x, y, size));
                emptyLabel.setText("");

            } catch (DepasireZonaError ex) {
                emptyLabel.setText(ex.getMessage());
                emptyLabel.setForeground(Color.RED);
            }
        });

        // Cand selectam "Patrat"
        radioPatrat.addActionListener(e -> {
            int panelW = zonaDeDesenare.getWidth();
            int panelH = zonaDeDesenare.getHeight();

            int size = 1 + rand.nextInt(Math.min(panelW, panelH));
            int x = rand.nextInt(panelW);
            int y = rand.nextInt(panelH);

            try {
                if (x + size > panelW || y + size > panelH) {
                    throw new DepasireZonaError("Depasire zona de desenare");
                }

                long elapsed = timerThread.getElapsed();
                if (elapsed > 0) {
                    historyText.append("Timp de la ultima afisare: " + elapsed + " ms\n");
                }
                timerThread.reset();

                Graphics g = zonaDeDesenare.getGraphics();
                g.clearRect(0, 0, panelW, panelH);
                g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                g.drawRect(x, y, size, size);
                g.dispose();

                historyText.append(String.format("Patrat – x:%d y:%d size:%d%n", x, y, size));
                emptyLabel.setText("");

            } catch (DepasireZonaError ex) {
                emptyLabel.setText(ex.getMessage());
                emptyLabel.setForeground(Color.RED);
            }
        });

        // Istoric
        historyLabel.setBounds(10, 40, 70, 20);
        zona2.add(historyLabel);
        historyText.setBounds(10, 60, 380, 40);
        zona2.add(historyText);
        historyText.setEditable(false); // nu poate fi modificat manual

        // Buton salvare
        saveButton.setBounds(150, 110, 100, 20);
        zona2.add(saveButton);

        // Mesaj / eticheta feedback
        emptyLabel.setBounds(10, 140, 380, 60);
        zona2.add(emptyLabel);

        // Actiune salvare istoric in fisier
        saveButton.addActionListener(e -> {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("thread_2_output.txt"));
                bw.write(historyText.getText());
                bw.close();

                emptyLabel.setText("Fisier scris cu succes.");
                emptyLabel.setForeground(Color.GREEN);

            } catch (IOException exIO) {
                emptyLabel.setText(exIO.getMessage());
                emptyLabel.setForeground(Color.RED);
            }
        });
    }
}
