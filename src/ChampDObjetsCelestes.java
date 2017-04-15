import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFrame;

public class ChampDObjetsCelestes {
	private int dt; //la constante de temps pour le calcul du mouvement
	protected ObjetCeleste[] o; //le tableau contenant tous les objets celestes
	protected int nb_objets; //le nombre d'objets ajoutes
	private double scale; //l'echelle de dessin
	private int width; //largeur de la fenetre dans laquelle on dessine
	private int heigth; //hauteur de la fenetre dans laquelle on dessine
	
	public ChampDObjetsCelestes(int width, int heigth){
		nb_objets=6;
		dt=(int)(1000*Math.pow(2.2,nb_objets)); //defini la vitesse des planetes en fonction de leur nombre (echelle pour les vitesses)
		this.width=width;
		this.heigth=heigth;
		initPlanetes();
	}
	
	/**
	 * Methode de test des donnes lues dans le fichier
	 */
	void print(){
		for(int i=0;i<nb_objets;i++){
			System.out.println(o[i].nom+"|"+o[i].x+"|"+o[i].y+"|"+o[i].m+"|"+o[i].xp+o[i].yp);
		}
	}
	
	/**
	 * Methode qui lit la liste de planetes fournies dans le fichier text
	 */
	private void initPlanetes(){
		try{
			Path path=Paths.get("./res/planetes.txt");
			List<String> data = Files.readAllLines(path);
			o=new ObjetCeleste[nb_objets];
			String temp;
			String nomPlanete;
			double rayonOrbite;
			double vitesseOrbitale;
			double masse;
			String[][] celestData=new String[nb_objets][];
			for(int i=1; i<nb_objets+1;i++){
				temp=data.get(i);
				celestData[i-1]=temp.split(";");
			}
			setScale(celestData);
			for(int i=0; i<nb_objets;i++){
				nomPlanete=celestData[i][0];
				rayonOrbite=Double.parseDouble(celestData[i][1]);
				vitesseOrbitale=Double.parseDouble(celestData[i][2]);
				masse=Double.parseDouble(celestData[i][3]);
				o[i]=new Planete(nomPlanete,width/2,rayonOrbite,vitesseOrbitale,masse,scale,width,heigth,4.0/nb_objets);
			}
		} catch(IOException i){
			System.out.println("Planet data file missing!");
		} catch(NumberFormatException n){
			System.out.println("Planet data format not compatible!");
		}
	}
	
	/**
	 * Methode qui calcule l'echelle a imposer a la fenetre en fonction du plus grand element de la liste de planetes
	 * @param celestData le tableau contenant les donnees des planetes
	 */
	public void setScale(String[][] celestData){
		double xMax=Double.parseDouble(celestData[0][1]);
		double temp=xMax;
		for(int i=0; i<nb_objets;i++){
			temp=Double.parseDouble(celestData[i][1]);
			if(temp>xMax)
				xMax=temp;
		}
		int i=0;
		temp=xMax;
		while(temp>10){
			i++;
			temp/=10;	
		}
		temp=Math.ceil(temp)*Math.pow(10, i);
		this.scale=((double)width)/(2.0e3*temp);
	}
	
	/**
	 * Methode qui met a jour les forces appliquees au differents planetes
	 */
	protected void update(){
		for(int i=0; i<nb_objets-1;i++){
			for(int j=i+1; j<nb_objets; j++){
				o[i].updateForce(o[j]);
				o[j].updateForce(o[i]);
			}
		}
		for(int i=0;i<nb_objets;i++){
			o[i].move(dt);
		}
	}
	
	/**
	 * Methode de dessin de toutes les planetes crees
	 * @param g 
	 * @param fj la FenetreJeux dans laquelle on dessine
	 */
	protected void dessine(Graphics2D g, JFrame jf){
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		for(int i=0; i<nb_objets; i++){
			o[i].dessine(g,jf);
		}
	}
}
