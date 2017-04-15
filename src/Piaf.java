
class Piaf extends Objet {
	
	public Piaf(double x, double y,int selectPiaf, int width, int heigth){
		super("./res/Piaf/Piaf_"+selectPiaf+".png",x,y,width, heigth);
		xpp=0;
		xp=0;
		ypp=0.4;
	}
	
	@Override
	public boolean move(double dt){
		super.move(dt);
		return (y>heigth || y<0);
	}
	
	public void jump(){
		this.yp=-7;
	}

}
