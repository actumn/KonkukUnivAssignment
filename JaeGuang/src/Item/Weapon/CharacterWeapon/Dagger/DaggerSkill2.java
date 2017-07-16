package Item.Weapon.CharacterWeapon.Dagger;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import DisplayObject.Monster;
import Item.Weapon.CharacterWeapon.Skill;

public class DaggerSkill2 extends Skill implements DaggerSkillManager {
	
	private int count;
	
	public DaggerSkill2(int physicalAttackPower){
		this.physicalAttackPower = physicalAttackPower;
		this.attackDistance = 1;
		this.decreaseMp = 20;
		this.attackDistance = 7;
	}

	@Override
	public void attackAreaPlus(Animal animal, int positionNum,
			int hitAreaCenterX, int hitAreaCenterY) {
		// TODO Auto-generated method stub
		attackAreaDistance(positionNum, hitAreaCenterX, hitAreaCenterY);
		
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] += ATTACK;

		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = animal;
		
		
	}

	@Override
	public void attackAreaMinus(int positionNum, int hitAreaCenterX,
			int hitAreaCenterY) {
		// TODO Auto-generated method stub
		attackAreaDistance(positionNum, hitAreaCenterX, hitAreaCenterY);
		
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] -= ATTACK;
		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = null;
		
		
		
	}

	@Override
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower) {
		// TODO Auto-generated method stub
		if (dc.getCh().getMotionNum() == 11) {
			
			makeSlash(dc, physicalAttackPower , 4);
			
		}
	}

	@Override
	public void attack(Animal animal, DisplayComponent dc,
			int physicalAttackPower) {
		// TODO Auto-generated method stub
		
	}
	
	public void makeSlash(DisplayComponent dc, int physicalAttackPower, int number) {
		
		Timer timer = new Timer();
    	TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if (count < number) {
					if (dc.getMonster().size() > 0) {
						addSlash(dc, physicalAttackPower, number);
						count++;
					}
				} else {
					timer.cancel();
				}
				
			}
    	};
    	
    	count = 0;
    	timer.schedule(task, 0, 320);
    	
	}
	
	public void addSlash(DisplayComponent dc, int physicalAttackPower, int number) {
		int num = (int)(Math.random()*(dc.getMonster().size()) + 0);
		int slashNum = 0;
		if (count == number - 1) slashNum = 1;
		
		Slash slash = new Slash(slashNum);
		dc.getEffect().add(slash);
		slash.moveMotion(dc, dc.getMonster().get(num).getImageX(), dc.getMonster().get(num).getImageY(), physicalAttackPower + this.physicalAttackPower);
	}

}
