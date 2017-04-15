import java.awt.Graphics2D;

import javax.swing.JFrame;

public abstract class ObjetCeleste extends Objet {
	protected double m; //la masse
	protected final double nullAvoider=0.1; //permet d'eviter une distance nulle entre les deux objets
	protected String nom; //le nom de la planete
	protected final static double G=6.67408e-11; //la constante universelle de gravite
	protected double scale; //l'echelle de dessin
	protected double fx, fy; //les forces selon x et y
	protected double imageScale;
	
	public ObjetCeleste(String nomObjet, double x, double y, double xp, double yp, double m, double scale, int width, int heigth, double imageScale){
		super("./res/planets/"+nomObjet+".png",x,y,width,heigth);
		this.scale=scale;
		this.imageScale=imageScale;
		this.nom=nomObjet;
		this.m=m;
		this.x-=l/(2.0*scale);
		this.y-=h/(2.0*scale);
		this.xpp=0;
		this.ypp=0;
		this.xp=xp;
		this.yp=yp;
	}
	
	/**
	 * Methode calculant la distance entre deux objets celestes
	 * @param p la planete a laquelle on calcule la distance
	 * @return la distance entre les deux objets
	 */
	public double getDistance(ObjetCeleste p){
		return (Math.sqrt(Math.pow(p.x-this.x,2)+Math.pow(p.y-this.y,2))+nullAvoider);
	}
	
	/**
	 * Methode qui calcule la force gravitationnelle (divisee par la distance) entre deux objets celestes
	 * @param p l'autre planete
	 * @return la force
	 */
	public double getForcePerMeter(ObjetCeleste p){
		return G*p.m*this.m/Math.pow(this.getDistance(p), 3);
	}
	
	/**
	 * Methode qui calcule la projection de la force gravitationnelle selon x
	 * @param p l'autre corps celeste
	 * @return la force selon x
	 */
	public double getForceX(ObjetCeleste p){
		return this.getForcePerMeter(p)*(p.x-this.x);
	}
	
	/**
	 * Methode qui calcule la projection de la force gravitationnelle selon y
	 * @param p l'autre corps celeste
	 * @return la force selon y
	 */
	public double getForceY(ObjetCeleste p){
		return this.getForcePerMeter(p)*(p.y-this.y);
	}
	
	/**
	 * Methode qui ajoute la contribution de l'objet celeste p a la force totale subie par cet objet
	 * @param p l'autre corps celeste
	 */
	public void updateForce(ObjetCeleste p){
		this.fx+=this.getForceX(p);
		this.fy+=this.getForceY(p);
	}
	
	/**
	 * Methode qui permet de reinitialiser la force totale en fin de boucle
	 */
	public void resetForce(){
		this.fx=0;
		this.fy=0;
	}
	
	@Override
	public boolean move(double dt){
		this.xpp=this.fx/this.m;
		this.ypp=this.fy/this.m;
		this.xp+=dt*this.xpp;
		this.yp+=dt*this.ypp;
		this.x+=dt*this.xp;
		this.y+=dt*this.yp;
		resetForce();
		return true;
	}
	
	@Override
	public void dessine(Graphics2D g, JFrame jf){
		g.scale(imageScale, imageScale);
		g.drawImage(image, (int)(x*scale/imageScale), (int)(y*scale/imageScale), jf);
		g.scale(1.0/imageScale, 1.0/imageScale);
	}
}
