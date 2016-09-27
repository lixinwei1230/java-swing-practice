import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by SuM_ on 9/26/16.
 */
public class LinesComponent extends JComponent {

    static LineLists lineLists = new LineLists();
    static {
        lineLists.setLines(new ArrayList<Line>());
    }

    private static final LinkedList<Line> lines = new LinkedList<Line>();

    public void addLine(int x1, int x2, int x3, int x4) {
        lineLists.getLines().add(new Line(x1, x2, x3, x4));
        repaint();
    }

    public void clearLines() {
        lineLists.getLines().clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Line line : lineLists.getLines()) {
            g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
        }
    }



    public static void main(String[] args) {

        JFrame myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final LinesComponent comp = new LinesComponent();
        comp.setPreferredSize(new Dimension(600, 600));
        myFrame.getContentPane().add(comp, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        JButton newLineButton = new JButton("New Line");
        JButton clearButton = new JButton("Clear");
        JButton savedAsButton = new JButton("Save As XML");
        JButton importXMLButton = new JButton("Import XML");
        buttonsPanel.add(newLineButton);
        buttonsPanel.add(clearButton);
        buttonsPanel.add(savedAsButton);
        buttonsPanel.add(importXMLButton);
        myFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        newLineButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int x1 = (int) (Math.random()*600);
                int x2 = (int) (Math.random()*600);
                int y1 = (int) (Math.random()*600);
                int y2 = (int) (Math.random()*600);
                comp.addLine(x1, y1, x2, y2);
            }
        });
        clearButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                comp.clearLines();
            }
        });
        savedAsButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {
                    File file = new File("/Users/SuM_/Desktop/demo/lines.xml");
                    JAXBContext jaxbContext = JAXBContext.newInstance(LineLists.class);
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                    // output pretty printed
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                    jaxbMarshaller.marshal(lineLists, file);
                    jaxbMarshaller.marshal(lineLists, System.out);
                } catch (JAXBException ex1) {
                    ex1.printStackTrace();
                }
            }
        });
        importXMLButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                comp.clearLines();

                try {
                    JAXBContext jaxbContext = JAXBContext.newInstance(LineLists.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                    LineLists lineLists = (LineLists) jaxbUnmarshaller.unmarshal( new File("/Users/SuM_/Desktop/demo/linesExample.xml") );

                    for(Line line: lineLists.getLines()) {

                        comp.addLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
                    }
                } catch (JAXBException ex2) {
                    ex2.printStackTrace();
                }

            }
        });
        myFrame.pack();
        myFrame.setVisible(true);
    }


}
