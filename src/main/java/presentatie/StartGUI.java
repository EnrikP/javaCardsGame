package presentatie;

import data.DataLayerJDBC;
import logica.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartGUI extends JPanel{
    private JPanel startpanel;
    private JPanel panelMain;
    private JTextField naamText1;
    private JTextField naamText2;
    private JTextField naamText3;

    private JComboBox comboBoxSpelerRol1;
    private JComboBox comboBoxSpelerRol2;
    private JComboBox comboBoxSpelerRol3;

    private JButton buttonStart;
    private JLabel errorLabel;
    private DataLayerJDBC dataLayerJDBC;
    private ArrayList<Rol>rollen;
    private ArrayList<Stad>steden;
    private ArrayList<String>rollenNaam;
    private ArrayList<String>alleRollenSpelers;

    private JComboBox comboBoxStartPositie;
    private Speler speler;
    private StartGUI startGUI;
    private SpelInfo spelInfo;
    private ArrayList<Speler> spelers;

    public JPanel getPanelMain() {
        return this.panelMain;
    }

    public StartGUI(JFrame parentFrame){

        try {
            DataLayerJDBC dataLayerJDBC = new DataLayerJDBC("pandemie", true);
            rollenNaam = (ArrayList<String>) dataLayerJDBC.getRollenNaamList();
            rollen = (ArrayList<Rol>) dataLayerJDBC.getRollenList();
            steden = (ArrayList<Stad>) dataLayerJDBC.getStedenList();
        }catch (SQLException ex){
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }



        comboBoxSpelerRol1.setModel(new DefaultComboBoxModel<>(rollenNaam.toArray()));
        comboBoxSpelerRol2.setModel(new DefaultComboBoxModel<>(rollenNaam.toArray()));
        comboBoxSpelerRol3.setModel(new DefaultComboBoxModel<>(rollenNaam.toArray()));
        comboBoxSpelerRol1.setSelectedItem(rollenNaam.get(0));
        comboBoxSpelerRol2.setSelectedItem(rollenNaam.get(1));
        comboBoxSpelerRol3.setSelectedItem(rollenNaam.get(2));

        comboBoxSpelerRol1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUniekeRolSpelers(comboBoxSpelerRol1);
            }
        });
        comboBoxSpelerRol2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUniekeRolSpelers(comboBoxSpelerRol2);
            }
        });
        comboBoxSpelerRol3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUniekeRolSpelers(comboBoxSpelerRol3);
            }
        });




        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (naamText1.getText().equals("") && naamText2.getText().equals("") && naamText3.getText().equals("")){
                    errorLabel.setText("Je kan het spel niet beginnen met 0 spelers.");
                }
                else {
                    errorLabel.setText("");
                    stuurSpelEnSpelersNaarDB();
                    try {
                        goToGame(parentFrame);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            }
        });
    }




    public void setUniekeRolSpelers(JComboBox comboBox){
        ArrayList<JComboBox> comboBoxesSpelerRol = new ArrayList<>();
        comboBoxesSpelerRol.add(comboBoxSpelerRol1);
        comboBoxesSpelerRol.add(comboBoxSpelerRol2);
        comboBoxesSpelerRol.add(comboBoxSpelerRol3);
        comboBoxesSpelerRol.remove(comboBox);
        alleRollenSpelers = new ArrayList<>(rollenNaam);
        for (int i = 0; i < comboBoxesSpelerRol.size(); i++){
            alleRollenSpelers.remove(comboBoxesSpelerRol.get(i).getSelectedItem());
        }
        for (int i = 0; i < comboBoxesSpelerRol.size(); i++){
            if (comboBox.getSelectedItem() == comboBoxesSpelerRol.get(i).getSelectedItem()){
                comboBoxesSpelerRol.get(i).setSelectedItem(alleRollenSpelers.get(0));
            }
        }
    }
    public void goToGame(JFrame parentFrame) throws SQLException {
        JFrame frame = new JFrame("Pandemie - <>");
        if(frame.getTitle().contains("student"))
            JOptionPane.showMessageDialog(frame,"Voeg in de titel van het frame je eigen naam toe \nom deze melding te vermijden.","Titel nog niet aangepast",JOptionPane.WARNING_MESSAGE);
        frame.setContentPane(new PandemieGui().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        parentFrame.dispose();
    }


    public void stuurSpelEnSpelersNaarDB(){
        try{
            DataLayerJDBC dataLayerJDBC = new DataLayerJDBC("pandemie", true);
            Spel spel = new Spel(LocalDateTime.now());
            spelers = (ArrayList<Speler>) dataLayerJDBC.getSpelerList();

            dataLayerJDBC.insertWithPreparedStatementSpelStart(spel);
            int spelid = dataLayerJDBC.getLastGeneratedKeySpellen();
            spel.setId(spelid);
            String speler1naam = naamText1.getText();
            if (!speler1naam.equals("")){
                String speler1RolNaam = (String)comboBoxSpelerRol1.getSelectedItem();
                int speler1RolId = getRolIdFromNaam(speler1RolNaam);
                Speler speler1 = new Speler(spelid, speler1RolId, speler1naam);
                dataLayerJDBC.insertWithPreparedStatement(speler1);
                spelers.add(speler1);
            }
            String speler2naam = naamText2.getText();
            if (!speler2naam.equals("")){
                String speler2RolNaam = (String)comboBoxSpelerRol2.getSelectedItem();
                int speler2RolId = getRolIdFromNaam(speler2RolNaam);
                Speler speler2 = new Speler(spelid, speler2RolId, speler2naam);
                dataLayerJDBC.insertWithPreparedStatement(speler2);
                spelers.add(speler2);
            }
            String speler3naam = naamText3.getText();
            if (!speler3naam.equals("")){

                String speler3RolNaam = (String)comboBoxSpelerRol3.getSelectedItem();
                int speler3RolId = getRolIdFromNaam(speler3RolNaam);
                Speler speler3 = new Speler(spelid, speler3RolId, speler3naam);
                dataLayerJDBC.insertWithPreparedStatement(speler3);
                spelers.add(speler3);
            }

        }

        catch (SQLException ex){
            Logger.getLogger(DataLayerJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int getRolIdFromNaam(String rolNaam){
        int rolId = 0;
        for (int i = 0; i < rollen.size(); i++){
            if (rolNaam.equals(rollen.get(i).getNaam())){
                rolId = rollen.get(i).getId();
            }
        }
        return rolId;
    }



}
