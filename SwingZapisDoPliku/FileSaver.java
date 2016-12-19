package SwingZapisDoPliku;

import javax.swing.*;

public class FileSaver{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int x = 600;
                int y = 400;

                JFrame window = new JFrame("File Saver");
                window.setSize(x, y);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setLocationRelativeTo(null);
                window.setVisible(true);
                
                Panels panels = new Panels(window,x,y);
            }
        });
    }
}
