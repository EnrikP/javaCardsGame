package logica;

import java.time.LocalDateTime;

public class Spel {
    private int id;
    private LocalDateTime start;
    private LocalDateTime einde;
    private boolean gewonnen;

    public Spel(int id, LocalDateTime start, LocalDateTime einde, boolean gewonnen) {
        this.id = id;
        this.start = start;
        this.einde = einde;
        this.gewonnen = gewonnen;
    }

    public Spel(LocalDateTime start, LocalDateTime einde, boolean gewonnen){
        this.start = start;
        this.einde = einde;
        this.gewonnen = gewonnen;
    }

    public Spel(LocalDateTime start) {
        this.start = start;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGewonnen() {
        return gewonnen;
    }

    public LocalDateTime getEinde() {
        return einde;
    }

    public void setEinde(LocalDateTime einde) {
        this.einde = einde;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setGewonnen(boolean gewonnen) {
        this.gewonnen = gewonnen;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }


}
