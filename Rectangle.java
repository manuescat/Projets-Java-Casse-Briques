import java.awt.*;

public class Rectangle extends Sprite {

    public Rectangle(Color couleur, int x, int y) {
        super(couleur, x, y);
    }

    @Override
    public void dessiner(Graphics g) {
        g.setColor(getCouleur());
        g.fillRect(getX(),getY(), 200, 25);
    }
}
