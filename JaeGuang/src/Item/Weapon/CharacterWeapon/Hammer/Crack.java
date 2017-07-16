package Item.Weapon.CharacterWeapon.Hammer;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import Item.Weapon.Effect;

public class Crack extends Effect {
	
	private Image[] im = new Image[30];
	private String[] fileDirectory = new String[]{"small", "long"};
	private String defaultDirectory = "image/character/attack/hammer/crack/";
	
	private int imageX = 100;
	private int imageY= 100;
	private int motionNum;
	private int count;
	
	public Crack(int crack) {
		for (int i = 0; i < im.length; i++) {
			File file  = new File(defaultDirectory + fileDirectory[crack] + "/crack" + (i + 1) + ".png");
			if (file.isFile()) {
				im[i] = Toolkit.getDefaultToolkit().getImage(defaultDirectory + fileDirectory[crack] + "/crack" + (i + 1) + ".png");
			}
		}
	}
	
	public void moveMotion(DisplayComponent dc, int attackAreaCenterX, int attackAreaCenterY, int physicalAttackPower) {
		
		imageX = (attackAreaCenterX - 1 - (extraArray / 2)) * 32;
		imageY = (attackAreaCenterY - 1 - (extraArray / 2)) * 32;
		
		attackAreaArray[attackAreaCenterX][attackAreaCenterY]++;
		
		if (attackAreaArray[attackAreaCenterX][attackAreaCenterY] > ATTACK_VALUE) 
			attack(attackAnimalArray[attackAreaCenterX][attackAreaCenterY][0], dc, physicalAttackPower);
		
		Timer timer = new Timer();
    	TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (im[count] != null) {
					motionNum++;
					dc.repaint();
					count++;
				} else {
					attackAreaArray[attackAreaCenterX][attackAreaCenterY]--;
					remove(dc);
					
					dc.repaint();
					timer.cancel();
				}
			}
    	};
    	timer.schedule(task, 0, 80);
	}
	
	public void remove(DisplayComponent dc) {
		dc.getEffect().remove(this);
	}
		
	
	public Image getIm() {
		return im[motionNum];
	}

	public int getImageX() {
		return imageX;
	}

	public int getImageY() {
		return imageY;
	}

	@Override
	public void attackAreaHitTest(DisplayComponent dc,
			int physicalAttackPower) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attack(Animal animal, DisplayComponent dc,
			int physicalAttackPower) {
		// TODO Auto-generated method stub
		if (animal != null) {
			animal.setHp(-physicalAttackPower);
			death(animal, dc);
			
		}
		
	}

	@Override
	public void attackAreaPlus(Animal animal, int positionNum,
			int hitAreaCenterX, int hitAreaCenterY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attackAreaMinus(int positionNum, int hitAreaCenterX,
			int hitAreaCenterY) {
		// TODO Auto-generated method stub
		
	}

}
