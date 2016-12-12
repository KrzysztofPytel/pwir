package SwingPrzeszukiwanieTekstu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Panels {
    
    JFrame window;
    private String setText;
    private SearchForWord search;
    
    public Panels(JFrame window) {
        this.window = window;
    }
    
    public void addContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panelOutput = new JPanel(new BorderLayout());
        JPanel panelInput = new JPanel(new BorderLayout());
        JTextArea showFilesLabel = new JTextArea(30, 50);
        JLabel enterWordLabel = new JLabel("Wprowadz szukane slowo: ");
        JLabel state = new JLabel();
        JButton button = new JButton("Szukaj");
        JTextField word = new JTextField();
        
        panelOutput.setBorder(BorderFactory.createLineBorder(Color.black));
        panelInput.setBorder(BorderFactory.createLineBorder(Color.black));
        
        window.add(mainPanel);
        mainPanel.add(panelInput, BorderLayout.NORTH);
        mainPanel.add(panelOutput, BorderLayout.CENTER);
        
        panelOutput.add(showFilesLabel, BorderLayout.CENTER);
        panelOutput.add(state, BorderLayout.SOUTH);
        
        panelInput.add(enterWordLabel, BorderLayout.WEST);
        panelInput.add(word, BorderLayout.CENTER);
        panelInput.add(button, BorderLayout.EAST);
        
        word.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setText = word.getText();
            }
        }
        );
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                word.setEditable(false);
                button.setEnabled(false);
                search = new SearchForWord(showFilesLabel, state, word, button, setText);
                search.execute();
            }
        }
        );
    }
}
