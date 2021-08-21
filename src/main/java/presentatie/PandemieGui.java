package presentatie;

import data.DataLayerJDBC;
import logica.Stad;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dit is een Gui voor het kaartspel Pandemie, een opdracht voor het vak Object Orientation & Database Concepts - 2020-2021 EP3
 * @author
 */

public class PandemieGui {

    private JPanel mainPanel;
    private JPanel spelersPanel;
    private JPanel infoPanel;
    private JButton infectieTrekButton;
    private JList stedenList;
    private JButton handTrekButton;
    private JTabbedPane beurt;
    private JTextArea logTextArea;
    private JButton afleggen;
    private JButton ruil;
    private JPanel infectieStapel;
    private JPanel AflegStapel;
    private List<SpelerPanel> spelerPanels;
    private ArrayList <String> aflegstapel = new ArrayList<>();
    DataLayerJDBC datalayer = new DataLayerJDBC("pandemie", true);
    private ArrayList <String> selectedSteden = new ArrayList<>();
    private ArrayList <JPanel> selectedJPanels = new ArrayList<>();


    public JButton getAfleggen() {
        return this.afleggen;
    }


    public JButton getInfectieTrekButton() {
        return infectieTrekButton;

    }

    public ArrayList<Stad> sortInfecties(ArrayList<Stad> steden) {
        steden.sort(Comparator.comparing(Stad::getInfecties).reversed().thenComparing(Stad::getNaam));
        return steden;
    }
    public void setAfleggen(ArrayList<String> beurt) {
        if(beurt.size()>0) {
            System.out.println("enabled");
            //this.afleggen.setEnabled(true);
        }
        else {
            //afleggen.setEnabled(false);
            System.out.println("disabled");
        }
    }





    public PandemieGui() throws SQLException {
        spelersPanel.setLayout(new BoxLayout(spelersPanel,BoxLayout.Y_AXIS));
        Spelverloop spelverloop = new Spelverloop();
        handTrekButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    try {
                        String getrokkenNaam = spelverloop.trekStad().getNaam();
                        JPanel test = new Kaart().getStadPanel(getrokkenNaam);
                        selectedJPanels.add(test);

                        test.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {

                                Stad stad;

                                super.mouseClicked(e);
                                try {
                                    stad = datalayer.getStadMetNaam(getrokkenNaam);
                                    if (selectedSteden.contains(getrokkenNaam)) {
                                        selectedSteden.remove(getrokkenNaam);
                                        TitledBorder border = new TitledBorder(BorderFactory.createTitledBorder(new LineBorder(stad.toColor(),1), getrokkenNaam));
                                        border.setTitleJustification(TitledBorder.CENTER);
                                        test.setBorder(border);

                                    } else {
                                        selectedSteden.add(getrokkenNaam);
                                        TitledBorder border = new TitledBorder(BorderFactory.createTitledBorder(new LineBorder(stad.toColor(),5), getrokkenNaam));
                                        border.setTitleJustification(TitledBorder.CENTER);
                                        test.setBorder(border);
                                    }
                                    System.out.println(selectedSteden);
                                    if (selectedSteden.size() > 0) {
                                        afleggen.setEnabled(true);
                                    } else {
                                        afleggen.setEnabled(false);
                                    }
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }

                            }
                        });

                        spelerPanels.get(0).getKaartenPanel().add(test).revalidate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
            }
        });

        afleggen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aflegstapel.addAll(selectedSteden);
                selectedSteden.clear();
                spelerPanels.get(0).getKaartenPanel().remove(0);
                System.out.println(spelerPanels.get(0));
                spelerPanels.get(0).getKaartenPanel().revalidate();

            }
        });
        infectieStapel.setLayout(new BorderLayout());
        infectieTrekButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String getrokkenNaam = spelverloop.trekStad().getNaam();
                    JPanel infectieKaart = new Kaart().getStadPanel(getrokkenNaam);
                    infectieStapel.add(infectieKaart).revalidate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() throws SQLException {
        int aantalSpelers =0;
        DataLayerJDBC dataLayer = new DataLayerJDBC("pandemie", true);
        try {
            aantalSpelers =  dataLayer.getNewSpelers().toArray().length;
        } catch (SQLException ex) {
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        spelerPanels=new ArrayList<>(3);
        spelersPanel=new JPanel();

        for(int i=1;i<=aantalSpelers;i++){//pas deze lijn aan

            //Overloop in deze lus je spelers. Let er ook op dat er ook minder dan 3 spelers kunnen meespelen
            SpelerPanel sp = new SpelerPanel(i);
            spelersPanel.add(sp.getSpelerPanel());
            spelerPanels.add(sp);
        }
    }





    public static void main(String[] args) {
        JFrame myFrame = new JFrame();
        myFrame.setTitle("Pandemie kaartspel - < >");
        while(myFrame.getTitle().contains("student"))
            JOptionPane.showMessageDialog(myFrame,"Voeg in de titel van het frame je eigen naam toe \nom deze melding te vermijden.","Titel nog niet aangepast", JOptionPane.WARNING_MESSAGE);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setContentPane(new StartGUI(myFrame).getPanelMain());
        //myFrame.pack();
        myFrame.setSize(800, 800);
        myFrame.setVisible(true);
    }
    public JPanel getMainPanel () {
        return this.mainPanel;
    }
}