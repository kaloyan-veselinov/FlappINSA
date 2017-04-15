import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class FenetrePause extends FenetreLostOrPause {
		
	public FenetrePause(FenetreJeux fj){
		super(fj,"pause");
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand()=="b1")
			onResume();
		else super.actionPerformed(e);		
	}
	
	/**
	 * La methode onResume() resume le jeu mis en pause
	 */
	private void onResume(){
		fj.timer.start();
		fj.audio.resumeAudio();
		this.dispose();
	}	
}
