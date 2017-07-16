package Item.Weapon.CharacterWeapon.Bow;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import Item.Weapon.CharacterWeapon.CharacterWeapon;

public class BowNormal extends CharacterWeapon implements BowSkillManager {
	
	private Arrow arrow;
	
	public BowNormal(int physicalAttackPower) {
		this.physicalAttackPower = physicalAttackPower;
		this.attackDistance = 1;
		
	}
	
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower) {
		if (dc.getCh().getMotionNum() == 3) {
		
		
			arrow = new Arrow(dc.getCh().getImageX(), dc.getCh().getImageY(), dc.getCh().getPositionNum(), 0);
			dc.getEffect().add(arrow);
			arrow.moveMotion(dc, physicalAttackPower);
		
		
		
		}
	}
	
	public void attackAreaPlus(Animal animal, int positionNum, int hitAreaCenterX, int hitAreaCenterY) {

		attackAreaArray[hitAreaCenterX][hitAreaCenterY] += ATTACK;

		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = animal;
	}

	public void attackAreaMinus(int positionNum, int hitAreaCenterX, int hitAreaCenterY) {

		attackAreaArray[hitAreaCenterX][hitAreaCenterY] -= ATTACK;

		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = null;
	}
	
	
	public void attack(Animal animal, DisplayComponent dc, int physicalAttackPower) {
	}

	@Override
	public int getDecreaseMp() {
		// TODO Auto-generated method stub
		return 0;
	}

}
