package Swing4;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

class Rain extends JPanel {

    Random r = new Random();
    private final int HEIGHT;
    private final int FONTSIZE;
    private final int XPOS;
    private int move;
    private final int X = 20;
    private final int Y = 50;
    ZrobmySobieStringa str;
    CurrentString currentStr;
    private int velocity = r.nextInt(Y - X + 1) + X;
    private String tekst = "";

    public Rain(int xpos, int height, int fontSize, String tekst) {
        this.FONTSIZE = fontSize;
        this.XPOS = xpos;
        this.HEIGHT = height;
        this.tekst = tekst;
        str = new ZrobmySobieStringa();
        currentStr = new CurrentString(tekst);
        currentStr.setCurrentString();

    }

    public void paintComponent(final Graphics g) {

        super.paintComponent(g);
        setBackground(Color.black);
        g.setFont(new Font("Gothic", Font.ITALIC, FONTSIZE));
        final FontMetrics fm = g.getFontMetrics();
        int ypos = fm.getHeight() - fm.getHeight() * tekst.length();

        for (int i = 0; i < tekst.length(); i++) {
            setColor(i, g);
            g.drawString(Character.toString(tekst.charAt(i)), XPOS, ypos + move);
            ypos += fm.getHeight();
        }

        new Thread(new Runnable() {
            public void run() {
                tekst = currentStr.getCurrentString();
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
}