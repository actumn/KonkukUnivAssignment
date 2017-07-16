package Item.Weapon.CharacterWeapon.Bow;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import Item.Weapon.CharacterWeapon.CharacterWeapon;

public class Bow extends CharacterWeapon {
	
	private BowSkillManager bsm;
	private BowNormal bn = new BowNormal(0);
	private BowSkill1 bk1 = new BowSkill1(10);
	private BowSkill2 bk2 = new BowSkill2(30);
	
	public Bow(String name, int physicalAttackPower, int requiredLevel){
		this.name = name;
		this.requiredLevel = requiredLevel;
		this.weaponSpeed = 0;
		this.bsm = bn;
		this.physicalAttackPower = physicalAttackPower + bn.getPhysicalAttackPower();
	}
	

	public void attackAreaPlus(Animal animal, int positionNum, int hitAreaCenterX, int hitAreaCenterY) {
		
		bsm.attackAreaPlus(animal, positionNum, hitAreaCenterX, hitAreaCenterY);
		
	}

	public void attackAreaMinus(int positionNum, int hitAreaCenterX, int hitAreaCenterY) {
		
		bsm.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
	}
	
	
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower) {
		bsm.attackAreaMinus(dc.getCh().getPositionNum(), dc.getCh().getHitAreaCenterX(), dc.getCh().getHitAreaCenterY());
		skillChage(dc.getCh().getSkillNum());
		bsm.attackAreaPlus(dc.getCh(), dc.getCh().getPositionNum(), dc.getCh().getHitAreaCenterX(), dc.getCh().getHitAreaCenterY());
		
		bsm.attackAreaHitTest(dc, physicalAttackPower);
	}
	
	
	public void attack(Animal animal, DisplayComponent dc, int physicalAttackPower) {
		
	}
	
	public void skillChage(int skillNum) {
		switch (skillNum) {
		case 0 : bsm = bn; break;
		case 1 : bsm = bk1;	break;
		case 2 : bsm = bk2; break;
		}

		this.physicalAttackPower = physicalAttackPower + bn.getPhysicalAttackPower();
	}
	
	public BowSkillManager bsmChage(int skillNum) {
		switch (skillNum) {
		case 0 : return bn;
		case 1 : return bk1;
		case 2 : return bk2;
		}
		return null;
	}
	
	public int getDecreaseMp(int skillNum) {
		
		BowSkillManager tempBsm = bsmChage(skillNum);
		
		return tempBsm.getDecreaseMp();
	}
}
