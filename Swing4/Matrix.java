package Swing4;
import java.awt.*;
import javax.swing.*;
public class Matrix extends JPanel {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ZrobmySobieStringa str = new ZrobmySobieStringa();
                int fontSize = 13;
                int x = 1600;
                int y = 900;
                JFrame okno = new JFrame("Matrix Rain");
                Key keyListener = new Key(okno);
                okno.setSize(x, y);
                okno.getContentPane().setBackground(Color.BLACK);
                okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                okno.setExtendedState(Frame.MAXIMIZED_BOTH);
                okno.setUndecorated(true);
                okno.setVisible(true);
                okno.setLayout(new GridLayout());
                int liczbaStringow = x / fontSize / 2;
                for (int i = 0; i < liczbaStringow; i++) {
                    okno.add(new Rain(1, y, fontSize, str.tworz()));
                }
            }
        });
    }
}
