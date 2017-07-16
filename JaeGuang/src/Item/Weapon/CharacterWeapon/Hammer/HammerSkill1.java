package Item.Weapon.CharacterWeapon.Hammer;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import Item.Weapon.CharacterWeapon.Skill;

public class HammerSkill1 extends Skill implements HammerSkillManager {
	
	private int count;
	
	public HammerSkill1(int physicalAttackPower){
		this.physicalAttackPower = physicalAttackPower;
		this.attackDistance = 1;
		this.decreaseMp = 20;
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
	public void attackAreaHitTest(DisplayComponent dc,
			int physicalAttackPower) {
		// TODO Auto-generated method stub
		if (dc.getCh().getMotionNum() == 12) {
			
			LongCrack lc1 = new LongCrack();
			lc1.makeCrack(dc, attackAreaCenterX, attackAreaCenterY, physicalAttackPower + this.physicalAttackPower, dc.getCh().getPositionNum());
			
			
		}
	}
	@Override
	public void attack(Animal animal, DisplayComponent dc,
			int physicalAttackPower) {
		// TODO Auto-generated method stub
		
	}

}
