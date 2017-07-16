package Item.Weapon.CharacterWeapon.Hammer;

import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import Item.Weapon.Effect;

public class LongCrack extends Effect {
	private int count;
	

	
	public void makeCrack(DisplayComponent dc, int attackAreaCenterX, int attackAreaCenterY, int physicalAttackPower, int positionNum) {
		Timer timer = new Timer();
    	TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				switch (positionNum) {
				case DOWN : 
					attackAreaArray[attackAreaCenterX][attackAreaCenterY + count]++;

				if (attackAreaArray[attackAreaCenterX][attackAreaCenterY + count] < ATTACK_VALUE 
						|| attackAnimalArray[attackAreaCenterX][attackAreaCenterY + count][0] != null) {
					attackAreaArray[attackAreaCenterX][attackAreaCenterY + count]--;
					addLCrack(dc, attackAreaCenterX, attackAreaCenterY + count, physicalAttackPower); 
				} else {
					timer.cancel();
					attackAreaArray[attackAreaCenterX][attackAreaCenterY + count]--;
				}
				
				break;
				case LEFT : 
					attackAreaArray[attackAreaCenterX - count][attackAreaCenterY]++;
					
				if (attackAreaArray[attackAreaCenterX - count][attackAreaCenterY] < ATTACK_VALUE 
						|| attackAnimalArray[attackAreaCenterX - count][attackAreaCenterY][0] != null) {
					attackAreaArray[attackAreaCenterX - count][attackAreaCenterY]--;
					 addLCrack(dc, attackAreaCenterX - count, attackAreaCenterY, physicalAttackPower); 
				} else {
					timer.cancel();
					attackAreaArray[attackAreaCenterX - count][attackAreaCenterY]--;
				}
				break;
				case RIGHT : 
					attackAreaArray[attackAreaCenterX + count][attackAreaCenterY]++;
				if (attackAreaArray[attackAreaCenterX + count][attackAreaCenterY] < ATTACK_VALUE 
						|| attackAnimalArray[attackAreaCenterX + count][attackAreaCenterY][0] != null) {
					attackAreaArray[attackAreaCenterX + count][attackAreaCenterY]--;
					 addLCrack(dc, attackAreaCenterX + count, attackAreaCenterY, physicalAttackPower); 
				} else {
					timer.cancel();
					attackAreaArray[attackAreaCenterX + count][attackAreaCenterY]--;
				}
				break;
				case UP : 
					attackAreaArray[attackAreaCenterX][attackAreaCenterY - count]++;
				if (attackAreaArray[attackAreaCenterX][attackAreaCenterY - count] < ATTACK_VALUE 
						|| attackAnimalArray[attackAreaCenterX][attackAreaCenterY - count][0] != null) {
					attackAreaArray[attackAreaCenterX][attackAreaCenterY - count]--;
					 addLCrack(dc, attackAreaCenterX, attackAreaCenterY - count, physicalAttackPower); 
				} else {
					timer.cancel();
					attackAreaArray[attackAreaCenterX][attackAreaCenterY - count]--;
				}
				break;
				}
				
				count++;
			}
    	};
    	
    	count = 0;
    	timer.schedule(task, 0, 100);
	}


	public void addLCrack(DisplayComponent dc, int attackAreaCenterX, int attackAreaCenterY, int physicalAttackPower) {
		Crack longCrack = new Crack(1);
		dc.getEffect().add(longCrack);
		longCrack.moveMotion(dc, attackAreaCenterX, attackAreaCenterY, physicalAttackPower);
	}

	@Override
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void attack(Animal animal, DisplayComponent dc,
			int physicalAttackPower) {
		// TODO Auto-generated method stub
		
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


	@Override
	public Image getIm() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getImageX() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getImageY() {
		// TODO Auto-generated method stub
		return 0;
	}


}
