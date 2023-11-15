import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;

//La classe principale hérite de Canvas, un composant permettant de réaliser des dessins
public class CasseBrique extends Canvas implements KeyListener {

    //largeur de l'écran
    private int largeur;
    //hauteur de l'écran
    private int hauteur;
    //variables bonus
    int score = 0;
    int level = 1;
    int vie = 3;
    //booleéns pour le déplacement de la barre
    boolean droite;
    boolean gauche;

    Balle balle = new Balle(Color.RED, 120, 350, 10, -2, -2);
    Balle balle2 = new Balle(Color.RED, 120, 350, 10, -4, -4);
    Barre barre = new Barre(Color.GREEN, 100, 490);
    private ArrayList<Brique> listeBrique = new ArrayList<>();

    public CasseBrique(int largeur, int hauteur) {

        this.largeur = largeur;
        this.hauteur = hauteur;

        JFrame fenetre = new JFrame("Casse brique");
        JPanel panneau = (JPanel) fenetre.getContentPane();
        panneau.setPreferredSize(new Dimension(this.largeur, this.hauteur));
        setBounds(10, 10, largeur, hauteur);
        panneau.add(this);
        fenetre.pack();
        fenetre.setResizable(false);
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenetre.requestFocus();
        fenetre.addKeyListener(this);
        fenetre.setFocusable(true);
        fenetre.setFocusTraversalKeysEnabled(false);

        //On indique que le raffraichissement de l'ecran doit être fait manuellement.
        createBufferStrategy(2);
        setIgnoreRepaint(true);
        this.setFocusable(false);

        //Ajout des 50 briques à la liste (5 lignes et 10 colonnes)
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 16; j++) {
                Brique brique = new Brique(Color.white, j * 50 - 50, 30 * i + 30, 50, 30);
                listeBrique.add(brique);
            }
        }
        //On appel la méthode contenant la boucle infinie qui va raffraichir notre écran
        demarrer();
    }

    public void demarrer() {

        while (true) {

            Graphics dessin = getBufferStrategy().getDrawGraphics();

            //création du fond noir, de la balle rouge et de la barre verte
            dessin.setColor(Color.BLACK);
            dessin.fillRect(0, 0, this.largeur, this.hauteur);
            balle.dessiner(dessin); //méthode dessiner de Sphere
            barre.dessiner(dessin);//méthode dessiner de Rectangle

            //création des briques à partir de get(index)
            for (int i = 0; i < listeBrique.size(); i++) {
                dessin.setColor(Color.white);
                dessin.fillRect(listeBrique.get(i).getX(), listeBrique.get(i).getY(), listeBrique.get(i).getLargeur(), listeBrique.get(i).getHauteur());
                dessin.setColor(Color.BLACK);
                dessin.drawRect(listeBrique.get(i).getX(), listeBrique.get(i).getY(), listeBrique.get(i).getLargeur(), listeBrique.get(i).getHauteur());
            }

            //mise en mouvement de la balle et conditions de rebonds
            balle.setX(balle.getX() + balle.getVitesseHorizontal());
            balle.setY(balle.getY() + balle.getVitesseVertical());
            if (balle.getX() < 0) {
                balle.inverseVitesseHorizontale();
            }
            if (balle.getY() < 0) {
                balle.inverseVitesseVerticale();
            }
            if (balle.getX() > (this.largeur - balle.getRayon() * 2)) {
                balle.inverseVitesseHorizontale();
            }

            //rebond sur la barre de la balle
            if (new Ellipse2D.Double(balle.getX(), balle.getY(), balle.getRayon() * 2, balle.getRayon() * 2).intersects(new Rectangle(barre.getX(), 490, 200, 25))) {
                balle.inverseVitesseVerticale();
            }

            //déplacement de la barre à droite et à gauche
            if (droite) {
                if (barre.getX() >= 600) {
                    barre.setX(600);
                } else {
                    barre.setX(barre.getX() + 10);
                }
            }
            if (gauche) {
                if (barre.getX() < 10) {
                    barre.setX(0);
                } else {
                    barre.setX(barre.getX() - 10);
                }
            }

            //boucle pour vérifier si la balle rencontre une brique => suppression brique avec iterateur
            Iterator<Brique> itr = listeBrique.iterator();
            while (itr.hasNext()) {
                Brique liste =itr.next();
                if (new Ellipse2D.Double(balle.getX(), balle.getY(), balle.getRayon() * 2, balle.getRayon() * 2).intersects(new Rectangle(liste.getX(), liste.getY(), liste.getLargeur(), liste.getHauteur()))){
                    itr.remove();
                    balle.inverseVitesseVerticale();
                    score = score + 5;
                    break; //la boucle s'arrête dès qu'une brique est touchée
                }
            }

            //Affichage du score
            dessin.setColor(Color.white);
            dessin.setFont(new Font("serif", Font.BOLD, 25));
            dessin.drawString("Score = " + score, 370, 30);

            //Affichage des vies
            dessin.setColor(Color.white);
            dessin.setFont(new Font("serif", Font.BOLD, 25));
            dessin.drawString("Vies = " + vie, 30, 30);

            //Affichage du game over et gestion des vies
            //si la balle passe sous la hauteur du cadre + 20 on perd une vie
            if ((balle.getY() > this.hauteur + 20) || (balle2.getY() > this.hauteur + 20)) {
                //Si le nombre de vies est à 0, on affiche game over
                if (vie < 2) {
                    vie = 0;
                    dessin.setColor(Color.white);
                    dessin.setFont(new Font("serif", Font.BOLD, 25));
                    dessin.drawString("GAME OVER", 170, 400);
                    balle.setVitesseVertical(0);
                    balle.setVitesseHorizontal(0);
                    balle.setCouleur(Color.black);
                    balle2.setVitesseVertical(0);
                    balle2.setVitesseHorizontal(0);
                    balle2.setCouleur(Color.black);
                } else {    //sinon on retire une vie et on replace la balle
                    vie--;
                    balle.setY(290 + balle.getVitesseVertical());
                    balle.inverseVitesseVerticale();
                    balle2.setY(290 + balle.getVitesseVertical());
                    balle2.inverseVitesseVerticale();
                }
            }

            //Affichage du level
            dessin.setColor(Color.white);
            dessin.setFont(new Font("serif", Font.BOLD, 25));
            dessin.drawString("Level = " + level, 200, 30);

            //Fin du jeu
            if (score == 250) {
                dessin.setColor(Color.yellow);
                dessin.setFont(new Font("serif", Font.BOLD, 25));
                dessin.drawString("YOU WIN", 200, 400);
                //on arrête les balles et on les met en noir
                balle.setVitesseVertical(0);
                balle.setVitesseHorizontal(0);
                balle.setCouleur(Color.black);
                balle2.setVitesseVertical(0);
                balle2.setVitesseHorizontal(0);
                balle2.setCouleur(Color.black);
            }

            //BONUS - augmentation vitesse balle avec level (2 à 4) puis ajout d'une balle et diminuation de la vitesse (5)
            if (((score >= 50) && (score < 100))&&vie>0) { //level2
                level = 2;
                if (balle.getVitesseHorizontal() > 0) {
                    balle.setVitesseHorizontal(3);
                } else {
                    balle.setVitesseHorizontal(-3);
                }
                if (balle.getVitesseVertical() > 0) {
                    balle.setVitesseVertical(3);
                } else {
                    balle.setVitesseVertical(-3);
                }
            }
            if (((score >= 100) && (score < 150))&&vie>0) { //level3
                level = 3;
                if (balle.getVitesseHorizontal() > 0) {
                    balle.setVitesseHorizontal(4);
                } else {
                    balle.setVitesseHorizontal(-4);
                }
                if (balle.getVitesseVertical() > 0) {
                    balle.setVitesseVertical(4);
                } else {
                    balle.setVitesseVertical(-4);
                }
            }
            if (((score >= 150) && (score < 200))&&vie>0) { //level4
                level = 4;
                if (balle.getVitesseHorizontal() > 0) {
                    balle.setVitesseHorizontal(5);
                } else {
                    balle.setVitesseHorizontal(-5);
                }
                if (balle.getVitesseVertical() > 0) {
                    balle.setVitesseVertical(5);
                } else {
                    balle.setVitesseVertical(-5);
                }
            }
            if (((score >= 200)&&(score<250))&&vie>0) { //level5
                level = 5;
                if (balle.getVitesseHorizontal() > 0) {
                    balle.setVitesseHorizontal(4);
                } else {
                    balle.setVitesseHorizontal(-4);
                }
                if (balle.getVitesseVertical() > 0) {
                    balle.setVitesseVertical(4);
                } else {
                    balle.setVitesseVertical(-4);
                }
                //ajout conditions balle2
                if (balle2.getX() < 0) {
                    balle2.inverseVitesseHorizontale();
                }
                if (balle2.getY() < 0) {
                    balle2.inverseVitesseVerticale();
                }
                if (balle2.getX() > (this.largeur - balle.getRayon() * 2)) {
                    balle2.inverseVitesseHorizontale();
                }
                //ajout de la balle 2
                balle2.dessiner(dessin);
                balle2.setX(balle2.getX() + balle2.getVitesseHorizontal());
                balle2.setY(balle2.getY() + balle2.getVitesseVertical());

                //rebond sur la barre de la balle2
                if (new Ellipse2D.Double(balle2.getX(), balle2.getY(), balle2.getRayon() * 2, balle2.getRayon() * 2).intersects(new Rectangle(barre.getX(), 490, 100, 25))) {
                    balle2.inverseVitesseVerticale();
                }
                //boucle pour vérifier si la balle2 rencontre une brique => suppression brique avec iterateur
                Iterator<Brique> itr2 = listeBrique.iterator();
                while (itr2.hasNext()) {
                    Brique liste =itr2.next();
                    if (new Ellipse2D.Double(balle2.getX(), balle2.getY(), balle2.getRayon() * 2, balle2.getRayon() * 2).intersects(new Rectangle(liste.getX(), liste.getY(), liste.getLargeur(), liste.getHauteur()))){
                        itr2.remove();
                        balle2.inverseVitesseVerticale();
                        score = score + 5;
                        break;//la boucle s'arrête dès qu'une brique est touchée
                    }
                }
            }
            dessin.dispose();
            getBufferStrategy().show();

                //pause de quelques milisecondes afin de n'effectuer que 60 raffraichissements par seconde
            try {
                Thread.sleep(1000 / 60);
            } catch (Exception e) {
            }
        }
    }

        //méthode principale de notre programme
    public static void main (String[]args){
        new CasseBrique(800, 500);
    }

    @Override
    public void keyTyped (KeyEvent e){
    }

    @Override
    public void keyPressed (KeyEvent e){
        if (e.getKeyCode() == 39) {
            droite = true;
        }
        if (e.getKeyCode() == 37) {
            gauche = true;
        }
    }

    @Override
    public void keyReleased (KeyEvent e){
        if (e.getKeyCode() == 39) {
            droite = false;
        }
        if (e.getKeyCode() == 37) {
            gauche = false;
        }
    }
}


