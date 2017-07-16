package Item.Weapon.MonsterWeapon;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;

public class Crow extends MonsterWeapon {
	public Crow(String name, int physicalAttackPower, int requiredLevel){
		this.name = name;
		this.physicalAttackPower = physicalAttackPower;
		this.requiredLevel = requiredLevel;
		this.weaponSpeed = 0;
		
		this.attackDistance = 0;
	}
	
	public void attackAreaPlus(Animal animal, int positionNum, int hitAreaCenterX, int hitAreaCenterY) {
		
		attackAreaDistance(positionNum, hitAreaCenterX, hitAreaCenterY);
		
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] += ATTACK_VALUE;
		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = animal;
		
		attackAreaArray[attackAreaCenterX - 1][attackAreaCenterY]++;
		attackAreaArray[attackAreaCenterX + 1][attackAreaCenterY]++;
		attackAreaArray[attackAreaCenterX][attackAreaCenterY - 1]++;
		attackAreaArray[attackAreaCenterX][attackAreaCenterY + 1]++;
		
	}

	public void attackAreaMinus(int positionNum, int hitAreaCenterX, int hitAreaCenterY) {
		
		attackAreaDistance(positionNum, hitAreaCenterX, hitAreaCenterY);
		
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] -= ATTACK_VALUE;
		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = null;
		
		attackAreaArray[attackAreaCenterX - 1][attackAreaCenterY]--;
		attackAreaArray[attackAreaCenterX + 1][attackAreaCenterY]--;
		attackAreaArray[attackAreaCenterX][attackAreaCenterY - 1]--;
		attackAreaArray[attackAreaCenterX][attackAreaCenterY + 1]--;
	}
	
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower) {
			
			
			if (attackAreaArray[attackAreaCenterX - 1][attackAreaCenterY] > ATTACK) {
				attack(attackAnimalArray[attackAreaCenterX - 1][attackAreaCenterY][0], dc, physicalAttackPower);
			}
			if (attackAreaArray[attackAreaCenterX][attackAreaCenterY - 1] > ATTACK) {
				attack(attackAnimalArray[attackAreaCenterX][attackAreaCenterY - 1][0], dc, physicalAttackPower);
			}
			if (attackAreaArray[attackAreaCenterX][attackAreaCenterY + 1] > ATTACK) {
				attack(attackAnimalArray[attackAreaCenterX][attackAreaCenterY + 1][0], dc, physicalAttackPower);
			}
			if (attackAreaArray[attackAreaCenterX + 1][attackAreaCenterY]> ATTACK) {
				attack(attackAnimalArray[attackAreaCenterX + 1][attackAreaCenterY][0], dc, physicalAttackPower);
			}
			
			
			
			
		
	}
	
	public void attack(Animal animal, DisplayComponent dc, int physicalAttackPower) {
		
		if(animal != null){
			animal.setHp(-physicalAttackPower);
			death(animal, dc);
		}
	}

	@Override
	public boolean attackAreaHitTest(Animal animal) {
		// TODO Auto-generated method stub
		if (attackAreaArray[attackAreaCenterX - 1][attackAreaCenterY] > ATTACK) {
			animal.setPositionNum(LEFT);
			return true;
		} else if (attackAreaArray[attackAreaCenterX + 1][attackAreaCenterY] > ATTACK) {
			animal.setPositionNum(RIGHT);
			return true;
		} else if (attackAreaArray[attackAreaCenterX][attackAreaCenterY - 1] > ATTACK) {
			animal.setPositionNum(UP);
			return true;
		} else if (attackAreaArray[attackAreaCenterX][attackAreaCenterY + 1] > ATTACK) {
			animal.setPositionNum(DOWN);
			return true;
		}
		
		return false;
	}
}
