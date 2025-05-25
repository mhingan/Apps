package org.example.ex3;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class Ex3 extends JFrame {

    JPanel panelDesenare = new JPanel();
    JPanel panelStanga = new JPanel();
    JPanel panelDreapta = new JPanel();


    JTextArea textArea = new JTextArea();

    JButton buttonX = new JButton();
    JButton buttonO = new JButton();
    JButton buttonSave = new JButton();

    JLabel emptyLabel = new JLabel();

    int indeX = 0;
    int indeO = 0;

    public Ex3() {

        this.setTitle("Ex3 Example");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setResizable(false);
        this.setLayout(null);

        desenarePanelDesenare();
        desenarePanelStanga();
        desenarePanelDreapta();


        this.add(panelDesenare);
        this.add(panelStanga);
        this.add(panelDreapta);

        this.setVisible(true);
    }


    public void desenarePanelDesenare() {
        panelDesenare.setLayout(null);
        panelDesenare.setBounds(0, 0, 500, 250);
        panelDesenare.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void desenarePanelStanga() {
        panelStanga.setLayout(null);
        panelStanga.setBounds(0, 250, 250, 250);
        panelStanga.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //adaugare zona text si 2 butaone x si o
        buttonX.setText("X");
        buttonX.setPreferredSize(new Dimension(50, 30));
        buttonX.setBounds(10, 100, 50, 30);

        buttonO.setText("O");
        buttonO.setPreferredSize(new Dimension(50, 30));
        buttonO.setBounds(10, 70, 50, 30);

        panelStanga.add(buttonX);
        panelStanga.add(buttonO);

        //adaugare zona de text
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBounds(60, 20, 180, 180);
        textArea.setBackground(Color.WHITE);
        textArea.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        panelStanga.add(textArea);


        int panelW = panelDesenare.getWidth();
        int panelH = panelDesenare.getHeight();
        int labelSize = 10;


        //adaugare functonalitate butaone
        buttonX.addActionListener(e -> {
            indeX++;
            //desenez x random
            Random random = new Random();
            int x = random.nextInt(500);
            int y = random.nextInt(750);

            if (x >= 0 && x + labelSize <= panelW && y >= 0 && y + labelSize <= panelH) {
                JLabel label = new JLabel("X");
                label.setBounds(x, y, 10, 10);
                panelDesenare.add(label);
                repaint();
            } else {
                emptyLabel.setForeground(Color.RED);
                emptyLabel.setText("Error: Depășire zonă de desenare.");
                indeX = 0;
                indeO = 0;
                return;
            }


            textArea.setText("X: " + indeX + "\nO: " + indeO);

        });



        //adaugare functonalitate butaone
        buttonO.addActionListener(e -> {
            indeO++;
            //desenez x random
            Random random = new Random();
            int x = random.nextInt(250);
            int y = random.nextInt(500);

            if (x >= 0 && x + labelSize <= panelW && y >= 0 && y + labelSize <= panelH) {
                JLabel label = new JLabel("O");
                label.setBounds(x, y, 10, 10);
                panelDesenare.add(label);
                repaint();
            } else {
                emptyLabel.setForeground(Color.RED);
                emptyLabel.setText("Error: Depășire zonă de desenare.");
                indeX = 0;
                indeO = 0;
                return;
            }


            textArea.setText("X: " + indeX + "\nO: " + indeO);
        });


        buttonSave.addActionListener(e -> {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("ex3.txt"));
                bufferedWriter.write(textArea.getText());
                bufferedWriter.close();
            } catch (IOException exe) {
                emptyLabel.setForeground(Color.RED);
                emptyLabel.setText("Error: " + exe.getMessage());
            }
        });
    }

    public void desenarePanelDreapta() {
        panelDreapta.setLayout(null);
        panelDreapta.setBounds(250, 250, 250, 250);
        panelDreapta.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        buttonSave.setText("Save");
        buttonSave.setPreferredSize(new Dimension(50, 30));
        buttonSave.setBounds(100, 180, 70, 30);
        panelDreapta.add(buttonSave);

        emptyLabel.setText("");
        emptyLabel.setPreferredSize(new Dimension(100, 100));
        emptyLabel.setBounds(10, 10, 230, 160);
        emptyLabel.setBackground(Color.WHITE);
        panelDreapta.add(emptyLabel);
    }


}
