import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public abstract class FenetreLostOrPause extends JFrame implements ActionListener {
	protected final int HAUTEUR_FENETRE = 400;
	protected final int LARGEUR_FENETRE = 400;
	protected FenetreJeux fj;
	protected Image image;
	
	public FenetreLostOrPause(FenetreJeux fj, String type){
		setLayout(null);
		setSize(LARGEUR_FENETRE, HAUTEUR_FENETRE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		this.fj=fj;
		
		JPanel pMain = new JPanel();
		pMain.setBounds(0, 0, LARGEUR_FENETRE, HAUTEUR_FENETRE);
		if(fj instanceof FlappySpace)
			pMain.setBackground(Color.CYAN);
		else if (fj instanceof FlappySeaside)
			pMain.setBackground(Color.GREEN);
		else if (fj instanceof FlappyNinja)
			pMain.setBackground(Color.ORANGE);
		pMain.setLayout(null);
		
		Toolkit t = Toolkit.getDefaultToolkit();
		String adress;
		if(type=="lost")
			adress="./res/game_over.png";
		else adress = "./res/pause.png";
		image = t.getImage(adress);
		
		JLabel logo_over = new JLabel("",SwingConstants.CENTER);
		logo_over.setIcon(new ImageIcon(image));
		logo_over.setBounds(0, 0, 400, 100);
		pMain.add(logo_over);
		
		Font f1 = new Font("Arial", Font.BOLD, 32);
		
		JLabel lb1 = new JLabel("Score: ", SwingConstants.LEFT);
		lb1.setFont(f1);
		lb1.setBounds(10, 100, 180, 50);
		pMain.add(lb1);
		
		JLabel lb2 = new JLabel(Integer.toString(fj.score), SwingConstants.RIGHT);
		lb2.setFont(f1);
		lb2.setBounds(200, 100, 170, 50);
		pMain.add(lb2);
		
		JLabel lb3 = new JLabel("Highscore: ", SwingConstants.LEFT);
		lb3.setFont(f1);
		lb3.setBounds(10, 150, 180, 50);
		pMain.add(lb3);
		
		JLabel lb4 = new JLabel(Integer.toString(fj.s.highscore), SwingConstants.RIGHT);
		lb4.setFont(f1);
		lb4.setBounds(200, 150, 170, 50);
		pMain.add(lb4);
		
		Font f2 = new Font("Arial",Font.PLAIN,20);
		
		String qname;
		if (type=="lost")
			qname="Rage-quit";
		else qname="Continue";
		JButton b1 = new JButton(qname);
		b1.setBounds(10, 220, 360, 50);
		b1.addActionListener(this);
		b1.setActionCommand("b1");
		b1.setFont(f2);
		pMain.add(b1);
		
		JButton b2 = new JButton("New");
		b2.setBounds(10, 280, 180, 50);
		b2.addActionListener(this);
		b2.setActionCommand("rs");
		b2.setFont(f2);
		pMain.add(b2);
				
		JButton b3 = new JButton("Settings");
		b3.setBounds(200, 280, 170, 50);
		b3.addActionListener(this);
		b3.setActionCommand("set");
		b3.setFont(f2);
		pMain.add(b3);
		
		setContentPane(pMain);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		switch(e.getActionCommand()){
			case "rs": {
				fj.dispose();
				if(fj instanceof FlappySeaside)
					new FlappySeaside(fj.piafMode,fj.level);
				else if(fj instanceof FlappySpace)
					new FlappySpace(fj.piafMode,fj.level);
				else if (fj instanceof FlappyNinja)
					new FlappyNinja(fj.piafMode, fj.level);
				this.dispose();
			} break;
			
			case "set": {
				fj.dispose();
				new FenetreParametre();
				this.dispose();
			} break;
		}
	}	
}
