package logica;

public class Speler {
    private int spel_id;
    private int rol_id;
    private String naam;
    private Stad locatie;

    public Speler(int spel_id, int rol_id, String naam, Stad locatie) {
        this.spel_id = spel_id;
        this.rol_id = rol_id;
        this.naam = naam;
        this.locatie = locatie;
    }
    public Speler (int spel_id, int rol_id, String naam) {
        this.spel_id = spel_id;
        this.rol_id = rol_id;
        this.naam = naam;
    }
    public int getspel_id() {
        return spel_id;
    }



    public int getrol_id() {
        return rol_id;
    }

    public String getNaam() {
        return naam;
    }

    public Stad getLocatie() {
        return locatie;
    }

    public void setLocatie(Stad locatie) {
        this.locatie = locatie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speler speler = (Speler) o;
        return spel_id == speler.spel_id;
    }

    @Override
    public String toString() {
        return "Speler{" +
                "spel_id=" + spel_id +
                ", rol_id=" + rol_id +
                ", naam='" + naam + '\'' +
                ", locatie=" + locatie +
                '}';
    }
}
