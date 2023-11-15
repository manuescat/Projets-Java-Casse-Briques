import java.awt.*;

public class Brique extends Rectangle{
    public int largeur;
    public int hauteur;


    public Brique(Color couleur, int x, int y, int largeur, int hauteur) {
        super(couleur, x, y);
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

}
