import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelApercu extends JPanel {
	public BufferedImage piaf;
	public BufferedImage fond;
	public BufferedImage pipeSup;
	public BufferedImage pipeInf;
	
	public PanelApercu(int piafMode, int gameMode){
		setLayout(null);
		updatePiaf(piafMode);
		updateGameMode(gameMode);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(fond, 0, 0, this);
		g.drawImage(pipeInf, 300, 280, this);
		g.drawImage(pipeSup, 300, 170-pipeSup.getHeight(),this);
		g.drawImage(piaf, 100, 200, this);
	}
	
	public void updatePiaf(int piafMode){
		try{
			piaf=ImageIO.read(new File("./res/piaf/piaf_"+piafMode+".png"));
		} catch(IOException i){
			i.printStackTrace();
			System.out.println("Image file missing");
		}
	}
	
	public void updateGameMode(int gameMode){
		try{
			fond=ImageIO.read(new File("./res/apercu/apercu_"+gameMode+".png"));
			pipeSup=ImageIO.read(new File("./res/pipe/pipe_sup_"+gameMode+".png"));
			pipeInf=ImageIO.read(new File("./res/pipe/pipe_inf_"+gameMode+".png"));
		} catch(IOException i){
			i.printStackTrace();
			System.out.println("Image file missing");
		}
	}
}
