import java.awt.*;

public class Sphere extends Sprite {
    private int rayon;

    public Sphere(Color couleur, int x, int y, int rayon) {
        super(couleur, x, y);
        this.rayon = rayon;
    }

    @Override
    public void dessiner(Graphics g) {
        g.setColor(getCouleur());
        g.fillOval(getX(),getY(),rayon*2,rayon*2);
    }

    public int getRayon() {
        return rayon;
    }

    public void setRayon(int rayon) {
        this.rayon = rayon;
    }
}
