import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BackgroundAudioPlayer {
	
	private Clip clip;
	private long time; //variable permettant de stocker l'endroit d'arret de la musique
	
	public BackgroundAudioPlayer(String audioFilePath){
		try{
			clip = AudioSystem.getClip();
	        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
	        clip.open(inputStream);
	        inputStream.close();
		} catch(Exception e){}
	}
	
	/**
	 * La methode startAudio() permet de lancer une musique en boucle
	 */
	public void startAudio(){
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * La methode pauseAudio() permet de suspendre la musique en cours, en sauvegardant son etat
	 */
	public void pauseAudio(){
		time=clip.getMicrosecondPosition();
		clip.stop();
	}
	
	
	/**
	 * La methode resumeAudio() permet de relancer la musique a partir de l'endroit ou elle a ete arretee
	 */
	public void resumeAudio(){
		clip.setMicrosecondPosition(time);
		clip.start();
	}
		
	
}
