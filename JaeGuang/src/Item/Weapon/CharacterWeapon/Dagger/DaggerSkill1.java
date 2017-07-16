package Item.Weapon.CharacterWeapon.Dagger;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import Item.Weapon.CharacterWeapon.Skill;

public class DaggerSkill1 extends Skill implements DaggerSkillManager {
	
	private ThrowingDagger tDagger;

	public DaggerSkill1(int physicalAttackPower){
		this.physicalAttackPower = physicalAttackPower;
		this.attackDistance = 1;
		this.decreaseMp = 10;
	}
	
	@Override
	public void attackAreaPlus(Animal animal, int positionNum,
			int hitAreaCenterX, int hitAreaCenterY) {
		// TODO Auto-generated method stub
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] += ATTACK;

		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = animal;
		
	}

	@Override
	public void attackAreaMinus(int positionNum, int hitAreaCenterX,
			int hitAreaCenterY) {
		// TODO Auto-generated method stub
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] -= ATTACK;

		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = null;
		
	}

	@Override
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower) {
		// TODO Auto-generated method stub
		if (dc.getCh().getMotionNum() == 3) {
			
			
			tDagger = new ThrowingDagger(dc.getCh().getImageX(), dc.getCh().getImageY(), dc.getCh().getPositionNum(), 1);
			dc.getEffect().add(tDagger);
			tDagger.moveMotion(dc, physicalAttackPower + this.physicalAttackPower);
		
		
		
		}
	}

	@Override
	public void attack(Animal animal, DisplayComponent dc,
			int physicalAttackPower) {
		// TODO Auto-generated method stub
		
	}

}
