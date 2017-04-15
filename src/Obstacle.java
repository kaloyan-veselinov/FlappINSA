public abstract class Obstacle extends Objet {
	protected double espacement;
	
	public Obstacle (String image, double x, double y, double espacement, int width, int heigth){
		super (image, x, y,width, heigth);
		ypp=0;
		xpp=0;
		yp=0;
		xp=-3.0;
		this.espacement=espacement;
	}

	public boolean move (double dt){
		super.move(dt);
		return x<-image.getWidth();
	}
	
	public void update(double x, double y){
		this.x=x;
		this.y=y;
	}
	
}
