package Item.Weapon.CharacterWeapon.Hammer;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import Item.Weapon.CharacterWeapon.CharacterWeapon;
import Item.Weapon.CharacterWeapon.Skill;

public class HammerNormal extends CharacterWeapon implements HammerSkillManager {
	public HammerNormal(int physicalAttackPower){
		this.physicalAttackPower = physicalAttackPower;
		this.attackDistance = 1;
	}
	

	public void attackAreaPlus(Animal animal, int positionNum, int hitAreaCenterX, int hitAreaCenterY) {
		
		attackAreaDistance(positionNum, hitAreaCenterX, hitAreaCenterY);
		
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] += ATTACK;
		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = animal;
		
	}

	public void attackAreaMinus(int positionNum, int hitAreaCenterX, int hitAreaCenterY) {
		
		attackAreaDistance(positionNum, hitAreaCenterX, hitAreaCenterY);
		
		attackAreaArray[hitAreaCenterX][hitAreaCenterY] -= ATTACK;
		attackAnimalArray[hitAreaCenterX][hitAreaCenterY][0] = null;
		
	}
	
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower) {
		if (dc.getCh().getMotionNum() == 3) {
		
			addSCrack(dc, attackAreaCenterX, attackAreaCenterY, physicalAttackPower);	
		
			switch (dc.getCh().getPositionNum()) {
			case DOWN:
				addSCrack(dc, attackAreaCenterX - 1, attackAreaCenterY, physicalAttackPower);
				addSCrack(dc, attackAreaCenterX + 1, attackAreaCenterY, physicalAttackPower);
				addSCrack(dc, attackAreaCenterX, attackAreaCenterY + 1, physicalAttackPower);
				break;
			case LEFT:
				addSCrack(dc, attackAreaCenterX, attackAreaCenterY - 1, physicalAttackPower);
				addSCrack(dc, attackAreaCenterX, attackAreaCenterY + 1, physicalAttackPower);
				addSCrack(dc, attackAreaCenterX - 1, attackAreaCenterY, physicalAttackPower);
				break;
			case RIGHT:
				addSCrack(dc, attackAreaCenterX, attackAreaCenterY - 1, physicalAttackPower);
				addSCrack(dc, attackAreaCenterX, attackAreaCenterY + 1, physicalAttackPower);
				addSCrack(dc, attackAreaCenterX + 1, attackAreaCenterY, physicalAttackPower);
				break;
			case UP:
				addSCrack(dc, attackAreaCenterX - 1, attackAreaCenterY, physicalAttackPower);
				addSCrack(dc, attackAreaCenterX + 1, attackAreaCenterY, physicalAttackPower);
				addSCrack(dc, attackAreaCenterX, attackAreaCenterY - 1, physicalAttackPower);
				break;
			}
		
		
		}
	}
	
	public void addSCrack(DisplayComponent dc, int attackAreaCenterX, int attackAreaCenterY, int physicalAttackPower) {
		Crack smallCrack = new Crack(0);
		dc.getEffect().add(smallCrack);
		smallCrack.moveMotion(dc, attackAreaCenterX, attackAreaCenterY, physicalAttackPower);
	}
	
	public void attack(Animal animal, DisplayComponent dc, int physicalAttackPower) {
		
	}


	@Override
	public int getDecreaseMp() {
		// TODO Auto-generated method stub
		return 0;
	}
}
