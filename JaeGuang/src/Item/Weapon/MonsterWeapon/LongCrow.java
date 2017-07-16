package Item.Weapon.MonsterWeapon;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;

public class LongCrow extends MonsterWeapon {

	public LongCrow(String name, int physicalAttackPower, int requiredLevel) {
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

		for (int i = 1; i < 5; i++) {
			
			attackAreaArray[attackAreaCenterX][attackAreaCenterY + i]++;
			attackAreaArray[attackAreaCenterX - i][attackAreaCenterY]++;
			attackAreaArray[attackAreaCenterX + i][attackAreaCenterY]++;
			attackAreaArray[attackAreaCenterX][attackAreaCenterY - i]++;
			
		}
		
	}

	public void attackAreaMinus(int positionNum, int hitAreaCenterX, int hitAreaCenterY) {
		
		attackAreaDistance(positionNum, hitAreaCenterX, hitAreaCenterY);
		
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] -= ATTACK_VALUE;
		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = null;
		
		for (int i = 1; i < 5; i++) {
			
			attackAreaArray[attackAreaCenterX][attackAreaCenterY + i]--;
			attackAreaArray[attackAreaCenterX - i][attackAreaCenterY]--;
			attackAreaArray[attackAreaCenterX + i][attackAreaCenterY]--;
			attackAreaArray[attackAreaCenterX][attackAreaCenterY - i]--;
			
		}
	}
	
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower) {
		if (dc.getCh().getMotionNum() == 3) {
			
			for (int i = 1; i < 5; i++) {
				if (attackAreaArray[attackAreaCenterX - i][attackAreaCenterY] > ATTACK) {
					attack(attackAnimalArray[attackAreaCenterX - i][attackAreaCenterY][0],
							dc, physicalAttackPower);
				}
				if (attackAreaArray[attackAreaCenterX][attackAreaCenterY - i] > ATTACK) {
					attack(attackAnimalArray[attackAreaCenterX][attackAreaCenterY - i][0],
							dc, physicalAttackPower);
				}
				if (attackAreaArray[attackAreaCenterX][attackAreaCenterY + i] > ATTACK) {
					attack(attackAnimalArray[attackAreaCenterX][attackAreaCenterY + i][0],
							dc, physicalAttackPower);
				}
				if (attackAreaArray[attackAreaCenterX + 1][attackAreaCenterY] > ATTACK) {
					attack(attackAnimalArray[attackAreaCenterX + i][attackAreaCenterY][0],
							dc, physicalAttackPower);
				}
			
			}
			
			
			
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
		
		for (int i = 1; i < 5; i++) {
			if (attackAreaArray[attackAreaCenterX - i][attackAreaCenterY] > ATTACK) {
				animal.setPositionNum(LEFT);
				return true;
			} else if (attackAreaArray[attackAreaCenterX + i][attackAreaCenterY] > ATTACK) {
				animal.setPositionNum(RIGHT);
				return true;
			} else if (attackAreaArray[attackAreaCenterX][attackAreaCenterY - i] > ATTACK) {
				animal.setPositionNum(UP);
				return true;
			} else if (attackAreaArray[attackAreaCenterX][attackAreaCenterY + i] > ATTACK) {
				animal.setPositionNum(DOWN);
				return true;
			}
		}
		return false;
	}
}
