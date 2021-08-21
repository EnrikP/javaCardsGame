package logica;

public class Rol {
    private int id;
    private String naam;
    private String beschrijving;

    public int getId() {
        return id;
    }

    public String getNaam() {
        return this.naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public Rol(int id, String naam, String beschrijving) {
        this.id = id;
        this.naam = naam;
        this.beschrijving = beschrijving;
    }

    Rol(Rol rol) {
        this.id = rol.getId();
        this.naam = rol.getNaam();
        this.beschrijving = rol.getBeschrijving();
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                '}';
    }
}
