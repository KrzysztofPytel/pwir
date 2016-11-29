package Swing2;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;



class Pasek{
    private int opoznienie;
    JFrame okno;
    JButton przycisk;
    JProgressBar pasek;
    
    Pasek(JFrame okno, int opoznienie)
    {
        System.out.println(opoznienie);
        this.okno = okno;
        this.opoznienie = opoznienie;
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0,32,112));
        
        przycisk = new JButton("Start");
        pasek = new JProgressBar();
        
        panel.add(przycisk);
        panel.add(pasek);
        
        przycisk.addActionListener(new ObslugaZdarzen());
        
        okno.add(panel);

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
                            Thread.sleep(opoznienie);
                        } catch (Exception e) {
                        }
                        
                    }
                    przycisk.setEnabled(true);
                }
            }).start();
        }

    }
}
public class Zadanie2 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame okno = new JFrame("Pasek postepu");
                okno.setSize(800, 600);
                okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                okno.setVisible(true);
                okno.setLayout(new GridLayout(2,4));
  
                Pasek pasek1 = new Pasek(okno, 50);
                Pasek pasek2 = new Pasek(okno, 10);
                Pasek pasek3 = new Pasek(okno, 20);
                Pasek pasek4 = new Pasek(okno, 30);

            }
        });
    }
}
