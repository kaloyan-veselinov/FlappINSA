import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class FenetreParametre extends JFrame implements ActionListener, ItemListener {
	
	protected static final int LARGEUR_FENETRE=800;
	protected static final int HAUTEUR_FENETRE=540;
	public JButton monbouton;
	public JButton monbouton2;
	public JComboBox<String> box1;
	public JComboBox<String> box2;
	public JComboBox<String> box3;
	public JLabel highscore;
	public PanelApercu apercu;
	public SaveGame s;
	
	public FenetreParametre() {
		
		//Param�trage de la fen�tre
		setLayout(null); 
		setSize(LARGEUR_FENETRE,HAUTEUR_FENETRE); 
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setResizable(false);
		this.setTitle("Settings");
	
		s = new SaveGame(2);
		
		Font police1 = new Font("Arial", Font.BOLD,25);
		Font police2 = new Font("Arial", Font.BOLD,14);
		
		//Bouton de debut de jeu
		monbouton = new JButton("GO !!!");
		monbouton.setBounds(10,415,300,80);
		monbouton.setBackground(Color.white);
		monbouton.addActionListener(this);
		monbouton.setActionCommand("go");
		monbouton.setFont(police1);
		
		//Bouton de reinitialisation
		monbouton2 = new JButton("Reset Stats");
		monbouton2.setBounds(10,325,300,80);
		monbouton2.setBackground(Color.WHITE);
		monbouton2.addActionListener(this);
		monbouton2.setActionCommand("reset");
		monbouton2.setFont(police1);
		
		
		//Titre principal
		JLabel titre1 = new JLabel("FlappINSA Settings",SwingConstants.LEFT);
		titre1.setBounds(10,10, 300,60);
		titre1.setFont(police1);
		
		//Highscore
		highscore=new JLabel("HIGHSCORE: "+Integer.toString(s.highscore), SwingConstants.RIGHT);
		highscore.setBounds(350, 10, 420, 60);
		highscore.setFont(police1);
		
		//Valeur du highscore
		JLabel highscoreValue=new JLabel(Integer.toString(s.highscore),SwingConstants.RIGHT);
		highscoreValue.setFont(police1);
		highscoreValue.setBounds(350,10,420,60);
				
		//Titres de parametres
		JLabel titre2 = new JLabel("Choix du piaf :");
		titre2.setBounds(10,85, 300,20);
		titre2.setFont(police2);
				
		JLabel titre3 = new JLabel("Choix de l'envirronnement :");
		titre3.setBounds(10,165, 300,20);
		titre3.setFont(police2);
				
		JLabel titre4 = new JLabel("Choix de la difficulte :");
		titre4.setBounds(10,245, 300,20);
		titre4.setFont(police2);
		
		//Menus deroulants de parametres
		box1 = new JComboBox<String>();
		box1.setBounds(10,110, 300,40);
		box1.addItem("Piaf classique");
		box1.addItem("Piaf astronaute");
		box1.addItem("Piaf ninja");
		box1.setSelectedIndex(1);
		box1.addItemListener(this);
		box1.setName("piaf");
				
		box2 = new JComboBox<String>();
		box2.setBounds(10,190,300,40);
		box2.addItem("Seaside");
		box2.addItem("SpaceFlap");
		box2.addItem("NinjaFlap");
		box2.setSelectedIndex(1);
		box2.addItemListener(this);
		box2.setName("mode");
		
		box3 = new JComboBox<String>();
		box3.setBounds(10,270,300,40);
		box3.addItem("Easy");
		box3.addItem("Normal");
		box3.addItem("Hard");
		box3.setSelectedIndex(1);
		box3.addItemListener(this);
		box3.setName("level");
		
		//Panel d'apercu
		apercu = new PanelApercu(box1.getSelectedIndex()+1,box2.getSelectedIndex()+1);
		apercu.setBounds(350,75,420,420);
		
		//Panel de dessin principal
		JPanel pmain =new JPanel();
		pmain.setBounds(0, 0, 800,540);
		pmain.setBackground(Color.CYAN);
		pmain.setLayout(null);
		
		//Ajouts
		pmain.add(monbouton2);
		pmain.add(monbouton);
		pmain.add(box1);
		pmain.add(box2);
		pmain.add(box3);
		pmain.add(titre1);
		pmain.add(titre2);
		pmain.add(titre3);
		pmain.add(titre4);
		pmain.add(highscore);
		pmain.add(apercu);
		
		this.setContentPane(pmain);
		setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		String command=e.getActionCommand();
		if(command=="go"){
			switch(box2.getSelectedItem().toString()){
				case "Seaside":
					new FlappySeaside(box1.getSelectedIndex()+1, box3.getSelectedIndex()+1);
					break;
				case "SpaceFlap":
					new FlappySpace(box1.getSelectedIndex()+1, box3.getSelectedIndex()+1);
					break;
				case "NinjaFlap":
					new FlappyNinja(box1.getSelectedIndex()+1, box3.getSelectedIndex()+1);
					break;
			}
			this.dispose();
		} else if(command=="reset"){
			s.resetStats();
			highscore.setText("HIGHSCORE: "+Integer.toString(s.highscore));
			repaint();
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent i){
		JComboBox<String> temp = (JComboBox<String>) (i.getSource());
		switch(temp.getName()){
		case "piaf":
			apercu.updatePiaf(temp.getSelectedIndex()+1);
			apercu.repaint();
			break;
		case "mode":
			apercu.updateGameMode(temp.getSelectedIndex()+1);
			apercu.repaint();
			break;
		case "level":
			s=new SaveGame(box3.getSelectedIndex()+1);
			highscore.setText("HIGHSCORE: "+Integer.toString(s.highscore));
			highscore.repaint();
			break;
		}
	}
	
	public static void main (String [] args) {
		new FenetreParametre();
	}

}
