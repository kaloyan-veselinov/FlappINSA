import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.Timer;

public class FenetreLost extends FenetreLostOrPause {
	Timer t; //Timer qui evite les mauvais click sur Rage-quit...
	int timeElapsed; //le temps depuis le debut du programme
	final int delay=100; //petit delay qui permet de ne pas eteindre l'ordi sans faire expres apres une fin de partie...
	
	public FenetreLost(FenetreJeux fj){
		super(fj,"lost");
		t=new Timer(delay,this);
		t.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() instanceof Timer)
			timeElapsed+=delay;
		else if(e.getActionCommand()=="b1"){
			if(timeElapsed>3000){
				try{
					rageQuit();
				} catch(RuntimeException | IOException ex){
					ex.printStackTrace();
				}  
			}
		} else super.actionPerformed(e);
		
	}
	
	/**
	 * La methode rageQuit() eteint l'ordinateur de la victime
	 * @throws RuntimeException envoyee quand l'OS n'est pas reconnu
	 * @throws IOException  envoyee si la commande ne peut pas etre executee
	 */
	public void rageQuit() throws RuntimeException, IOException {
	    
		String shutdownCommand;
		String os = System.getProperty("os.name").toLowerCase();
		    
		if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0 || os.indexOf("mac") >= 0) {
		    shutdownCommand = "shutdown -h now";
		}
		else if (os.indexOf("win") >= 0) {
		    shutdownCommand = "shutdown.exe -s -t 0";
		}
		else {
		    throw new RuntimeException("Sorry, you can't ragequit");
		}
	
		Runtime.getRuntime().exec(shutdownCommand);
		System.exit(0);
	    
	}
}
