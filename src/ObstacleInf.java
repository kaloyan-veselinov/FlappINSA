public class ObstacleInf extends Obstacle {
	
	public ObstacleInf (int gameMode, double x, double y,double espacement, int width, int heigth){
		super ("./res/pipe/pipe_inf_"+gameMode+".png", x, y+espacement,espacement, width, heigth);
	}
	
	@Override
	public void update(double x, double y){
		super.update(x,y);
		this.y+=espacement;
	}
}
