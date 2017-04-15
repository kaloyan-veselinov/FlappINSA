import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class FlappySeaside extends FenetreJeux {
	
	public FlappySeaside(int piafMode, int level){
		super(1,piafMode, level);
		audio = new BackgroundAudioPlayer("./res/audio/Chicken song  -  [Geco Remix].wav");
	}
	
	@Override
	public void paint(Graphics g){
		buffer.drawImage(fondEcran,0,0,this);
		buffer.setColor(Color.WHITE);
		super.paint(g);
	}
}
