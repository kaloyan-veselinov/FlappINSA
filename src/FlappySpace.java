import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

@SuppressWarnings("serial")
public class FlappySpace extends FenetreJeux {
	protected ChampDObjetsCelestes champ;
	
	public FlappySpace(int piafMode, int level){
		super(2,piafMode, level);
		audio=new BackgroundAudioPlayer("./res/audio/Bag Raiders - Shooting Stars.wav");
		champ=new ChampDObjetsCelestes(LARGEUR_FENETRE, HAUTEUR_FENETRE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() instanceof Timer){
			champ.update();
		}
		super.actionPerformed(e);
	}
	
	@Override
	public void paint(Graphics g){
		buffer.drawImage(fondEcran,0,0,this);
		champ.dessine(buffer,this);
		buffer.setColor(Color.LIGHT_GRAY);
		super.paint(g);
	}
	
	
}
