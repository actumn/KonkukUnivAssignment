package Item.Weapon.CharacterWeapon.Bow;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import Item.Weapon.Effect;

public class IceArrow extends Effect {
	
	private Image[] im = new Image[17];
	private String defaultDirectory = "image/character/attack/bow/skill2/ice";
	
	private int imageX = 100;
	private int imageY= 100;
	private int motionNum;
	private int count;
	
	public IceArrow() {
		for (int i = 0; i < im.length; i++) {
			File file  = new File(defaultDirectory + "/position1" + (i + 1) + ".png");
			if (file.isFile()) {
				im[i] = Toolkit.getDefaultToolkit().getImage(defaultDirectory + "/position1" + (i + 1) + ".png");
			}
		}
	}
	
	public void moveMotion(DisplayComponent dc, int attackAreaCenterX, int attackAreaCenterY, int physicalAttackPower) {
		
		imageX = (attackAreaCenterX - 1 - (extraArray / 2)) * 32;
		imageY = (attackAreaCenterY - 1 - (extraArray / 2)) * 32;
		
		//attackAreaArray[attackAreaCenterX][attackAreaCenterY]++;
		
		Timer timer = new Timer();
    	TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (im[count] != null) {
					motionNum++;
					dc.repaint();
					count++;
					
					if (count == 6) {
						for (int i = -1; i < 2; i++) {
							for (int j = -1; j < 2 ; j++) {
								if (attackAreaArray[attackAreaCenterX + j][attackAreaCenterY + i] > ATTACK_VALUE) {
									attack(attackAnimalArray[attackAreaCenterX + j][attackAreaCenterY + i][0], dc, physicalAttackPower);
								}
							}
						}
						
					}
					
				} else {
					//attackAreaArray[attackAreaCenterX][attackAreaCenterY]--;
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
		
	@Override
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower) {
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
	public Image getIm() {
		return im[motionNum];
	}

	public int getImageX() {
		return imageX;
	}

	public int getImageY() {
		return imageY;
	}

}
