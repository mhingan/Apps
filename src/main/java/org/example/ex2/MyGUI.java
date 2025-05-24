package org.example.ex2;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MyGUI extends JFrame {

    //declararea jpaneluri
    JPanel panelDesenare = new JPanel();
    JPanel panelChoiceList = new JPanel();
    JPanel panelDuration = new JPanel();
    JPanel panelPlanification = new JPanel();
    JPanel finalPanel = new JPanel();

    //declarare Butoane choice
    JRadioButton muncaBtn = new JRadioButton("Munca");
    JRadioButton relaxareBtn = new JRadioButton("Relaxare");
    JRadioButton sportBtn = new JRadioButton("Sport");

    //camp durata + buton
    JLabel durataLabel = new JLabel("Durata: ");
    JTextField durataField = new JTextField();
    JButton adaugaBtn = new JButton("Adauga");

    //text  multiline gol + eticheta Planificare
    JLabel planificareLabel = new JLabel("Planificare: ");
    JTextArea planificareTextArea = new JTextArea();

    //buton salvare
    JButton salvareBtn = new JButton("Salvare");

    //eticheta goala
    JLabel emptyLabel = new JLabel();


    public MyGUI() {
        this.setTitle("My GUI");
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);


        //formatare panel desenare
        panelDesenare.setLayout(null);
        JLabel labelDesenare = new JLabel("Zona desenare: ");
        labelDesenare.setBounds(0, 0, 100, 30);
        this.add(labelDesenare);
        panelDesenare.setBounds(0, 30, 300, 300);
        panelDesenare.setBorder(BorderFactory.createLineBorder(Color.black));


        //formatare Panel Choice List
        panelChoiceList.setLayout(null);
        JLabel labelChoiceList = new JLabel("Alegeti: ");
        labelChoiceList.setBounds(350, 0, 100, 30);
        this.add(labelChoiceList);
        panelChoiceList.setBounds(300, 30, 300, 100);
        panelChoiceList.setBorder(BorderFactory.createLineBorder(Color.black));


        //creare grup butoane si adaugare in panelChoceList
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(muncaBtn);
        buttonGroup.add(relaxareBtn);
        buttonGroup.add(sportBtn);

        muncaBtn.setBounds(0, 0, 100, 30);
        relaxareBtn.setBounds(0, 30, 100, 30);
        sportBtn.setBounds(0, 60, 100, 30);

        panelChoiceList.add(muncaBtn);
        panelChoiceList.add(relaxareBtn);
        panelChoiceList.add(sportBtn);


        //panelDurata formatare
        panelDuration.setLayout(null);
        durataLabel.setBounds(350, 130, 100, 30);
        this.add(durataLabel);
        panelDuration.setBounds(300, 100, 300, 150);
        durataField.setBounds(30, 60, 100, 30);
        panelDuration.add(durataField);
        adaugaBtn.setBounds(30, 120, 100, 30);
        panelDuration.add(adaugaBtn);


        //adaugare camp planificare
        panelPlanification.setLayout(null);
        planificareLabel.setBounds(350, 250, 100, 30);
        this.add(planificareLabel);
        panelPlanification.setBounds(310, 280, 280, 50);
        planificareTextArea.setBounds(0, 0, 300, 50);
        this.add(panelPlanification);
        planificareTextArea.setEditable(false);
        planificareTextArea.setBackground(Color.WHITE);
        panelPlanification.add(planificareTextArea);

        //adaugare buton salvare si eticheta goala
        finalPanel.setLayout(null);
        finalPanel.setBounds(50, 450, 500, 80);
        finalPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        salvareBtn.setBounds(10, 10, 100, 30);
        finalPanel.add(salvareBtn);

        emptyLabel.setBounds(120, 10, 200, 30);
        emptyLabel.setOpaque(true);
        emptyLabel.setBackground(Color.LIGHT_GRAY);
        emptyLabel.setText("");
        finalPanel.add(emptyLabel);

        JLabel textToWrite = new JLabel();


        adaugaBtn.addActionListener(e -> {
            // 1) Parsezi și validezi durata
            int durata;
            try {
                durata = Integer.parseInt(durataField.getText());
            } catch (NumberFormatException ex) {
                planificareTextArea.append("Eroare: durata invalidă\n");
                return;
            }

            // 2) Creezi un nou JLabel la fiecare click (să nu acumulezi referințe)
            textToWrite.setText("Hello World: " + durata + "s");
            textToWrite.setForeground(Color.GREEN);
            textToWrite.setBounds(60, 70, 200, 30);
            panelDesenare.add(textToWrite);
            // reaplici layout-ul și redesenezi
            panelDesenare.revalidate();
            panelDesenare.repaint();

            // 3) Pornești un javax.swing.Timer care după 'durata' secunde îndepărtează label-ul
            new Timer(durata * 1000, evtTimer -> {
                panelDesenare.remove(textToWrite);
                panelDesenare.revalidate();
                panelDesenare.repaint();
                ((Timer) evtTimer.getSource()).stop();
            }).start();

            // 4) Adaugi un singur rând nou în planificare (fără a re-atașa tot textul existent)
            String activitate;
            if (relaxareBtn.isSelected()) activitate = "Relaxare";
            else if (sportBtn.isSelected()) activitate = "Sport";
            else activitate = "Munca";

            planificareTextArea.append(activitate + ": " + durata + "s\n");

            // 5) (Opțional) golești câmpul de input
            durataField.setText("");
        });


        //adaugare functionalitate buton
        salvareBtn.addActionListener(e -> {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("planificare.txt"));
                bw.write(planificareTextArea.getText());
                emptyLabel.setText("Succes.");
                bw.close();
            } catch (IOException e1) {
                emptyLabel.setText("Error: " + e1.getMessage());
                System.out.println("err: " + e1.getMessage());
            }
        });


        this.add(panelDesenare);
        this.add(panelChoiceList);
        this.add(panelDuration);
        this.add(panelPlanification);
        this.add(finalPanel);

        this.setVisible(true);
    }
}
