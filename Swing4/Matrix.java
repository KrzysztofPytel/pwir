package Swing4;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

class Rain extends JPanel {

    Random r = new Random();
    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    private final int WIDTH;
    private final int HEIGHT;
    private final int FONTSIZE;
    private int move;
    private final int XPOS;
    private final int X = 10;
    private final int Y = 25;

    private int velocity = r.nextInt(Y - X + 1) + X;

    private static String tekst = "";
    private static final char[] test = {'優', '劣', 'や', '善', '悪', 'に', 'よ',
        +'っ', 'て', '選', '出', 'さ', 'れ', 'て', 'い', 'ケ', 'コ', 'イ', 'ウ',
        +'エ', 'オ', 'ジ', 'ャ', 'な', 'な', '記', '事', 'に', '選', 'ば', 'れ', 'て', 'い'
        + 'る', 'み', 'ん', 'な', 'の', '感', '想', 'を', '見', 'る', 'こ', 'と', 'が', 'で', 'き'};

    public Rain(int xpos, int width, int height, int fontSize) {
        this.FONTSIZE = fontSize;
        this.XPOS = xpos;
        this.WIDTH = width;
        this.HEIGHT = height;
        new Worker().execute();
    }

    @SuppressWarnings("Convert2Lambda")
    public void paintComponent(final Graphics g) {

        super.paintComponent(g);
        setBackground(Color.black);
        g.setFont(new Font("Gothic", Font.ITALIC, FONTSIZE));

        final FontMetrics fm = g.getFontMetrics();

        //int stringWidth = fm.stringWidth(tekst);
        int ypos = fm.getHeight() - fm.getHeight() * tekst.length();

        for (int i = 0; i < tekst.length(); i++) {
            setColor(i, g);
            g.drawString(Character.toString(tekst.charAt(i)), XPOS, ypos + move);
            System.out.println(ypos);
            ypos += fm.getHeight();
        }

        new Thread(new Runnable() {
            public void run() {
                repaint();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        move = move + velocity;
                        if (move > HEIGHT + fm.getHeight() * tekst.length()) {
                            move = 0;
                            velocity = r.nextInt(Y - X + 1) + X;
                        }
                    }
                });
            }
        }
        ).start();
    }

    public String str(char[] signArray) {
        String slong = "";
        char sign;
      
        for (int i = 0; i < HEIGHT / FONTSIZE / 2; i++) {
            sign = test[r.nextInt(test.length)];
            slong += Character.toString(sign);
        }
        return slong;
    }

    public void setColor(int i, Graphics g) {
        int div = tekst.length() / 4;
        if (i == tekst.length() - 1) {
            g.setColor(Color.WHITE);
        }
        if (i < div) {
            g.setColor(new Color(0, 50, 0));
        }
        if (i > div && i < div * 2 && i != tekst.length() - 1) {
            g.setColor(new Color(0, 100, 0));
        }
        if (i > div * 2 && i < div * 3 && i != tekst.length() - 1) {
            g.setColor(new Color(0, 150, 0));
        }
        if (i > div * 3 && i != tekst.length() - 1) {
            g.setColor(new Color(0, 180, 0));
        }
    }

    class Worker extends SwingWorker<Integer, Integer> {

        @SuppressWarnings("SleepWhileInLoop")
        protected Integer doInBackground() throws Exception {
            while (true) {
                tekst = str(test);
                Thread.sleep(Math.round(Math.random() * 2000));
            }
        }
    }
}

public class Matrix extends JPanel {

    @SuppressWarnings("Convert2Lambda")
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int fontSize = 12;
                int x = 1600;
                int y = 900;
                Random rd = new Random();
                JFrame okno = new JFrame("GUI");
                okno.setSize(x, y);
                okno.setLocationRelativeTo(null);
                okno.setBackground(Color.BLACK);
                okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                okno.setVisible(true);
                okno.setLayout(new GridLayout());

                int liczbaWatkow = x / fontSize / 2;
                for (int i = 0; i < liczbaWatkow; i++) {
                    okno.add(new Rain(1, x, y, fontSize));
                }
            }
        });
    }
}
