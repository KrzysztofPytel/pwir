/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Swing4;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

class Key implements KeyListener {

    public JFrame okno;

    Key(JFrame okno) {
        this.okno = okno;
        okno.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            okno.dispose();
            okno.setUndecorated(false);
            okno.setSize(800, 600);
            okno.setLocationRelativeTo(null);
            okno.getContentPane().setBackground(Color.BLACK);
            okno.setVisible(true);
            okno.setExtendedState(Frame.NORMAL);
        }
    }

}
