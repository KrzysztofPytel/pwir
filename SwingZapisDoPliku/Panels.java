package SwingZapisDoPliku;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Panels implements ActionListener {

    private Component modalToComponent;
    private JFrame window;
    private JPanel mainPanel;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton saveButton;
    private int windowX, windowY;
    private SaveText st;

    public Panels(JFrame window, int x, int y) {

        this.window = window;
        this.windowX = x;
        this.windowY = y;

        mainPanel = new JPanel(new BorderLayout());
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        saveButton = new JButton("Zapisz");
        saveButton.addActionListener(this);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(saveButton, BorderLayout.SOUTH);

        window.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        st = new SaveText();
        st.execute();
        saveButton.setEnabled(false);
    }

    class SaveText extends SwingWorker<String, Void> {

        private String tekst;

        @Override
        protected String doInBackground() throws Exception {
            tekst = textArea.getText();
            Thread.sleep(1000);
            return tekst;
        }

        @Override
        protected void done() {
            saveButton.setEnabled(true);
            
            FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Text File", "txt");
            JFileChooser saveAsFileChooser = new JFileChooser();
            saveAsFileChooser.setApproveButtonText("Zapisz");
            saveAsFileChooser.setFileFilter(extensionFilter);
            int actionDialog = saveAsFileChooser.showOpenDialog(window);
            if (actionDialog != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File file = saveAsFileChooser.getSelectedFile();
            if (!file.getName().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }

            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file));
                textArea.write(writer);
            } catch (IOException ex) {
            }
        }
    }
}
