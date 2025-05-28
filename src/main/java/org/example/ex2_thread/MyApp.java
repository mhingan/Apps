package org.example.ex2_thread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MyApp extends JFrame {

    //componentele UI
    JPanel zonaDeDesenare = new JPanel();
    JPanel zona2 = new JPanel();

    ButtonGroup buttonGroup = new ButtonGroup();
    JRadioButton radioPatrat = new JRadioButton("Patrat");
    JRadioButton radioCerc = new JRadioButton("Cerc");

    JLabel historyLabel = new JLabel("Istoric");
    JTextArea historyText = new JTextArea();

    JButton saveButton = new JButton("Salveaza");

    JLabel emptyLabel = new JLabel("");

    Random rand = new Random();


    // variabila pentru masurarea timpului dintre afisari
    private long lastTime = -1;




    public MyApp() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("My App");
        this.setSize(400, 600);
        this.setLayout(null);

        desenareZona1();
        desenareZona2();



        this.setVisible(true);
    }


    public void desenareZona1(){
        this.add(zonaDeDesenare);
        zonaDeDesenare.setBounds(0,0, 400, 300);
        zonaDeDesenare.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void desenareZona2(){
        this.add(zona2);
        zona2.setLayout(null);
        zona2.setBounds(0,300, 400, 300);
        zona2.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel optinuiLabel = new JLabel("Optiuni: ");
        optinuiLabel.setBounds(0, 0, 70, 20);
        zona2.add(optinuiLabel);


        radioCerc.setBounds(75, 0, 70, 20);
        zona2.add(radioCerc);
        radioPatrat.setBounds(150, 0, 70, 20);
        zona2.add(radioPatrat);

        buttonGroup.add(radioPatrat);
        buttonGroup.add(radioCerc);


        radioCerc.addActionListener(e -> {
            int panelW = zonaDeDesenare.getWidth();
            int panelH = zonaDeDesenare.getHeight();

            // 1. generezi size random între 1 și min(panelW, panelH)
            int size = 1 + rand.nextInt(Math.min(panelW, panelH));

            // 2. generezi x și y random între 0 și panelW/H
            int x = rand.nextInt(panelW);
            int y = rand.nextInt(panelH);

            try {
                // 3. verifici depășirea
                if (x + size > panelW || y + size > panelH) {
                    throw new DepasireZonaError("Depășire zonă de desenare");
                }

                // masurare timp
                long now = System.currentTimeMillis();
                if (lastTime != -1) {
                    historyText.append("Timp de la ultima afisare: " + (now - lastTime) + " ms\n");
                }
                lastTime = now;


                // 4. desenezi (curățând ce era înainte)
                Graphics g = zonaDeDesenare.getGraphics();
                g.clearRect(0, 0, panelW, panelH);
                g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                g.drawOval(x, y, size, size);
                g.dispose();

                // 5. actualizezi istoricul și golești label-ul de eroare
                historyText.append(String.format("Cerc – x:%d y:%d size:%d%n", x, y, size));
                emptyLabel.setText("");

            } catch (DepasireZonaError ex) {
                emptyLabel.setText(ex.getMessage());
                emptyLabel.setForeground(Color.RED);
            }
        });



        radioPatrat.addActionListener(e -> {
            int panelW = zonaDeDesenare.getWidth();
            int panelH = zonaDeDesenare.getHeight();

            // 1. generezi size random între 1 și min(panelW, panelH)
            int size = 1 + rand.nextInt(Math.min(panelW, panelH));

            // 2. generezi x și y random între 0 și panelW/H
            int x = rand.nextInt(panelW);
            int y = rand.nextInt(panelH);

            try {
                // 3. verifici depășirea
                if (x + size > panelW || y + size > panelH) {
                    throw new DepasireZonaError("Depășire zonă de desenare");
                }

                // masurare timp
                long now = System.currentTimeMillis();
                if (lastTime != -1) {
                    historyText.append("Timp de la ultima afisare: " + (now - lastTime) + " ms\n");
                }
                lastTime = now;

                // 4. desenezi (curățând ce era înainte)
                Graphics g = zonaDeDesenare.getGraphics();
                g.clearRect(0, 0, panelW, panelH);
                g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                g.drawRect(x, y, size, size);
                g.dispose();

                // 5. actualizezi istoricul și golești label-ul de eroare
                historyText.append(String.format("Patrat – x:%d y:%d size:%d%n", x, y, size));
                emptyLabel.setText("");

            } catch (DepasireZonaError ex) {
                emptyLabel.setText(ex.getMessage());
                emptyLabel.setForeground(Color.RED);
            }
        });



        historyLabel.setBounds(10, 40, 70, 20);
        zona2.add(historyLabel);
        historyText.setBounds(10, 60, 380, 40);
        zona2.add(historyText);
        historyText.setEditable(false);


        saveButton.setBounds(150, 110, 70, 20);
        zona2.add(saveButton);

        emptyLabel.setBounds(10, 140, 380, 60);
        zona2.add(emptyLabel);
        //emptyLabel.setVisible(true);
        //emptyLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // pt test



        saveButton.addActionListener(e -> {
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter("thread_2_output.txt"));
                bw.write(historyText.getText());
                bw.close();

                emptyLabel.setText("Fisier scris cu succes.");
                emptyLabel.setForeground(Color.GREEN);

            } catch (IOException exIO){
                emptyLabel.setText(exIO.getMessage());
                emptyLabel.setForeground(Color.RED);
            }
        });


    }

}
