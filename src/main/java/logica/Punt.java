package logica;

public class Punt {
    private int x;
    private int y;

    public Punt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Punt() {
        this.x = 0;
        this.y = 0;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double berekenAfstand (Punt punt) {
        return Math.sqrt(Math.pow(this.x - punt.getX(),2)+Math.pow(this.y + punt.getY(),2));
    }
}
