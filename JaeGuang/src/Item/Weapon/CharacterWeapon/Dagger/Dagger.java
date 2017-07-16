package Item.Weapon.CharacterWeapon.Dagger;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import Item.Weapon.CharacterWeapon.CharacterWeapon;
import Item.Weapon.CharacterWeapon.Bow.BowNormal;
import Item.Weapon.CharacterWeapon.Bow.BowSkill1;
import Item.Weapon.CharacterWeapon.Bow.BowSkill2;
import Item.Weapon.CharacterWeapon.Bow.BowSkillManager;

public class Dagger extends CharacterWeapon {

	private DaggerSkillManager dsm;
	private DaggerNormal dn = new DaggerNormal(0);
	private DaggerSkill1 dk1 = new DaggerSkill1(10);
	private DaggerSkill2 dk2 = new DaggerSkill2(30);
	
	public Dagger(String name, int physicalAttackPower, int requiredLevel){
		this.name = name;
		this.requiredLevel = requiredLevel;
		this.weaponSpeed = 0;
		this.dsm = dn;
		this.physicalAttackPower = physicalAttackPower + dn.getPhysicalAttackPower();
	}
	

	public void attackAreaPlus(Animal animal, int positionNum, int hitAreaCenterX, int hitAreaCenterY) {
		
		dsm.attackAreaPlus(animal, positionNum, hitAreaCenterX, hitAreaCenterY);
		
	}

	public void attackAreaMinus(int positionNum, int hitAreaCenterX, int hitAreaCenterY) {
		
		dsm.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
	}
	
	
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower) {
		dsm.attackAreaMinus(dc.getCh().getPositionNum(), dc.getCh().getHitAreaCenterX(), dc.getCh().getHitAreaCenterY());
		skillChage(dc.getCh().getSkillNum());
		dsm.attackAreaPlus(dc.getCh(), dc.getCh().getPositionNum(), dc.getCh().getHitAreaCenterX(), dc.getCh().getHitAreaCenterY());
		
		dsm.attackAreaHitTest(dc, physicalAttackPower);
	}
	
	
	public void attack(Animal animal, DisplayComponent dc, int physicalAttackPower) {
		
	}
	
	public void skillChage(int skillNum) {
		switch (skillNum) {
		case 0 : dsm = dn; break;
		case 1 : dsm = dk1;	break;
		case 2 : dsm = dk2; break;
		}

		this.physicalAttackPower = physicalAttackPower + dn.getPhysicalAttackPower();
	}
	
	public DaggerSkillManager dsmChage(int skillNum) {
		switch (skillNum) {
		case 0 : return dn;
		case 1 : return dk1;
		case 2 : return dk2;
		}
		return null;
	}
	
	public int getDecreaseMp(int skillNum) {
		
		DaggerSkillManager tempDsm = dsmChage(skillNum);
		
		return tempDsm.getDecreaseMp();
	}
}
