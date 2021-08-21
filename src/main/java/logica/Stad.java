package logica;


import java.awt.*;
import java.util.ArrayList;

public class Stad  {
    private int id;
    private String naam;
    private StadKleur kleur;
    private int infecties;
    private ArrayList<Speler>spelers;
    private int ziekteId;
    private int x;
    private int y;

    public Stad(int id, String naam, StadKleur kleur,  int x, int y,int infecties) {
        this.x = x;
        this.y = y;

        this.id = id;
        this.naam = naam;
        this.kleur = kleur;
        this.infecties = infecties;
        this.spelers = spelers;
        this.ziekteId = ziekteId;

    }

    public Stad(Stad stad) {
        this.x = stad.getX();
        this.y = stad.getY();
        this.id = stad.getId();
        this.naam = stad.getNaam();
        this.kleur = stad.getKleur();
        this.infecties = stad.getInfecties();
        this.ziekteId = stad.getZiekteId();
    }

    public Stad(int id, String naam, StadKleur kleur, int x, int y) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.naam = naam;
        this.kleur = kleur;
    }

    public Stad() {

    }

    public int getId() {
        return id;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public String getNaam() {
        return naam;
    }

    public StadKleur getKleur() {
        return kleur;
    }

    public ArrayList<Speler> getSpelers() {
        return spelers;
    }

    public int getInfecties() {
        return infecties;
    }

    public int getZiekteId() {
        return ziekteId;
    }

    public void addSpeler(Speler speler){
        boolean contains = false;
        for (int i = 0 ; i < spelers.size() ; i++) {
            if(speler.equals(spelers.get(i))){
                contains = true;
            }
        }
        if (!contains){
            spelers.add(speler);
        }
    }

    public Color toColor () {
        String kleur = this.getKleur().toString();
        switch (kleur) {
            case "BLAUW": return Color.BLUE;
            case "ROOD": return Color.RED;
            case "GEEL": return Color.YELLOW;
            case "ZWART": return Color.BLACK;
        }
        throw new IllegalArgumentException("Dit kleur is niet mogelijk");
    }

    @Override
    public String toString() {
        return naam + "- "+kleur +"- "+infecties;
    }
}
