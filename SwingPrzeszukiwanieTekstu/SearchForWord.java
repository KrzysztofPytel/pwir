package SwingPrzeszukiwanieTekstu;

import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class SearchForWord extends SwingWorker<Void, Void> {

    private File katalog;
    private File[] files;
    private String[] filesName;
    private JTextArea output;
    private String filesFound = "";
    private boolean found;
    private String text;
    private int stan;
    private JLabel stanLabel;
    private JButton start;
    private JTextField searchField;

    public SearchForWord(JTextArea output, JLabel stanLabel, JTextField searchField, JButton start, String text) {
        this.stanLabel = stanLabel;
        this.searchField = searchField;
        this.start = start;
        stan = 1;
        this.text = text;
        getFiles();
        found = false;
        this.output = output;

    }

    public void getFiles() {
        katalog = new File("D:\\test");

        files = katalog.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });

        filesName = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            filesName[i] = files[i].getName();
        }
    }

    public void search(String word) {
        for (int i = 0; i < files.length; i++) {
            stanLabel.setText(stan + "/" + files.length + " Szukam w pliku: " + files[i]);
            try {
                Scanner scan = new Scanner(new BufferedReader(new FileReader(files[i])));
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    for (int x = 0; x < line.length(); x++) {
                        if (line.contains(word)) {
                            filesFound += files[i] + " znaleziono slowo: " + word + '\n';
                            output.setText(filesFound);
                            found = true;
                            break;
                        }
                        if (found == true) {
                            break;
                        }
                    }
                    if (found == true) {
                        found = false;
                        break;
                    }
                }
            } catch (FileNotFoundException ex) {
                System.out.println("File not found");
            }
            stan++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    protected Void doInBackground() throws Exception {
        search(text);
        return null;
    }

    @Override
    protected void done() {
        stanLabel.setText("Szukanie zakonczone");
        start.setEnabled(true);
        searchField.setEditable(true);
    }
}
