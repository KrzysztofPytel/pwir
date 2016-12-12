package SwingParserNBP;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Zadanie5 {
        public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

               JFrame window = new JFrame("Poszukiwacz Tekstu");
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setVisible(true);
                window.setSize(800,700);
                
                JPanel mainPanel = new JPanel();           
                mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
             
                window.add(mainPanel);
                    
                Parser parser = new Parser(window);
                parser.execute();

            }
        });
    }
}
