package Swing3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

class Czas extends JLabel {

    private Thread watek;
    private JFrame okno;
    private JLabel label;
    private JButton start;
    private JButton stop;
    private JPanel panel;
    private TimeZone TimeZone;
    private String miasto;

    Czas(JFrame okno, TimeZone TimeZone, String miasto) {
        this.okno = okno;
        this.TimeZone = TimeZone;
        this.miasto = miasto;

        panel = new JPanel();
        panel.setBackground(Color.BLUE);

        label = new JLabel(miasto);
        label.setFont(new Font("Consolas", Font.BOLD, 10));
        label.setBackground(Color.BLUE);
        label.setForeground(Color.WHITE);

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        start = new JButton("Start");
        stop = new JButton("Stop");

        start.addActionListener(new ObslugaStart());
        stop.addActionListener(new ObslugaStop());

        okno.add(label);
        okno.add(panel);
        panel.add(start);
        panel.add(stop);
        stop.setEnabled(false);

        label.setOpaque(true);

    }

    private class ObslugaStart implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            new Thread(new Runnable() {

                public void run() {
                    watek = Thread.currentThread();

                    for (;;) {
                        while (watek != null) {
                            Date godzina = new Date();
                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                            format.setTimeZone(TimeZone);
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    label.setText(miasto +": " + format.format(godzina));
                                    start.setEnabled(false);
                                    stop.setEnabled(true);
                                }

                            });
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                            }
                        }

                    }
                }
            }).start();
        }
    }

    private class ObslugaStop implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            start.setEnabled(true);
            stop.setEnabled(false);

            //watek = null;
            watek.stop();
        }
    }

}

public class Zegar {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame okno = new JFrame("Zegarek");
                okno.setSize(400, 200);
                okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                okno.setVisible(true);
                okno.setLayout(new GridLayout(4,0));
                
                TimeZone Warszawa = TimeZone.getTimeZone("Europe/Warsaw");
                TimeZone LosAngeles = TimeZone.getTimeZone("America/Los_Angeles");
                TimeZone Londyn = TimeZone.getTimeZone("Europe/London");
                TimeZone Tokio = TimeZone.getTimeZone("Asia/Tokyo");

                Czas czas1 = new Czas(okno, Warszawa, "Warszawa");
                Czas czas2 = new Czas(okno, LosAngeles, "Los Angeles");
                Czas czas3 = new Czas(okno, Londyn, "Londyn");
                Czas czas4 = new Czas(okno, Tokio, "Tokio");

            }
        });
    }
}
