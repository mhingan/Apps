package org.example.ex3_thread;

import javax.swing.*;        // componente UI
import java.awt.*;           // desenare si culori
import java.awt.event.*;     // evenimente (click pe butoane)
import java.io.*;            // scriere in fisier

// Clasa principala care extinde JFrame (fereastra)
public class MyAPP extends JFrame {

    // Panou pentru desen
    JPanel zonaDesenare = new JPanel();

    // Panou cu butoane, input, combo box
    JPanel zona2 = new JPanel();

    // Combo box-uri pentru selectie forma si culoare
    JComboBox<String> formaComboBox = new JComboBox<>();
    JComboBox<String> culoareComboBox = new JComboBox<>();

    // Input X si Y
    JLabel labelX = new JLabel("X");
    JTextField textFieldX = new JTextField();
    JLabel labelY = new JLabel("Y");
    JTextField textFieldY = new JTextField();

    // Zona istoric
    JLabel istoricLabel = new JLabel("Istoric");
    JTextArea textAreaIstoric = new JTextArea();

    // Butoane
    JButton buttonAfis = new JButton("Afiseaza");
    JButton buttonSave = new JButton("Salveaza");

    // Eticheta pentru afisarea mesajelor de eroare
    JLabel emptyLabel = new JLabel();

    // Constructorul aplicatiei
    public MyAPP() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // inchide aplicatia cand apesi X
        this.setTitle("MH");                                 // titlul ferestrei
        this.setSize(300, 600);                              // dimensiuni
        this.setResizable(false);                            // nu poate fi redimensionata

        paintZonaDesenare();                                 // configuram zona de desenare
        paintZona2();                                        // configuram zona de input

        this.setVisible(true);                               // facem aplicatia vizibila
    }

    // Configurare panou de desen
    public void paintZonaDesenare() {
        this.setLayout(null);                                // layout liber (manual)
        zonaDesenare.setBounds(0, 0, 300, 300);
        zonaDesenare.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // contur
        this.add(zonaDesenare);
    }

    // Configurare panou inferior (cu forme, input, butoane)
    public void paintZona2() {
        zona2.setLayout(null);
        zona2.setBounds(0, 300, 300, 300);
        zona2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(zona2);

        // Adaugam optiunile pentru forma
        formaComboBox.addItem("Patrat");
        formaComboBox.addItem("Cerc");
        formaComboBox.addItem("Text");
        formaComboBox.setBounds(10, 0, 100, 30);
        zona2.add(formaComboBox);

        // Adaugam optiunile pentru culoare
        culoareComboBox.addItem("Rosu");
        culoareComboBox.addItem("Verde");
        culoareComboBox.addItem("Albastru");
        culoareComboBox.setBounds(120, 0, 100, 30);
        zona2.add(culoareComboBox);

        // Campuri de input pentru X si Y
        labelX.setBounds(10, 30, 30, 30);
        zona2.add(labelX);
        textFieldX.setBounds(45, 30, 70, 30);
        zona2.add(textFieldX);

        labelY.setBounds(130, 30, 30, 30);
        zona2.add(labelY);
        textFieldY.setBounds(155, 30, 70, 30);
        zona2.add(textFieldY);

        // Zona de istoric
        istoricLabel.setBounds(10, 70, 70, 30);
        zona2.add(istoricLabel);
        textAreaIstoric.setBounds(10, 100, 280, 50);
        zona2.add(textAreaIstoric);

        // Butoane de afisare si salvare
        buttonAfis.setBounds(30, 170, 100, 30);
        zona2.add(buttonAfis);
        buttonSave.setBounds(150, 170, 100, 30);
        zona2.add(buttonSave);

        // Eticheta pentru mesaje
        emptyLabel.setBounds(10, 210, 280, 40);
        zona2.add(emptyLabel);

        // Cronometru pentru timp intre afisari
        ThreadTimer timer = new ThreadTimer();

        // Actiune pentru butonul "Afiseaza"
        buttonAfis.addActionListener(e -> {
            int x = Integer.parseInt(textFieldX.getText());
            int y = Integer.parseInt(textFieldY.getText());

            Graphics g = zonaDesenare.getGraphics();
            g.clearRect(0, 0, zonaDesenare.getWidth(), zonaDesenare.getHeight());

            // verificam daca campurile nu sunt goale
            if (!(textFieldX.getText().isBlank() || textFieldY.getText().isBlank())) {
                String forma = formaComboBox.getSelectedItem().toString();
                String culoare = culoareComboBox.getSelectedItem().toString();

                // setam culoarea aleasa
                if (culoare.equals("Rosu")) g.setColor(Color.RED);
                else if (culoare.equals("Verde")) g.setColor(Color.GREEN);
                else if (culoare.equals("Albastru")) g.setColor(Color.BLUE);

                // verificare depasire dimensiune (gresit: folosesti textFieldX.getX() in loc de x)
                if (x < 0 || y < 0 || x + 50 > zonaDesenare.getWidth() || y + 50 > zonaDesenare.getHeight()) {
                    try {
                        throw new MyExcept("Eroare depasire X sau Y");
                    } catch (MyExcept myEx) {
                        emptyLabel.setForeground(Color.RED);
                        emptyLabel.setText("Eroare depasire X sau Y");
                    }
                }

                long elapsed = timer.getElapsed();
                if (elapsed > 0) {
                    textAreaIstoric.append("Timp de la ultima afisare: " + elapsed + " ms\n");
                }
                timer.reset();

                // desenare forma
                switch (forma) {
                    case "Patrat":
                        g.drawRect(x, y, 50, 50);
                        break;
                    case "Cerc":
                        g.drawOval(x, y, 50, 50);
                        break;
                    case "Text":
                        g.setFont(new Font("Arial", Font.PLAIN, 24));
                        g.drawString("TEXT", x, y);
                        break;
                }

                g.dispose();

                // adaugam in istoric
                textAreaIstoric.append("Forma: " + forma + ", culoare: " + culoare +
                        " X: " + x + ", Y: " + y + ", dim = 50\n");
            }
        });

        // Actiune pentru butonul "Salveaza"
        buttonSave.addActionListener(e -> {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("fisier_ex3_thread.txt"));
                bw.write(textAreaIstoric.getText());
                bw.close();

                emptyLabel.setText("Scris cu succes.");
                emptyLabel.setForeground(Color.GREEN);
            } catch (IOException ioEx) {
                emptyLabel.setText("Eroare fisier.");
                emptyLabel.setForeground(Color.RED);
            }
        });
    }
}
