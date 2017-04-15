import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

public class FlappyNinja extends FenetreJeux {
	private final int NB_MAX_SHURIKEN=6;
	private Shuriken[] shuriken;
	
	public FlappyNinja(int piafMode, int level){
		super(3,piafMode, level);
		audio = new BackgroundAudioPlayer("./res/audio/Shadow Ninja.wav");
		shuriken = new Shuriken[NB_MAX_SHURIKEN];
		for(int i=0; i<shuriken.length;i++){
			shuriken[i] = new Shuriken(LARGEUR_FENETRE,HAUTEUR_FENETRE);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		for(int i=0; i<shuriken.length;i++)
			if(shuriken[i].move(1))
				shuriken[i].setXYAleatoireBord();
		super.actionPerformed(e);
	}
	
	@Override
	public void paint(Graphics g){
		buffer.drawImage(fondEcran,0,0,this);
		buffer.setColor(Color.DARK_GRAY);
		for(int i=0; i<shuriken.length; i++)
			if(shuriken[i]!=null)
				shuriken[i].dessine(buffer, this);
		super.paint(g);
	}
}
