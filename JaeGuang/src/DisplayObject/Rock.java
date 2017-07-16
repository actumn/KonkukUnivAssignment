package DisplayObject;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;


public class Rock extends JComponent {
	
	private Image im = Toolkit.getDefaultToolkit().getImage("image/background/rock/rockAll.png");
	private int imageX;
	private int imageY;
	
	public Rock(int imageX, int imageY) {
		this.imageX = imageX;
		this.imageY = imageY;
	}
	
	public Image getIm() {
		return im;
	}

	public int getImageX() {
		return imageX;
	}

	public int getImageY() {
		return imageY;
	}

}
