
public class Planete extends ObjetCeleste {
	
	public Planete(String nomPlanete, double xCentre, double rOrbite, double vInit, double m, double scale, int width, int heigth, double imageScale){
		super(nomPlanete,(xCentre/(scale))+ (rOrbite*1e3),heigth/(2.0*scale),0,vInit,m,scale,width, heigth, imageScale);
	}
}
