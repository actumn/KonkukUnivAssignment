package DisplayObject;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import Item.Weapon.Weapon;
import Item.Weapon.CharacterWeapon.CharacterWeapon;
import Item.Weapon.CharacterWeapon.Bow.Bow;
import Item.Weapon.CharacterWeapon.Dagger.Dagger;
import Item.Weapon.CharacterWeapon.Hammer.Hammer;
import Item.Weapon.CharacterWeapon.Sword.Sword;

public class Character extends Animal implements KeyListener, Movable {
	
	// 기본 능력치 필드
	private int defaultLv;
	private int defaultHp;
	private int defaultMp;
	private int defaultPhysicalAttackPower;
	private int defaultMagicalAttackPower;
	private int defaultPhysicalDefensePower;
	private int defaultMagicalDefensePower;
	private int defaultAccuracyRate;
	private int defaultEvasionRate;
	private int defaultExp;
	
	private int sp;
	private int ap;

	private boolean[] keyPushBoolean = { false, false, false, false, false, false, false, false, false }; // 키 불룬값
	private Thread thread;
	private CharacterWeapon weapon;
	private int weaponType;
	
	private Sword sword;
	private Dagger dagger;
	private Hammer hammer;
	private Bow bow;
	
	int key = 0;
	boolean key2 = true;

	public Character(DisplayComponent dc, int imageX, int imageY) {
		
		this.fileDirectory = new String[]{"move", "attack/sword", "attack/dagger", "attack/bow", "attack/hammer"};
		this.defaultDirectory = "image/character/";
		this.skillDirectory = new String[]{"/normal", "/skill1", "/skill2", "/skill3"};
		this.weaponType = 0;

		for (int i = 0; i < im.length; i++) {
			for (int j = 0; j < im[i].length; j++) {
				for (int k = 0; k < im[i][j].length; k++) {
					for (int l = 0; l < im[i][j][k].length; l++) {
						File file  = new File(defaultDirectory + fileDirectory[i] + skillDirectory[j] + "/position" + (k + 1) + (l + 1) + ".png");
						if (file.isFile()) {
							im[i][j][k][l] = Toolkit.getDefaultToolkit().getImage(defaultDirectory + fileDirectory[i] + skillDirectory[j] + "/position" + (k + 1) + (l + 1) + ".png");
						} else break;
					}
				}
			}
		}
		
		this.defaultHp = 2000;
		this.hp = defaultHp;
		this.defaultMp = 1000;
		this.mp = defaultMp;
		this.defaultExp = 100;
		this.exp = 0;
		this.lv = 1;

		this.imageX = imageX;
		this.imageY = imageY;
		this.hitAreaCenterX += imageX / (MOVE_VALUE * STOP_VALUE) + (BG_BLOCK / 2);
		this.hitAreaCenterY += imageY / (MOVE_VALUE * STOP_VALUE) + (BG_BLOCK / 2);
		hitAreaPlus();
		areaLimit();

		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {

					try {
						hitTest(dc); // 키값 지속 확인
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
		
		this.sword = new Sword("초보검", 20, 1);
		this.dagger = new Dagger("초보단검", 10, 5);
		this.hammer = new Hammer("초보망치", 30, 20);
		this.bow = new Bow("초보활", 5, 10);
		
		this.weaponType = 1;
		this.weapon = sword;
		weaponChange(sword);

		weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);
	}
	
	public void weaponChange(CharacterWeapon weapon) {
		this.weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
		this.weapon = weapon;
		this.attackSpeed = ACT_SPEED + weapon.getWeaponSpeed();
		this.physicalAttackPower = this.defaultPhysicalAttackPower + weapon.getPhysicalAttackPower();
		this.weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == LEFT_ARROW) keyPushBoolean[0] = true;
		else if (e.getKeyCode() == UP_ARROW) keyPushBoolean[1] = true;
		else if (e.getKeyCode() == RIGHT_ARROW) keyPushBoolean[2] = true;
		else if (e.getKeyCode() == DOWN_ARROW) keyPushBoolean[3] = true;
		
		if (e.getKeyCode() == SPACEBAR) keyPushBoolean[4] = true;
		else if (e.getKeyCode() == CTRL) keyPushBoolean[5] = true;
		else if (e.getKeyCode() == A) keyPushBoolean[6] = true;
		else if (e.getKeyCode() == S) keyPushBoolean[7] = true;
		else if (e.getKeyCode() == D) keyPushBoolean[8] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == LEFT_ARROW) keyPushBoolean[0] = false;
		else if (e.getKeyCode() == UP_ARROW) keyPushBoolean[1] = false;
		else if (e.getKeyCode() == RIGHT_ARROW) keyPushBoolean[2] = false;
		else if (e.getKeyCode() == DOWN_ARROW) keyPushBoolean[3] = false;
		
		if (e.getKeyCode() == SPACEBAR) keyPushBoolean[4] = false;
		else if (e.getKeyCode() == CTRL) {
			keyPushBoolean[5] = false;
			key2 = true;
		}
		else if (e.getKeyCode() == A) keyPushBoolean[6] = false;
		else if (e.getKeyCode() == S) keyPushBoolean[7] = false;
		else if (e.getKeyCode() == D) keyPushBoolean[8] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void hitTest(DisplayComponent dc) throws InterruptedException {

		// 왼쪽
		if (keyPushBoolean[0] == true) {
			if (hitAreaArray[hitAreaCenterX - 1][hitAreaCenterY] < BLOCK) {
			
				hitAreaMinus(); // 범위 이동
				weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
				hitAreaCenterX--;
				positionNum = MOVE_ARRAY_NUM + LEFT; // 캐릭터 방향 결정
				hitAreaPlus();
				weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);
	
				moveMotion(dc); // 이동
				synchronized (thread) {
					thread.wait();
				}
			} else if (hitAreaArray[hitAreaCenterX - 1][hitAreaCenterY] >= BLOCK) {
				weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
				positionNum = MOVE_ARRAY_NUM + LEFT; // 캐릭터 방향 결정
				weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);
				dc.repaint();
			}
		}
			

		// 위
		if (keyPushBoolean[1] == true) {
			if (hitAreaArray[hitAreaCenterX][hitAreaCenterY - 1] < BLOCK) {
				
				hitAreaMinus();
				weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
				hitAreaCenterY--;
				positionNum = MOVE_ARRAY_NUM + UP;
				hitAreaPlus();
				weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);

				moveMotion(dc);
				synchronized (thread) {
					thread.wait();
				}
			} else if (hitAreaArray[hitAreaCenterX][hitAreaCenterY - 1] >= BLOCK) {
				weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
				positionNum = MOVE_ARRAY_NUM + UP; // 캐릭터 방향 결정
				weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);
				dc.repaint();
			}
		}
			

		// 오른쪽
		if (keyPushBoolean[2] == true) {
			if (hitAreaArray[hitAreaCenterX + 1][hitAreaCenterY] < BLOCK) {

				hitAreaMinus();
				weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
				hitAreaCenterX++;
				positionNum = MOVE_ARRAY_NUM + RIGHT;
				hitAreaPlus();
				weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);

				moveMotion(dc);
				synchronized (thread) {
					thread.wait();
				}
			} else if (hitAreaArray[hitAreaCenterX + 1][hitAreaCenterY] >= BLOCK) {
				weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
				positionNum = MOVE_ARRAY_NUM + RIGHT; // 캐릭터 방향 결정
				weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);
				dc.repaint();
			}
		}
			 

		// 아래
		if (keyPushBoolean[3] == true) {
			if (hitAreaArray[hitAreaCenterX][hitAreaCenterY + 1] < BLOCK) {
	
				hitAreaMinus();
				weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
				hitAreaCenterY++;
				positionNum = MOVE_ARRAY_NUM + DOWN;
				hitAreaPlus();
				weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);
				moveMotion(dc);
				
				synchronized (thread) {
					thread.wait();
				}
			} else if (hitAreaArray[hitAreaCenterX][hitAreaCenterY + 1] >= BLOCK) {
				weapon.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
				positionNum = MOVE_ARRAY_NUM + DOWN; // 캐릭터 방향 결정
				weapon.attackAreaPlus(this, positionNum, hitAreaCenterX, hitAreaCenterY);
				dc.repaint();
			}
		}
		
		// 스페이스 바
		if (keyPushBoolean[4] == true || keyPushBoolean[6] == true || keyPushBoolean[7] == true) {

			int tempSkillNum = 0;
			
			if (keyPushBoolean[4]) tempSkillNum = 0;
			else if (keyPushBoolean[6]) tempSkillNum = 1;
			else if (keyPushBoolean[7]) tempSkillNum = 2;
			
			if (mp - weapon.getDecreaseMp(tempSkillNum) >= 0) {
				mp -= weapon.getDecreaseMp(tempSkillNum);
				
				skillNum = tempSkillNum;
				
				arrayNum += weaponType;
				attackMotion(dc);
				
				synchronized (thread) {
					thread.wait();
				}
			} else {
				System.out.println("mp가 부족행");
			}
			
			/*
			// 몬스터 공격
			for (int i = 1; i <= weapon.getWeaponRange(); i++) {
					if (positionNum == DOWN && attackAreaArray[attackAreaCenterX][attackAreaCenterY + i] >= BLOCK) { 
						attack(attackAnimalArray[attackAreaCenterX][attackAreaCenterY + i][0]);
					} else if (positionNum == UP && attackAreaArray[attackAreaCenterX][attackAreaCenterY - i] >= BLOCK) {
						attack(attackAnimalArray[attackAreaCenterX][attackAreaCenterY - i][0]);
					} else if (positionNum == RIGHT && attackAreaArray[attackAreaCenterX + i][attackAreaCenterY] >= BLOCK) {
						attack(attackAnimalArray[attackAreaCenterX + i][attackAreaCenterY][0]);
					} else if (positionNum == LEFT && attackAreaArray[attackAreaCenterX - i][attackAreaCenterY] >= BLOCK) {
						attack(attackAnimalArray[attackAreaCenterX - i][attackAreaCenterY][0]);
					}
				
				
			}
			*/
		}
		
		if (keyPushBoolean[5] == true && key2 == true) {
			key++;
			
			switch (key) {
			case 1 : requiredLv(dagger.getRequiredLevel(), dagger);
				break;
			case 2 : requiredLv(bow.getRequiredLevel(), bow);
				break;  
 			case 3 : requiredLv(hammer.getRequiredLevel(), hammer);
				break;
			default:
				weaponChange(sword);   
				weaponType = 1;
				key = 0;       
				break;
			}
			key2 = false;
		}
		//test();
		//Weapon.test();

	}
	
	public void requiredLv(int lv, CharacterWeapon weapon) {
		if (this.lv >= lv) {
			weaponChange(weapon);
			weaponType++;
		} else {
			weaponChange(sword);
			weaponType = 1;
			key = 0;
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
					case DOWN: imageY += MOVE_VALUE; break;
					case LEFT: imageX -= MOVE_VALUE; break;
					case RIGHT: imageX += MOVE_VALUE; break;
					case UP: imageY -= MOVE_VALUE; break;
					}
					setMotionNum(1); // 모션
					dc.repaint();
					count++;

				} else {
					timer.cancel();
					synchronized (thread) {
						thread.notify(); // 스레드 재시작
					}

				}
			}
		};

		count = 0;
		timer.schedule(task, 0, ACT_SPEED);
		
	}

	public void attackMotion(DisplayComponent dc) {

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

				motionNum++;
				
				weapon.attackAreaHitTest(dc, physicalAttackPower);
				
				if (im[arrayNum][skillNum][positionNum][motionNum] != null) {
					dc.repaint();
				} else {
					arrayNum = 0;
					skillNum = 0;
					motionNum = 0;
					dc.repaint();
					timer.cancel();
					synchronized (thread) {
						thread.notify(); // 스레드 재시작
					}

				}
			}
		};

		count = 0;
		timer.schedule(task, 0, attackSpeed);
	}

	public Image getIm() {
		return im[arrayNum][skillNum][positionNum][motionNum];
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
	
	public int getHitAreaCenterX() {
		return this.hitAreaCenterX;
	}
	
	public int getHitAreaCenterY() {
		return this.hitAreaCenterY;
	}
	
	public int getMotionNum() {
		return this.motionNum;
	}
	
	public int getPositionNum() {
		return this.positionNum;
	}
	
	public int getSkillNum() {
		return this.skillNum;
	}

	@Override
	public void death() {
		// TODO Auto-generated method stub
		
	}

	public int getDefaultHp() {
		return defaultHp;
	}
	
	public int getDefaultMp() {
		return defaultMp;
	}
	
	public int getDefaultExp() {
		return defaultExp;
	}

	
	public void setExp(int exp){
		this.exp += exp;
		
		if (defaultExp <= this.exp) {
			this.exp -= defaultExp;
			this.hp = defaultHp;
			this.mp = defaultMp;
			lv++;
		}
	}

	public int getSp() {
		return sp;
	}
	
	public int getAp() {
		return ap;
	}

	public void setSp(int sp) {
		this.sp += sp;
	}

	public void setAp(int ap) {
		this.ap += ap;
	}

	public int getDefaultPhysicalAttackPower() {
		return defaultPhysicalAttackPower;
	}

	public void setDefaultPhysicalAttackPower(int defaultPhysicalAttackPower) {
		this.defaultPhysicalAttackPower = defaultPhysicalAttackPower;
	}

	public int getDefaultMagicalAttackPower() {
		return defaultMagicalAttackPower;
	}

	public void setDefaultMagicalAttackPower(int defaultMagicalAttackPower) {
		this.defaultMagicalAttackPower = defaultMagicalAttackPower;
	}

	public int getDefaultPhysicalDefensePower() {
		return defaultPhysicalDefensePower;
	}

	public void setDefaultPhysicalDefensePower(int defaultPhysicalDefensePower) {
		this.defaultPhysicalDefensePower = defaultPhysicalDefensePower;
	}

	public int getDefaultMagicalDefensePower() {
		return defaultMagicalDefensePower;
	}

	public void setDefaultMagicalDefensePower(int defaultMagicalDefensePower) {
		this.defaultMagicalDefensePower = defaultMagicalDefensePower;
	}

	public void setDefaultHp(int defaultHp) {
		this.defaultHp = defaultHp;
	}

	public void setDefaultMp(int defaultMp) {
		this.defaultMp = defaultMp;
	}

	public Thread getThread() {
		return thread;
	}

}
