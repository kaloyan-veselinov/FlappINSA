import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveGame {
	protected int highscore;
	protected int level;
	
	public SaveGame(int level){
		this.level=level;
		try{	
			FileInputStream f_in = new FileInputStream("./data/highscore"+this.level+".data");
			ObjectInputStream obj_in = new ObjectInputStream(f_in);
			Object obj = obj_in.readObject();
			if(obj instanceof Integer)
				highscore=(int)obj;
			obj_in.close();
			f_in.close();
		} catch (IOException | ClassNotFoundException e){
			highscore=0;
			return;
		}
	}
	
	/**
	 * La methode saveToFile() sauvegarde le highscore dans un fichier.
	 */
	public void saveToFile(){
		try{
			FileOutputStream f_out = new FileOutputStream("./data/highscore"+level+".data");
			ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
			obj_out.writeObject(highscore);
			obj_out.close();
			f_out.close();
		} catch(IOException i){
			return;
		}
	}
	
	/**
	 * La methode resetStats() remet le highscore a 0.
	 */
	public void resetStats(){
		highscore=0;
		saveToFile();
	}
}
