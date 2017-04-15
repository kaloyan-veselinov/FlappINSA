import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public abstract class Objet {
	
	protected BufferedImage image;
	
	//Parametres de mouvement
	protected double x; //abcisse de l'objet
	protected double y; //ordonnee de l'objet
	protected double l; //largeur de l'objet
	protected double h; //hauteur de l'objet
	protected double xp; //vitesse sur x
	protected double xpp; //acceleration sur x
	protected double yp; //vitesse sur y
	protected double ypp; //acceleration sur y
	
	//Parametres de la fenetre source
	protected int width; //largeur de la fenetre de dessin
	protected int heigth; //hauteur de la fenetre de dessin

	public Objet(String nomImage, double x, double y, int width, int heigth) {
		try {
			image= ImageIO.read(new File(nomImage)); 
		} catch(Exception e) {
			System.out.print(e.getMessage());
		}
		this.image.setAccelerationPriority(1);
		this.heigth=heigth;
		this.width=width;
		this.x = x;
		this.y = y;
		this.l=image.getWidth();
		this.h=image.getHeight();
	}
	
	/**
	 * La methode dessine() permet de dessiner l'objet dans la fenetre definie 
	 * @param g l'objet Graphics2D permettant de dessiner
	 * @param fj la fenetre dans laquelle on dessine
	 */
	public void dessine(Graphics2D g, JFrame fj) {
		g.drawImage(image,(int)x,(int)y,fj);
	}
	
	/**
	 * La methode move() permet de mettre a jour les coordonnes de l'objet
	 * @param time le temps de mouvement
	 * @return condition de test specifique a chaque objet
	 */
	public boolean move(double time) {
		xp+=xpp*time;
		yp+=ypp*time;
		x+=xp*time; 
		y+=yp*time;
		return true;
	}
	
	/**
	 * La methode collision() verifie si deux objets sont entres en collision
	 * @param obj l'autre objet
	 * @return variable de type boolean, vrai si les deux objets sont en collision
	 */
	public boolean collision(Objet obj){
    	Rectangle r1 = new Rectangle((int)this.x,(int)this.y,(int)this.l,(int)this.h);
    	Rectangle r2 = new Rectangle((int)obj.x,(int)obj.y,(int)obj.l,(int)obj.h);
    	return r1.intersects(r2);
    }

}



