package SwingParserNBP;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class Parser extends SwingWorker<Void, Void> {

    private JFrame window;
    private JTable tabela;
    private JScrollPane panel;
    public Parser(JFrame window) {
        this.window = window;
    }
    
    public void parse() {
        String url = "http://www.nbp.pl/kursy/xml/a238z161209.xml";
        System.out.println("elo");
        try {

            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(url);
            doc.getDocumentElement().normalize();
            
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("pozycja");

            System.out.println("----------------------------");
            tabela = new JTable(0,3);
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            model.addRow(new Object[]{"Nazwa Waltuy", "Kod Waluty", "Kurs sredni"});
            window.add(tabela);
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    model.addRow(new Object[]{eElement.getElementsByTagName("nazwa_waluty").item(0).getTextContent(),
                        eElement.getElementsByTagName("przelicznik").item(0).getTextContent() + " " +
                        eElement.getElementsByTagName("kod_waluty").item(0).getTextContent(),
                        eElement.getElementsByTagName("kurs_sredni").item(0).getTextContent()});

                }
            }

        } catch (Exception e) {
        }
    }


    @Override
    protected Void doInBackground() {
        return null;
    }
}
