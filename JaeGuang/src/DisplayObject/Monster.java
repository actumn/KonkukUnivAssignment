package DisplayObject;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import Item.Weapon.MonsterWeapon.Crow;
import Item.Weapon.MonsterWeapon.LongCrow;
import Item.Weapon.MonsterWeapon.MonsterWeapon;


public class Monster extends Animal implements Movable {
	
	// 기본 능력치 필드
	private int defaultHp;
	private int defaultMp;
	private int defaultPhysicalAttackPower;
	private int defaultMagicalAttackPower;
	private int defaultPhysicalDefensePower;
	private int defaultMagicalDefensePower;
	private int defaultAccuracyRate;
	private int defaultEvasionRate;
	private int defaultExp;

	private Thread thread;
	private Timer timer;
	private TimerTask task;
	private MonsterWeapon weapon;
	
	private boolean hitFlag; 
	
	public Monster(DisplayComponent dc, int imageX, int imageY, int defaultPhysicalAttackPower, int hp, int exp) {
		
		this.fileDirectory = new String[]{"move", "attack"};
		this.defaultDirectory = "image/monster/wolf/";
		this.skillDirectory = new String[]{"/normal"};
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 1; j++) {
				for (int k = 0; k < im[i][j].length; k++) {
					for (int l = 0; l < im[i][j][k].length; l++) {
						im[i][j][k][l] = Toolkit.getDefaultToolkit().getImage(defaultDirectory + fileDirectory[i] + skillDirectory[j] + "/position" + (k + 1) + (l + 1) + ".png");
					}
				}
			}
		}

		this.hp = 100;
		this.exp = 50;
		
		this.defaultPhysicalAttackPower = defaultPhysicalAttackPower;
		
		this.imageX = imageX;
		this.imageY = imageY;
		this.hitAreaCenterX += imageX / (MOVE_VALUE * STOP_VALUE) + 1;
		this.hitAreaCenterY += imageY / (MOVE_VALUE * STOP_VALUE) + 1;
		hitAreaPlus();
		//attackAreaPlus(1, 0);
		
		Crow crow = new Crow("초보검", 10, 1);
		LongCrow longCrow = new LongCrow("초보검", 10, 1);

		this.weapon = crow;
		weaponChange(crow);
		weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);
		
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				try {
					hitTest(dc); //System.out.println(getHp());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		timer.schedule(task, 0, (int)(Math.random()*1000 + 1000)); // 무작위 이동
		
	}
	
	public void weaponChange(MonsterWeapon weapon) {
		weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
		this.weapon = weapon;
		this.attackSpeed = ACT_SPEED + weapon.getWeaponSpeed();
		this.physicalAttackPower = this.defaultPhysicalAttackPower + weapon.getPhysicalAttackPower();
		weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);
	}
	
	public void attackMotion(DisplayComponent dc) {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (count < STOP_VALUE) {
					dc.repaint();
					setMotionNum(1);
					count++;
					
					if (motionNum == 2) weapon.attackAreaHitTest(dc, physicalAttackPower);

				} else {
					timer.cancel();
				}
			}
		};

		count = 0;
		timer.schedule(task, 0, ACT_SPEED);

	}

	public void hitTest(DisplayComponent dc) throws InterruptedException {
		
		
		
		// 공격!
		
		// 공격!
		if (weapon.attackAreaHitTest(this)) {
			attackMotion(dc);
			hitFlag = true;
		} else {
			hitFlag = false;
		}
				
		
		/*
		if (hitAreaArray[hitAreaCenterX][hitAreaCenterY + 1] >= BLOCK) { 
			positionNum = DOWN;
			randomPosition = -1;

			attackMotion(dc);
		} else if (hitAreaArray[hitAreaCenterX][hitAreaCenterY - 1] >= BLOCK) {
			positionNum = UP;
			randomPosition = -1;

			attackMotion(dc);
		} else if	(hitAreaArray[hitAreaCenterX + 1][hitAreaCenterY] >= BLOCK) {
			positionNum = RIGHT;
			randomPosition = -1;

			attackMotion(dc);
		} else if (hitAreaArray[hitAreaCenterX - 1][hitAreaCenterY] >= BLOCK) {
			positionNum = LEFT;
			randomPosition = -1;

			attackMotion(dc);
		}
		
		
		
		if ((hitAreaArray[hitAreaCenterX][hitAreaCenterY + 1] >= BLOCK
						|| hitAreaArray[hitAreaCenterX][hitAreaCenterY - 1] >= BLOCK
						|| hitAreaArray[hitAreaCenterX + 1][hitAreaCenterY] >= BLOCK 
						|| hitAreaArray[hitAreaCenterX - 1][hitAreaCenterY] >= BLOCK)) {
			
			arrayNum = ATTACK_ARRAY_NUM + randomPosition;
			randomPosition = -1;

			attack(dc);

		} else {
			arrayNum = randomPosition; // 방향 결정
		}*/
		

		if(hitFlag == false){
			if(Math.random()<0.5){
				// 왼쪽
				if (dc.getCh().getImageX() <= this.imageX && 
						hitAreaArray[hitAreaCenterX - 1][hitAreaCenterY] < BLOCK) {
					hitAreaMinus(); // 범위 이동
					weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
					hitAreaCenterX--;
					positionNum = LEFT; // 방향 결정
					hitAreaPlus();
					weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);
					
					moveMotion(dc);// 이동
				}
				// 오른쪽
				else if (dc.getCh().getImageX() >= this.imageX && 
						hitAreaArray[hitAreaCenterX + 1][hitAreaCenterY] < BLOCK) {
					hitAreaMinus();
					weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
					hitAreaCenterX++;
					positionNum = RIGHT;
					hitAreaPlus();
					weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);
					
					moveMotion(dc);
				}
			}
			
			else{
				// 위
				if (dc.getCh().getImageY() <= this.imageY && 
						hitAreaArray[hitAreaCenterX][hitAreaCenterY - 1] < BLOCK) {
					hitAreaMinus();
					weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
					hitAreaCenterY--;
					positionNum = UP;
					hitAreaPlus();
					weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);
					
					moveMotion(dc);
				}
				// 아래
				else if (dc.getCh().getImageY() >= this.imageY && 
						hitAreaArray[hitAreaCenterX][hitAreaCenterY + 1] < BLOCK){
					hitAreaMinus();
					weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
					hitAreaCenterY++;
					positionNum = DOWN;
					hitAreaPlus();
					weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);
					
					moveMotion(dc);
				}
			}

		}

	}
	
	
	public void moveMotion(DisplayComponent dc) {

		Timer timer = new Timer();
    	TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if (count < STOP_VALUE) {
					switch (positionNum) { // 이동 수치값 조정
					case DOWN : imageY += MOVE_VALUE; break; // 아래
					case LEFT : imageX -= MOVE_VALUE; break; // 왼쪽
					case RIGHT : imageX += MOVE_VALUE; break; // 오른쪽
					case UP : imageY -= MOVE_VALUE; break; // 위
					}
					setMotionNum(1);
					dc.repaint();
					count ++;
					
				} else {
					timer.cancel();
				}
			}
    	};
    	
    	count = 0;
    	timer.schedule(task, 0, ACT_SPEED);
	
	}
	
	
	public Image getIm() {
		return im[arrayNum][skillNum][positionNum][motionNum];
	}
	
	public int getArrayNum() {
		return arrayNum;
	}

	public int getImageX() {
		return imageX;
	}

	public int getImageY() {
		return imageY;
	}

	public void setMotionNum(int motionNum) {
		this.motionNum += motionNum;
		this.motionNum %= 4;
	}
	
	public void setPositionNum(int positionNum) {
		this.positionNum = positionNum;
	}
	
	public void death() {
		this.timer.cancel();
		this.hitAreaMinus();
		weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
	}
	
	public int getHp() {
		return hp;
	}
}
