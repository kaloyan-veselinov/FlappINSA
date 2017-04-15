import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public abstract class FenetreJeux extends JFrame implements ActionListener,KeyListener{
		
	//Parametres de la fenetre
	protected final int LARGEUR_FENETRE=990;
	protected final int HAUTEUR_FENETRE=540;
	
	//Parametres de temps
	protected final int delay=16;
	protected Timer timer;
	protected long timeElapsed;
	
	//Parametres gerant la fluidite
	protected Graphics2D buffer;
	protected BufferedImage arrierePlan;
	
	//Images
	protected Image fondEcran;
	protected Image pause;
	
	//Parametres lies au score
	protected int score;
	protected SaveGame s;
		
	//Parametres des obstacles
	protected int nb_obs=4;
	protected ObstacleSup[] obsSup;
	protected ObstacleInf[] obsInf;
	protected int L=300; //espacement entre les obstacles (sur l'horizontale)
	protected int espacement=70; //espace entre l'obstacle superieur et l'obstacle inferieur
	protected final int ampliMax=150; //parametres gerant la hauteur du passage entre les obstacles
	
	protected Random rn;
	
	protected BackgroundAudioPlayer audio;
	
	//Polices
	Font f;
	Font f2;
	
	//Parametres du jeu definis par FenetreParametre
	protected Piaf p;
	protected int piafMode;
	protected int gameMode;
	protected int level;
		
	public FenetreJeux(int gameMode, int piafMode, int level) {
        //Parametrage de la fenetre
		setLayout(null); 
		setSize(LARGEUR_FENETRE,HAUTEUR_FENETRE); 
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setResizable(false);
		setUndecorated(true);
		
		//Definition du mode de jeu
		this.gameMode=gameMode;
		this.piafMode=piafMode;
		this.level=level;
		switch(level){
			//niveau facile
			case 1:
				espacement*=1.1;
				L*=1.3;
				break;
			
			//niveau difficile
			case 3:
				espacement*=0.9;
				L*=0.7;
				nb_obs+=2;
				break;
		}
				
		//Restoration du highscore
		s=new SaveGame(level);
		
		//creation buffer	
		arrierePlan = new BufferedImage(this.getWidth(), this.getHeight(),BufferedImage.TYPE_INT_RGB);
		buffer = arrierePlan.createGraphics();
		
		//creation de l'image de fond
		Toolkit t=Toolkit.getDefaultToolkit();
		fondEcran=t.getImage("./res/background/background_"+gameMode+".png");
		pause=t.getImage("./res/pause.png");
		
		//creation du piaf
		p = new Piaf(150,200,this.piafMode,LARGEUR_FENETRE,HAUTEUR_FENETRE);
		
		rn = new Random();
		
		//initialisation des obstacles
		obsSup = new ObstacleSup[nb_obs];
		obsInf = new ObstacleInf[nb_obs];
		int x;
		int y;
		for (int i=0; i<nb_obs; i++){
			x=LARGEUR_FENETRE + i*L;
			y=HAUTEUR_FENETRE/2 + (10+rn.nextInt(ampliMax))*(rn.nextInt(3)-1);
			obsSup[i]=new ObstacleSup(gameMode,x,y,espacement,LARGEUR_FENETRE,HAUTEUR_FENETRE);
			obsInf[i]=new ObstacleInf(gameMode,x,y,espacement,LARGEUR_FENETRE,HAUTEUR_FENETRE);
		}
		
		//definition des polices
		f=new Font("Arial", Font.BOLD, 55);
		f2=new Font("Arial", Font.BOLD, 30);
		
		//creation du timer
		timer=new Timer(delay,this);
		
		addKeyListener(this);
		setVisible(true); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		//Is the source a timer?
		if(e.getSource() instanceof Timer){
			
			//Is the game lost?
			int i=0;
			while(i<nb_obs&&!p.collision(obsSup[i])&&!p.collision(obsInf[i]))
				i++;
			if(i<nb_obs||p.move(1))
				onLost();
			
			//on met a jour les positions des obstacles
			for(i=0; i<nb_obs; i++){
				if(obsSup[i].move(1.0)||obsInf[i].move(1.0)){
					double x;
					if(i==0)
						x=obsSup[nb_obs-1].x+L;
					else x=obsSup[i-1].x+L;
					double y=(HAUTEUR_FENETRE/2)+(10+rn.nextInt(ampliMax))*(rn.nextInt(3)-1);
					obsSup[i].update(x,y);
					obsInf[i].update(x,y);
				}
				if(p.x==obsSup[i].x||p.x==obsInf[i].x)
					score++;
			}
			
			timeElapsed++;
			repaint();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			if(timeElapsed==0){
				timer.start();
				audio.startAudio();
			}
			if(timer.isRunning())
				p.jump();
		}
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			if(timer.isRunning()){
				timer.stop();
				audio.pauseAudio();
				new FenetrePause(this);
			}
		}		
	}
	
	@Override
	public void keyReleased(KeyEvent e){}
	
	@Override
	public void keyTyped(KeyEvent e){}
	
	/**
	 * La methode onLost() gere la fin du jeu en arretant le timer, sauvegardant le highscore et creant la fenetre FenetreLost.
	 */
	protected void onLost(){
		timer.stop();
		audio.pauseAudio();
		if(score>s.highscore){
			s.highscore=score;
			s.saveToFile();
		}
		new FenetreLost(this);
	}
	
	@Override
	public void paint(Graphics g) {
		
		for(int i=0; i<nb_obs;i++){
        	obsSup[i].dessine(buffer, this);
        	obsInf[i].dessine(buffer, this);
        }
        p.dessine(buffer, this);
        buffer.setFont(f);
        buffer.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if(timeElapsed==0){
			buffer.setFont(f2);
			buffer.drawString("Appuyer sur SPACE commencer!",255,100);
			buffer.drawString("Appuyer sur ESC pour suspendre le jeu!", 210, 150);
		} else buffer.drawString(Integer.toString(score), LARGEUR_FENETRE/2, 100);
        g.drawImage(arrierePlan, 0, 0, this);
	}	
}
