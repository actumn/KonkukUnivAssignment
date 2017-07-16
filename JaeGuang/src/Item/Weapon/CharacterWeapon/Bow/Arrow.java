package Item.Weapon.CharacterWeapon.Bow;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import Item.Weapon.Effect;

public class Arrow extends Effect {
	
	private Image[][][] im = new Image[1][4][3];
	private String[] fileDirectory = new String[]{"fire", "power"};
	private String defaultDirectory = "image/character/attack/bow/arrow/";

	private int imageX;
	private int imageY;
	private int arrayNum;
	private int positionNum;
	private int motionNum;
	private int count;
	
	private int arrowNum;
	
	public Arrow(int imageX, int imageY, int positionNum, int arrowNum) {
		
		this.imageX = imageX;
		this.imageY = imageY;
		this.positionNum = positionNum;
		this.arrowNum = arrowNum;
		
		this.attackDistance = 1;
		
		for (int i = 0; i < im.length; i++) {
			for (int j = 0; j < im[i].length; j++) {
				for (int k = 0; k < im[i][j].length; k++) {
					im[i][j][k] = Toolkit.getDefaultToolkit().getImage(defaultDirectory + fileDirectory[arrowNum] + "/position" + (j + 1) + (k + 1) + ".png");
				}
			}
		}
	}
	
	public void moveMotion(DisplayComponent dc, int physicalAttackPower) {
		
		this.attackAreaCenterX = dc.getCh().getHitAreaCenterX();
		this.attackAreaCenterY = dc.getCh().getHitAreaCenterY();
		
		int positionNum = dc.getCh().getPositionNum();
		
		Timer timer = new Timer();
    	TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				

				if (count++ == 0) attackAreaArray[attackAreaCenterX][attackAreaCenterY]++;

				attackAreaArray[attackAreaCenterX][attackAreaCenterY]--;
				switch (positionNum) {
				case DOWN : attackAreaCenterY += attackDistance; break;
				case LEFT : attackAreaCenterX -= attackDistance; break;
				case RIGHT : attackAreaCenterX += attackDistance; break;
				case UP : attackAreaCenterY -= attackDistance; break;
				}
				attackAreaArray[attackAreaCenterX][attackAreaCenterY]++;
				
				if (attackAreaArray[attackAreaCenterX][attackAreaCenterY] > ATTACK_VALUE) {
					
					if (arrowNum == 0) {
						attackAreaArray[attackAreaCenterX][attackAreaCenterY]--;
						remove(dc);
						attack(attackAnimalArray[attackAreaCenterX][attackAreaCenterY][0], dc, physicalAttackPower);
						dc.repaint();
						timer.cancel();
					} else if (arrowNum == 1) {
						if (attackAnimalArray[attackAreaCenterX][attackAreaCenterY][0] == null){
							attackAreaArray[attackAreaCenterX][attackAreaCenterY]--;
							remove(dc);
							dc.repaint();
							timer.cancel();
						} else {
							attack(attackAnimalArray[attackAreaCenterX][attackAreaCenterY][0], dc, physicalAttackPower);
						}
					}
				}
				

				for (int i = 0; i < 2; i++) {
					switch (positionNum) { // 이동 수치값 조정
						case DOWN : imageY += 16; break; // 아래
						case LEFT : imageX -= 16; break; // 왼쪽
						case RIGHT : imageX += 16; break; // 오른쪽
						case UP : imageY -= 16; break; // 위
					}
					setMotionNum(1);
					dc.repaint();
				}
			}
    	};
    	
    	timer.schedule(task, 0, 100);
	
	}
	

	public void remove(DisplayComponent dc) {
		dc.getEffect().remove(this);
	}
	
	public Image getIm() {
		return im[arrayNum][positionNum][motionNum];
	}

	public int getImageX() {
		return imageX;
	}

	public int getImageY() {
		return imageY;
	}

	public void setMotionNum(int motionNum) {
		this.motionNum += motionNum;
		this.motionNum %= 3;
	}

	@Override
	public void attack(Animal animal, DisplayComponent dc, int physicalAttackPower) {
		// TODO Auto-generated method stub
		if (animal != null) {
			animal.setHp(-physicalAttackPower);
			death(animal, dc);
		}
		
	}

	@Override
	public void attackAreaPlus(Animal animal, int positionNum,
			int hitAreaCenterX, int hitAreaCenterY) {
		
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] += ATTACK;
		/*
		attackAreaDistance(positionNum, hitAreaCenterX, hitAreaCenterY);
		
		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = animal;
		
		attackAreaArray[attackAreaCenterX][attackAreaCenterY]++;*/
		
	}

	@Override
	public void attackAreaMinus(int positionNum, int hitAreaCenterX,
			int hitAreaCenterY) {
		
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] -= ATTACK;
		/*
		attackAreaDistance(positionNum, hitAreaCenterX, hitAreaCenterY);
		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = null;
		
		attackAreaArray[attackAreaCenterX][attackAreaCenterY]--;*/
	}

	@Override
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower) {
		// TODO Auto-generated method stub
		
	}
}
