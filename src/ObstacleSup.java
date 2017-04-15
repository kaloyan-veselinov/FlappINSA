public class ObstacleSup extends Obstacle {
	
	public ObstacleSup (int gameMode, double x, double y, double espacement, int width, int heigth){
		super ("./res/pipe/pipe_sup_"+gameMode+".png", x, y-espacement,espacement, width, heigth);
		this.y-=h;
	}
	
	@Override
	public void update(double x, double y){
		super.update(x,y);
		this.y-=espacement+h;
	}

}
