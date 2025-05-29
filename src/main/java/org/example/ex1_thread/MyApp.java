package org.example.ex1_thread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MyApp extends JFrame {

    TimerThread timerThread = new TimerThread();
    // componente UI
    private final JPanel zonaDesenare = new JPanel();
    private final JPanel zona2 = new JPanel();
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private final JRadioButton butonPatrat  = new JRadioButton("Patrat");
    private final JRadioButton butonCerc    = new JRadioButton("Cerc");
    private final JRadioButton butonText    = new JRadioButton("Text");
    private final JLabel labelX             = new JLabel("X:");
    private final JTextField textX          = new JTextField();
    private final JLabel labelY             = new JLabel("Y:");
    private final JTextField textY          = new JTextField();
    private final JLabel historyLabel       = new JLabel("Istoric");
    private final JTextArea historyText     = new JTextArea();
    private final JButton butonAfiseaza     = new JButton("Afiseaza");
    private final JButton butonSalveaza     = new JButton("Salveaza");
    private final JLabel emptyLabel         = new JLabel(" ");
    private final Random rand               = new Random();

    // variabila pentru masurarea timpului dintre afisari
    private long lastTime = -1;

    public MyApp() {
        setTitle("My App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 800);
        setLayout(null);

        setZonaDesenare();
        setZona2();

        add(zonaDesenare);
        add(zona2);

        timerThread.start();

        setVisible(true);
    }

    private void setZonaDesenare(){
        zonaDesenare.setLayout(null);
        Label zonaDesenareLabel = new Label("Zona Desenare");
        zonaDesenareLabel.setBounds(0, 0, 100, 30);
        add(zonaDesenareLabel);

        zonaDesenare.setBounds(0, 30, 500, 370);
        //zonaDesenare.setBorder(BorderFactory.createLineBorder(Color.BLACK)); - test only
    }

    private void setZona2(){
        zona2.setLayout(null);
        Label zona2Label = new Label("Forma:");
        zona2Label.setBounds(0, 420, 100, 30);
        add(zona2Label);

        zona2.setBounds(0, 450, 500, 350);

        butonPatrat.setBounds(0,0,100,30);
        butonCerc.setBounds(0,30,100,30);
        butonText.setBounds(0,60,100,30);
        buttonGroup.add(butonPatrat);
        buttonGroup.add(butonCerc);
        buttonGroup.add(butonText);
        zona2.add(butonPatrat);
        zona2.add(butonCerc);
        zona2.add(butonText);

        labelX.setBounds(10, 90, 100, 30);
        textX.setBounds(40, 90, 100, 30);
        zona2.add(labelX);
        zona2.add(textX);

        labelY.setBounds(10, 120, 100, 30);
        textY.setBounds(40, 120, 100, 30);
        zona2.add(labelY);
        zona2.add(textY);

        historyLabel.setBounds(10, 150, 100, 30);
        historyText.setBounds(40, 180, 420, 70);
        zona2.add(historyLabel);
        zona2.add(historyText);

        emptyLabel.setBounds(10, 260, 480, 30);
        zona2.add(emptyLabel);

        butonAfiseaza.setBounds(40, 300, 100, 30);
        butonSalveaza.setBounds(220, 300, 100, 30);
        zona2.add(butonAfiseaza);
        zona2.add(butonSalveaza);

        // listener "Afiseaza"
        butonAfiseaza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graphics g = null;
                try {
                    int x = Integer.parseInt(textX.getText().trim());
                    int y = Integer.parseInt(textY.getText().trim());

                    int w = zonaDesenare.getWidth();
                    int h = zonaDesenare.getHeight();

                    // dimensiune clasica (fara ?:)
                    int size;
                    if (butonText.isSelected()) {
                        size = 50;
                    } else {
                        size = 10 + rand.nextInt(Math.min(w, h) - 10);
                    }

                    // verifica depasire
                    if (x < 0 || y < 0 || x + size > w || y + size > h) {
                        throw new DepasireZonaDesenareException();
                    }

                    long elapsed = timerThread.getElapsed();
                    if (elapsed > 0) {
                        historyText.append("Timp de la ultima afisare: " + elapsed + " ms\n");
                    }
                    timerThread.reset();

                    // desen
                    g = zonaDesenare.getGraphics();
                    g.clearRect(0, 0, w, h);
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
                    if (g != null) g.dispose();
                }
            }
        });

        // listener "Salveaza"
        butonSalveaza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("thread_ex1.txt"))) {
                    bw.write(historyText.getText());
                    emptyLabel.setForeground(Color.GREEN);
                    emptyLabel.setText("Date scrise cu succes in fisier.");
                }
                catch (IOException ioE) {
                    emptyLabel.setForeground(Color.RED);
                    emptyLabel.setText(ioE.getMessage());
                }
            }
        });
    }




}
