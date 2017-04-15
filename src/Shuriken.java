import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Random;

import javax.swing.JFrame;

public class Shuriken extends Objet {
	private Random rn;
	private final int vMax=6;
	private double theta;
	private BufferedImage srcImage;
	
	public Shuriken(int width, int heigth){
		super("./res/autres_objets/shuriken.png",0,0,width,heigth);
		this.xpp=0;
		this.ypp=0;
		this.theta=0;
		this.srcImage=copyImage(image);
		rn=new Random();
		setXYAleatoireBord();
	}
	
	/**
	 * Cette methode permet de placer aleatoirement le shuriken sur un des 4 bords
	 */
	public void setXYAleatoireBord() {
		switch(rn.nextInt(4)) { // choix dâ€™un des bords
			case 0: // bord gauche
					x = 1-l;
					y = rn.nextInt(heigth);
					xp=2+rn.nextInt(vMax);
					yp=(2-rn.nextInt(3))*rn.nextInt(vMax);
					break;
			case 1: // bord droit
					x = width-1;
					y = rn.nextInt(heigth);
					xp=-2-rn.nextInt(vMax);
					yp=(2-rn.nextInt(3))*rn.nextInt(vMax);
					break;
			case 2: // bord haut
					x = rn.nextInt(width);
					y = 1-h;
					xp=(2-rn.nextInt(3))*rn.nextInt(vMax);
					yp=2+rn.nextInt(vMax);
					break;
			case 3: // bord bas
					x = rn.nextInt(width);
					y = heigth-1;
					xp=(2-rn.nextInt(3))*rn.nextInt(vMax);
					yp=-2-rn.nextInt(vMax);
					break;
		}
	}
	
	/**
	 * Permet de copier une BufferedImage
	 * @param bi l'image a copier
	 * @return une copie de l'image
	 */
	private BufferedImage copyImage(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	/**
	 * Permet de tourner l'image du shuriken
	 * @param angle l'angle de rotation
	 */
	public void rotateImage(double angle){
		this.theta+=angle*Math.PI/16.0;
		AffineTransform tx = new AffineTransform();
		tx.rotate(theta, l/2.0, h/2.0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		image=op.filter(srcImage, null);
	}
	
	@Override
	public boolean move(double time){
		rotateImage(0.1*Math.sqrt(xp*xp+yp*yp));
		super.move(time);
		return (x<-l||x>width||y<-h||y>heigth);
	}
	
	@Override
	public void dessine(Graphics2D g, JFrame fj){
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		super.dessine(g, fj);
	}
}
