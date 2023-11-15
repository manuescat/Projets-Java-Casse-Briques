import java.awt.*;

public class Balle extends Sphere{
     int vitesseHorizontal;
     int vitesseVertical;

    public Balle(Color couleur, int x, int y, int rayon, int vitesseHorizontal, int vitesseVertical) {
        super(couleur, x, y, rayon);
        this.vitesseHorizontal = vitesseHorizontal;
        this.vitesseVertical = vitesseVertical;
    }

    public int getVitesseHorizontal() {
        return vitesseHorizontal;
    }

    public void setVitesseHorizontal(int vitesseHorizontal) {
        this.vitesseHorizontal = vitesseHorizontal;
    }

    public int getVitesseVertical() {
        return vitesseVertical;
    }

    public void setVitesseVertical(int vitesseVertical) {
        this.vitesseVertical = vitesseVertical;
    }

    void inverseVitesseHorizontale (){
        vitesseHorizontal= -vitesseHorizontal;
    }

    void inverseVitesseVerticale (){
        vitesseVertical= -vitesseVertical;
    }
}
