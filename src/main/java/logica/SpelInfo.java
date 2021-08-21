package logica;

import java.util.ArrayList;

public class SpelInfo {
    private ArrayList<Speler> spelers;
    private Stad startpositie;

    public SpelInfo(ArrayList<Speler> spelers, Stad start) {
        this.spelers = spelers;
        this.startpositie = start;
    }

    public ArrayList<Speler> getSpelers() {
        return spelers;
    }

    public Stad getStartpositie() {
        return startpositie;
    }
}
