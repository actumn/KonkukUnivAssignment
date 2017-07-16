package Item.Weapon.CharacterWeapon.Sword;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import Item.Weapon.CharacterWeapon.CharacterWeapon;

public class Sword extends CharacterWeapon {
	
	public Sword(String name, int physicalAttackPower, int requiredLevel){
		this.name = name;
		this.physicalAttackPower = physicalAttackPower;
		this.requiredLevel = requiredLevel;
		this.weaponSpeed = 10;
		
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
