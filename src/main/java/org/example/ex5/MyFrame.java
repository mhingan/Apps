/*
5. Implementați o aplicație grafică AWT sau SWING cu următoarele specificații:
•	Zona de desenare de 300x300.
•	Componentă Choice/List cu itemii: „Cerc”, „Pătrat”, „Triunghi”.
•	Câmp text etichetat „Coordonate” (X și Y).
•	Buton „Desenează”.
•	Buton „Salvează”.
•	Etichetă goală.
Funcționalități:
1.	(2p) Implementarea interfeței grafice.
2.	(2,5p) La apăsarea butonului „Desenează”, se afișează în zona de desenare forma selectată la coordonatele X și Y.
3.	(1p) Acțiunea se salvează în format: Formă - X,Y în câmpul „Coordonate”.
4.	(1,5p) Salvarea textului într-un fișier forme_desenate.txt.
5.	(2p) Dacă coordonatele sunt în afara zonei de desenare, se aruncă o excepție și se afișează în etichetă „Coordonate invalide”.
6.	(1p) Coding style corect, clase separate, nume sugestive, interfață complet vizibilă.

* **/



package org.example.ex5;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MyFrame extends JFrame {

    JPanel panelDesenare = new JPanel();
    JPanel panelFinal = new JPanel();

    ButtonGroup group = new ButtonGroup();

    JRadioButton radioCerc = new JRadioButton("Cerc");
    JRadioButton radioPatrat = new JRadioButton("Patrat");
    JRadioButton radioTriunghi = new JRadioButton("Triunghi");

    JLabel labelCoordonates = new JLabel("Coordonate: ");
    JTextArea textCoordonate = new JTextArea();

    JTextArea afisareOutput = new JTextArea();

    JButton paintButton = new JButton("Deseneaza");

    JButton saveButton = new JButton("Salveaza");

    JLabel emptyLabel = new JLabel();

    int x = 0;
    int y = 0;


    public MyFrame(){
        this.setTitle("Examen - HM - 2521");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300, 600);
        this.setLayout(null);
        this.setResizable(false);

        drawPanelDensenare();
        drawPanelFinal();

        this.add(panelDesenare);
        this.add(panelFinal);



        this.setVisible(true);
    }


    public void drawPanelDensenare(){
        panelDesenare.setLayout(null);
        panelDesenare.setBounds(0,0, 300, 300);
        panelDesenare.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void drawPanelFinal(){
        panelFinal.setLayout(null);
        panelFinal.setBounds(0, 300,300, 300);
        panelFinal.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        radioCerc.setBounds(0,0, 70, 30);
        radioPatrat.setBounds(0, 30, 70, 30);
        radioTriunghi.setBounds(0,60, 100, 30);

        //creare grup pentru butonale radio
        group.add(radioCerc);
        group.add(radioPatrat);
        group.add(radioTriunghi);

        panelFinal.add(radioCerc);
        panelFinal.add(radioPatrat);
        panelFinal.add(radioTriunghi);


        //adaugare camp text + label
        labelCoordonates.setBounds(0, 90, 300, 30);
        panelFinal.add(labelCoordonates);

        textCoordonate.setBounds(10, 120, 280, 30);
        panelFinal.add(textCoordonate);

        //adaugare buton deseneaza

        paintButton.setBounds(10, 150, 100, 30);
        panelFinal.add(paintButton);

        //adaugare buton salvare
        saveButton.setBounds(130, 150, 100, 30);
        panelFinal.add(saveButton);

        //adaugare eticheta goala
        emptyLabel.setBounds(10, 220, 280, 30);
        panelFinal.add(emptyLabel);
        emptyLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        afisareOutput.setBounds(10, 180, 280, 30);
        afisareOutput.setEditable(false);
        panelFinal.add(afisareOutput);




        //adaugare functionalitate buton deseneaza
        //trebuie sa iau forma si coordonatele
        paintButton.addActionListener(e -> {
                try {
                    String[] xy = textCoordonate.getText().split(",");
                    int x = Integer.parseInt(xy[0].trim());
                    int y = Integer.parseInt(xy[1].trim());

                    Graphics g = panelDesenare.getGraphics();
                    if (g == null) return;

                    //sterg panelul inainte sa desenez alta forma
                    g.clearRect(0, 0, panelDesenare.getWidth(), panelDesenare.getHeight());

                    if (radioCerc.isSelected()) {
                        g.drawOval(x, y, 100, 100);
                        //salvez actiunea in campul de coordonate
                        afisareOutput.append("\nCerc - " + x + " " + y);
                    } else if (radioPatrat.isSelected()) {
                        g.drawRect(x, y, 100, 100);
                        //salvez actiunea in campul de coordonate
                        afisareOutput.append("\nPatrat - " + x + " " + y);
                    } else if (radioTriunghi.isSelected()) {
                        int[] xp = { x, x + 50, x + 100 };
                        int[] yp = { y + 100, y,    y + 100 };
                        g.drawPolygon(xp, yp, 3);
                        //salvez actiunea in campul de coordonate
                        afisareOutput.append("\nTriunghi - " + x + " " + y);
                    }

                    g.dispose();


                } catch (Exception ex) {
                    emptyLabel.setForeground(Color.RED);
                    emptyLabel.setText("Error: " + ex.getMessage());
                }
            });


        saveButton.addActionListener(e -> {
            try{
                BufferedWriter out = new BufferedWriter(new FileWriter("examen-output.txt"));
                out.write(afisareOutput.getText());
                out.close();

                emptyLabel.setForeground(Color.GREEN);
                emptyLabel.setText("Scris cu succes.");
            } catch (IOException exception){
                emptyLabel.setForeground(Color.RED);
                emptyLabel.setText("Error: " + exception.getMessage());
            }
        });



    }
}
