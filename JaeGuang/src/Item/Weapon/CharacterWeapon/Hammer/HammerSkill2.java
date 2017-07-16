package Item.Weapon.CharacterWeapon.Hammer;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import Item.Weapon.CharacterWeapon.Skill;

public class HammerSkill2 extends Skill implements HammerSkillManager {

	public HammerSkill2(int physicalAttackPower){
		this.physicalAttackPower = physicalAttackPower;
		this.attackDistance = 1;
		this.decreaseMp = 50;
	}

	@Override
	public void attackAreaPlus(Animal animal, int positionNum,
			int hitAreaCenterX, int hitAreaCenterY) {
		// TODO Auto-generated method stub

		attackAreaDistance(positionNum, hitAreaCenterX, hitAreaCenterY);
		
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] += ATTACK;
		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = animal;
		
		for (int i = -2; i < 3; i++) {
			for (int j = -2; j < 3 ; j++) {
				attackAreaArray[hitAreaCenterX + j][(hitAreaCenterY + 1) + i]++;
			}
		}
	}

	@Override
	public void attackAreaMinus(int positionNum, int hitAreaCenterX,
			int hitAreaCenterY) {
		// TODO Auto-generated method stub

		attackAreaDistance(positionNum, hitAreaCenterX, hitAreaCenterY);
		
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] -= ATTACK;
		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = null;
		
		for (int i = -2; i < 3; i++) {
			for (int j = -2; j < 3 ; j++) {
				attackAreaArray[hitAreaCenterX + j][(hitAreaCenterY + 1) + i]--;
			}
		}
	}

	@Override
	public void attackAreaHitTest(DisplayComponent dc,
			int physicalAttackPower) {
		// TODO Auto-generated method stub
		if (dc.getCh().getMotionNum() == 20) {
			
			for (int i = -2; i < 3; i++) {
				for (int j = -2; j < 3 ; j++) {
					if (attackAreaArray[dc.getCh().getHitAreaCenterX() + j][(dc.getCh().getHitAreaCenterY() + 1) + i] > ATTACK_VALUE) {
						attack(attackAnimalArray[dc.getCh().getHitAreaCenterX() + j][(dc.getCh().getHitAreaCenterY() + 1) + i][0], dc, physicalAttackPower + this.physicalAttackPower);
					}
				}
			}
			/*
			for (int i = -2; i < 3; i++) {
				for (int j = 0; j < 3 ; j++) {
					if (attackAreaArray[dc.getCh().getHitAreaCenterX() - j][(dc.getCh().getHitAreaCenterY() + 1) + i] > ATTACK_VALUE) {
						attack(attackAnimalArray[dc.getCh().getHitAreaCenterX() - j][(dc.getCh().getHitAreaCenterY() + 1) + i][0], dc, physicalAttackPower);
					}
					if (attackAreaArray[dc.getCh().getHitAreaCenterX() + j][(dc.getCh().getHitAreaCenterY() + 1) + i] > ATTACK_VALUE) {
						attack(attackAnimalArray[dc.getCh().getHitAreaCenterX() + j][(dc.getCh().getHitAreaCenterY() + 1) + i][0], dc, physicalAttackPower);
					}
				}
			}*/
			
			LongCrack lc1 = new LongCrack();
			LongCrack lc2 = new LongCrack();
			LongCrack lc3 = new LongCrack();
			LongCrack lc4 = new LongCrack();
			
			lc1.makeCrack(dc, dc.getCh().getHitAreaCenterX() - 2, dc.getCh().getHitAreaCenterY() + 1, physicalAttackPower, LEFT);
			lc2.makeCrack(dc, dc.getCh().getHitAreaCenterX() + 2, dc.getCh().getHitAreaCenterY() + 1, physicalAttackPower, RIGHT);
			lc3.makeCrack(dc, dc.getCh().getHitAreaCenterX(), dc.getCh().getHitAreaCenterY() + 1 - 2, physicalAttackPower, UP);
			lc4.makeCrack(dc, dc.getCh().getHitAreaCenterX(), dc.getCh().getHitAreaCenterY() + 1 + 2, physicalAttackPower, DOWN);
			
			
		}
	}
	
	


	@Override
	public void attack(Animal animal, DisplayComponent dc,
			int physicalAttackPower) {
		// TODO Auto-generated method stub
		if (animal != dc.getCh()) {
			animal.setHp(-physicalAttackPower);
			death(animal, dc);
		}
	}

}
