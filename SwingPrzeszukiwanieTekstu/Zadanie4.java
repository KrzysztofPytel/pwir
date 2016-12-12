package SwingPrzeszukiwanieTekstu;

import javax.swing.*;

public class Zadanie4{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int x = 800;
                int y = 600;

                JFrame window = new JFrame("Poszukiwacz Tekstu");
                window.setSize(x, y);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setLocationRelativeTo(null);
                window.setVisible(true);

                Panels panels = new Panels(window);
                panels.addContent();

            }
        });
    }
}
