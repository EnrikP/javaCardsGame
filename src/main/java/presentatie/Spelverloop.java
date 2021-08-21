package presentatie;

import data.DataLayerJDBC;
import logica.Stad;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Spelverloop {
    private ArrayList<Stad> aftrekstapel;
    private ArrayList<Stad> aflegstapel;
    DataLayerJDBC datalayer = new DataLayerJDBC("pandemie",true);
    private ArrayList<JPanel>selectedPanels = new ArrayList<>();

    ArrayList<String> beurt = new ArrayList<>();


    public Spelverloop() throws SQLException {
        this.aftrekstapel = (ArrayList<Stad>) datalayer.getStedenList();
        this.aflegstapel = new ArrayList<Stad>();
    }

    public ArrayList<String> getBeurt() {
        return beurt;
    }

    public void setBeurt(ArrayList<String> beurt) {
        this.beurt = beurt;
    }

    public ArrayList<Stad> getAflegstapel() {
        return aflegstapel;
    }

    public ArrayList<Stad> getAftrekstapel() {
        return aftrekstapel;
    }

    public void addToAflegstapel(Stad stad) {
        aflegstapel.add(stad);
    }

    public void removeFromAftrekstapel(Stad stad) {
        aftrekstapel.remove(stad);
    }

    public void setAftrekstapel(ArrayList<Stad>stad) {
        this.aftrekstapel = stad;
    }

    public void setAflegstapel(ArrayList<Stad>stad) {
        this.aflegstapel = stad;
    }

    public  Stad trekStad() {
        ArrayList<Stad> aflegstapel = getAflegstapel();
        ArrayList<Stad> aftrekstapel = getAftrekstapel();
        if (aftrekstapel.size()>0) {
            int getrokkenKaart = (int) (Math.random() * 25 + 1);
            for (Stad stad : aftrekstapel) {
                if (getrokkenKaart != stad.getId()) {
                    addToAflegstapel(stad);
                    removeFromAftrekstapel(stad);
                    return stad;
                }
            }
        }
        shuffle();
        throw new IllegalArgumentException("aftrekstapel is op aflegstapel gelegd & geschudt");
    }

    public void shuffle() {
        setAftrekstapel(getAflegstapel());
        this.aflegstapel.clear();
    }
//Hier worden de kaarten getrokken
    public Stad infectieBeurtTrek1 () {
        Stad stad = trekStad();
        return new Stad(stad.getId(),stad.getNaam(),stad.getKleur(),stad.getX(),stad.getY(),3);
    }

    public Stad infectieBeurtTrek2 () {
        Stad stad = trekStad();
        return new Stad(stad.getId(),stad.getNaam(),stad.getKleur(),stad.getX(),stad.getY(),2);
    }

    public Stad infectieBeurtTrek3 () {
        Stad stad = trekStad();
        return new Stad(stad.getId(),stad.getNaam(),stad.getKleur(),stad.getX(),stad.getY(),1);
    }

    public int[] beurtTrek4 () {
        int [] trekking = new int[2];
        trekking[0] = trekStad().getId();
        trekking[1] = 4;
        return trekking;
    }


    public void setSelectedPanels(ArrayList<JPanel> selectedPanels) {
        this.selectedPanels = selectedPanels;
    }

    public ArrayList<JPanel> getSelectedPanels() {
        return selectedPanels;
    }
}
