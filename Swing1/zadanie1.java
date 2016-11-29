package Swing1;

import java.awt.event.*;
import javax.swing.*;

class Okno extends JFrame {

    JButton przycisk;
    JProgressBar pasek;

    public Okno(String nazwa) {
        super(nazwa);

        przycisk = new JButton("Start");
        pasek = new JProgressBar();

        JPanel panel = new JPanel();

        panel.add(przycisk);
        panel.add(pasek);

        przycisk.addActionListener(new ObslugaZdarzen());

        add(panel);

        setSize(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class ObslugaZdarzen implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            pasek.setValue(0);
            pasek.setStringPainted(true);
            przycisk.setEnabled(false);

            new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i <= 100; i++) {
                        final int postep = i;
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                pasek.setValue(postep);
                            }
                        });
                        try {
                            Thread.sleep(50);
                        } catch (Exception e) {
                        }
                        
                    }
                    przycisk.setEnabled(true);
                }
            }).start();
        }

    }

}

public class zadanie1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Okno("Pasek postępu");
            }
        });
    }
}
