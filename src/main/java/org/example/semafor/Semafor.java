package org.example.semafor;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TimerTask;

public class Semafor extends JFrame {

    JPanel panelDesenare = new JPanel();
    JPanel panel2 = new JPanel();

    JButton buttonAuto = new JButton("Auto");
    JButton buttonR = new JButton("RED");
    JButton buttonY = new JButton("YELLOW");
    JButton buttonG = new JButton("GREEN");

    JButton buttonSave = new JButton("Save to file");

    JLabel labelHistory = new JLabel("Istoric: ");
    JTextArea textArea = new JTextArea();

    JLabel labelEmpty = new JLabel();


    public Semafor() {
        this.setTitle("Semafor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(600, 800);

        desenare1();
        desenare2();



        this.setVisible(true);
    }


    public void desenare1(){
        panelDesenare.setLayout(null);
        panelDesenare.setBounds(0,0,600, 400);
        panelDesenare.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(panelDesenare);

        this.setVisible(true);
    }


    public void desenare2(){
        panel2.setLayout(null);
        panel2.setBounds(0,400, 600, 400);
        panel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(panel2);

        buttonAuto.setBounds(10, 10, 70, 30);
        panel2.add(buttonAuto);

        buttonR.setBounds(80, 10, 70, 30);
        panel2.add(buttonR);

        buttonY.setBounds(160, 10, 100, 30);
        panel2.add(buttonY);

        buttonG.setBounds(270, 10, 70, 30);
        panel2.add(buttonG);

        labelHistory.setBounds(50, 50, 500, 30);
        panel2.add(labelHistory);


        textArea.setBounds(50, 80, 500, 60);
        textArea.setEditable(false);
        panel2.add(textArea);


        labelEmpty.setBounds(50, 150, 500, 30);
        panel2.add(labelEmpty);

        buttonSave.setBounds(100, 180, 100, 30);
        panel2.add(buttonSave);


        this.setVisible(true);






        buttonR.addActionListener(e -> {
           Graphics g = panelDesenare.getGraphics();
           g.clearRect(0, 0, panelDesenare.getWidth(), panelDesenare.getHeight());


           g.setColor(Color.RED);
           g.fillOval(250, 30, 70, 70);

            g.setColor(Color.lightGray);
            g.fillOval(250, 100, 70, 70);

            g.fillOval(250, 170, 70, 70);

           g.dispose();

           textArea.append("RED - 1\n");

        });

        buttonY.addActionListener(e -> {
            Graphics g = panelDesenare.getGraphics();
            g.clearRect(0, 0, panelDesenare.getWidth(), panelDesenare.getHeight());

            g.setColor(Color.lightGray);
            g.fillOval(250, 30, 70, 70);

            g.setColor(Color.YELLOW);
            g.fillOval(250, 100, 70, 70);

            g.setColor(Color.lightGray);
            g.fillOval(250, 170, 70, 70);

            g.dispose();

            textArea.append("YELLOW - 2\n");

        });


        buttonG.addActionListener(e -> {
            Graphics g = panelDesenare.getGraphics();
            g.clearRect(0, 0, panelDesenare.getWidth(), panelDesenare.getHeight());

            g.setColor(Color.lightGray);
            g.fillOval(250, 30, 70, 70);

            g.setColor(Color.lightGray);
            g.fillOval(250, 100, 70, 70);

            g.setColor(Color.GREEN);
            g.fillOval(250, 170, 70, 70);

            g.dispose();

            textArea.append("GREEN - 3\n");

        });



        buttonSave.addActionListener(e -> {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("semafor_istoric.txt"));
                bw.write(textArea.getText());
                bw.close();

                labelEmpty.setText("Write successful in file.");
                labelEmpty.setForeground(Color.GREEN);
            } catch (IOException ioEx) {
                labelEmpty.setText("Error file.");
                labelEmpty.setForeground(Color.RED);
            }
        });




        buttonAuto.addActionListener(e -> {
            int delay = 3000;
            int[] step = {0};

            Timer swingTimer = new Timer(delay, null);
            swingTimer.addActionListener(ev -> {
                Graphics g = panelDesenare.getGraphics();

                g.clearRect(0, 0, panelDesenare.getWidth(), panelDesenare.getHeight());
                g.setColor(Color.lightGray);
                g.fillOval(250, 30, 70, 70);
                g.fillOval(250, 100, 70, 70);
                g.fillOval(250, 170, 70, 70);

                switch (step[0]) {
                    case 0 -> {
                        g.setColor(Color.RED);
                        g.fillOval(250, 30, 70, 70);
                    }
                    case 1 -> {
                        g.setColor(Color.YELLOW);
                        g.fillOval(250, 100, 70, 70);
                    }
                    case 2 -> {
                        g.setColor(Color.GREEN);
                        g.fillOval(250, 170, 70, 70);
                        swingTimer.stop();
                    }
                }

                g.dispose();
                step[0]++;
            });

            swingTimer.setInitialDelay(0);
            swingTimer.start();

            textArea.append("AUTO - 4\n");
        });



    }







}
