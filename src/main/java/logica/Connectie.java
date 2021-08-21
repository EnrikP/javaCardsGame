package logica;

/**
 * @author
 */

public class Connectie {
    private Stad stad1;
    private Stad stad2;

    public Connectie(Stad stad1, Stad stad2){
        this.stad1 = stad1;
        this.stad2 = stad2;
    }

    public Stad getStad1() {
        return stad1;
    }

    public Stad getStad2() {
        return stad2;
    }
}
