package org.example.ex4;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MyFrame extends JFrame {

    JPanel zonaDesenare = new JPanel();
    JPanel zona2 = new JPanel();


    JLabel labelLista = new JLabel("Alege:");
    JComboBox<String> items = new JComboBox<>();

    JLabel labelNotita = new JLabel("Notita: ");
    JTextArea textAreaNotita1 = new JTextArea();
    JButton addButton = new JButton("Adauga");

    JLabel labelNotita2 = new JLabel("Notite: ");
    JTextArea textAreaNotite = new JTextArea();

    JButton saveButton = new JButton("Salveaza");

    JLabel emptyLabel = new JLabel("");


    public MyFrame() {
        this.setTitle("My Frame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 700);
        this.setLayout(null);

        paintZonaDesenare();
        paintZona2();


        this.add(zonaDesenare);
        this.add(zona2);

        this.setVisible(true);
    }


    public void paintZonaDesenare() {
        zonaDesenare.setLayout(null);
        zonaDesenare.setBounds(0, 0, 300, 300);
        zonaDesenare.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }


    public void paintZona2() {
        zona2.setLayout(null);
        zona2.setBounds(0, 300, 300, 400);
        zona2.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        labelLista.setBounds(0, 0, 300, 30);
        zona2.add(labelLista);


        //adaugare elemente in lista
        items.addItem("Important");
        items.addItem("Normal");
        items.addItem("Trivial");

        items.setBounds(0, 30, 300, 30);
        zona2.add(items);

        //adaugaree camp text +etichtea notita
        labelNotita.setBounds(0, 60, 300, 30);
        zona2.add(labelNotita);
        textAreaNotita1.setBounds(10, 90, 280, 50);
        textAreaNotita1.setEditable(true);
        zona2.add(textAreaNotita1);

        //adaugare buton adauga
        addButton.setPreferredSize(new Dimension(70, 40));
        addButton.setBounds(100, 150, 70, 40);
        zona2.add(addButton);

        //adaugare camp multilinie gol + eticheta notite
        labelNotita2.setBounds(0, 180, 300, 30);
        zona2.add(labelNotita2);
        textAreaNotite.setBounds(10, 210, 280, 50);
        textAreaNotite.setEditable(false);
        zona2.add(textAreaNotite);

        //adaugare buton salveaza
        saveButton.setPreferredSize(new Dimension(70, 40));
        saveButton.setBounds(100, 270, 70, 40);
        zona2.add(saveButton);

        //adaugare eticheta goala
        emptyLabel.setBounds(10, 320, 280, 30);
        emptyLabel.setOpaque(true);
        emptyLabel.setBackground(new Color(220, 220, 220));
        zona2.add(emptyLabel);

        JPanel patrat = new JPanel();
        patrat.setLayout(null);


        //functionalitate Buton adauga
        addButton.addActionListener(e -> {

            if (textAreaNotita1.getText().isEmpty() || textAreaNotita1.getText().isBlank()) {
                emptyLabel.setText("Nu exista notita pentru adaugare.");
                emptyLabel.setForeground(Color.RED);
                throw new RuntimeException("Nu exista notita pentru adaugare.");
            }

            if (!(textAreaNotita1.getText().isEmpty() && textAreaNotita1.getText().isBlank())) {
                if (items.getSelectedItem().equals("Important")) {
                    patrat.setBounds(this.getX() / 2, this.getY() / 2, 50, 50);
                    patrat.setBackground(Color.RED);
                    zonaDesenare.add(patrat);
                    repaint();
                    textAreaNotite.append("Notita: " + textAreaNotita1.getText() + " - Important" + "\n");
                }
                if (items.getSelectedItem().equals("Normal")) {
                    patrat.setBounds(this.getX() / 2, this.getY() / 2, 50, 50);
                    patrat.setBackground(Color.GREEN);
                    zonaDesenare.add(patrat);
                    repaint();
                    textAreaNotite.append("Notita: " + textAreaNotita1.getText() + " - Normal" + "\n");
                }
                if (items.getSelectedItem().equals("Trivial")) {
                    patrat.setBounds(this.getX() / 2, this.getY() / 2, 50, 50);
                    patrat.setBackground(Color.BLUE);
                    zonaDesenare.add(patrat);
                    repaint();
                    textAreaNotite.append("Notita: " + textAreaNotita1.getText() + " - Trivial" + "\n");
                }
            }
        });


        //functionalitate buton salveaza
        saveButton.addActionListener(e -> {
           try{
               BufferedWriter bw = new BufferedWriter(new FileWriter("notite.txt"));
               bw.write(textAreaNotite.getText());
               emptyLabel.setText("Fisier scris cu succes.");
               emptyLabel.setForeground(Color.GREEN);
               bw.close();

           } catch (IOException ex){
               System.out.println("Error: " + ex.getMessage());
           }
        });

    }


}
