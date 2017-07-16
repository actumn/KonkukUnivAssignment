package DisplayObject;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;

import Item.Weapon.Effect;


public class DisplayComponent extends JComponent {
	
	private static final int DEFAULT_COORDINATE = -304;

	private Character ch = new Character(this, 160, 160);
	private Rock rock = new Rock(0, 0);
	private ArrayList<Monster> monster = new ArrayList<Monster>();
	private ArrayList<Effect> effect = new ArrayList<Effect>();
	
	private int roundCount;
	
	Image buffImage;
	Graphics buffg;
	
	public DisplayComponent() {
		
	}
	
	public void paint(Graphics g) {
		buffImage = createImage(640, 640);
		buffg = buffImage.getGraphics();

		buffg.clearRect(0, 0, 640, 640);
		update(g);
	}
	
	public void update(Graphics g) {
		
		buffg.drawImage(rock.getIm(), DEFAULT_COORDINATE + rock.getImageX(), DEFAULT_COORDINATE + rock.getImageY(), 1000, 1000, this);
		
		for (int i = 0; i < monster.size(); i++) {
			buffg.drawImage(monster.get(i).getIm(), DEFAULT_COORDINATE + monster.get(i).getImageX(), DEFAULT_COORDINATE + monster.get(i).getImageY(), this);
		}
		for (int i = 0; i < effect.size(); i++) {
			buffg.drawImage(effect.get(i).getIm(), DEFAULT_COORDINATE + effect.get(i).getImageX(), DEFAULT_COORDINATE + effect.get(i).getImageY(), this);
		}
		
		buffg.drawImage(ch.getIm(), DEFAULT_COORDINATE + ch.getImageX(), DEFAULT_COORDINATE + ch.getImageY(), this);
		
		g.drawImage(buffImage, 0, 0, this);
	}

	public Character getCh() {
		return ch;
	}

	public ArrayList<Effect> getEffect() {
		return effect;
	}

	public ArrayList<Monster> getMonster() {
		return monster;
	}
	
	public void makeMonster() {		
		try {
			BufferedReader r = new BufferedReader(new FileReader("txt/Round1.txt"));
			
			String line;
			int lineNumber = 0;
			
			while ((line = r.readLine()).equals("wolf")) {
				monster.add(new Monster(this, 32 + (32 * lineNumber++), 320, 5 * roundCount, 5 * roundCount, 5 * roundCount));
			}
			roundCount ++;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			
		}
	}


}
