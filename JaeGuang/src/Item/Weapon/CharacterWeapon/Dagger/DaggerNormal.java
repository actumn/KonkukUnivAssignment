package Item.Weapon.CharacterWeapon.Dagger;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import Item.Weapon.CharacterWeapon.Skill;

public class DaggerNormal extends Skill implements DaggerSkillManager {
	
	public DaggerNormal(int physicalAttackPower){
		this.physicalAttackPower = physicalAttackPower;
		this.attackDistance = 1;
	}
	
	public void attackAreaPlus(Animal animal, int positionNum, int hitAreaCenterX, int hitAreaCenterY) {
		
		attackAreaDistance(positionNum, hitAreaCenterX, hitAreaCenterY);
		
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] += ATTACK;
		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = animal;
		
		attackAreaArray[attackAreaCenterX][attackAreaCenterY]++;
		
	}

	public void attackAreaMinus(int positionNum, int hitAreaCenterX, int hitAreaCenterY) {
		
		attackAreaDistance(positionNum, hitAreaCenterX, hitAreaCenterY);
		
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] -= ATTACK;
		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = null;
		
		attackAreaArray[attackAreaCenterX][attackAreaCenterY]--;
	}
	
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower) {
		if (dc.getCh().getMotionNum() == 3) {
			
			
			if (attackAreaArray[attackAreaCenterX][attackAreaCenterY] > ATTACK_VALUE) {
				attack(attackAnimalArray[attackAreaCenterX][attackAreaCenterY][0],
						dc, physicalAttackPower);
			}
			
			
			
			
		}
	}
	
	public void attack(Animal animal, DisplayComponent dc, int physicalAttackPower) {
		if (animal != null) {
			animal.setHp(-physicalAttackPower);
			death(animal, dc);
		}
		
	}
	
}
