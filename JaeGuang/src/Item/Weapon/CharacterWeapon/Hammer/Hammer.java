package Item.Weapon.CharacterWeapon.Hammer;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import Item.Weapon.CharacterWeapon.CharacterWeapon;

public class Hammer extends CharacterWeapon {
	
	private HammerSkillManager hsm;
	private HammerNormal hn = new HammerNormal(0);
	private HammerSkill1 hk1 = new HammerSkill1(20);
	private HammerSkill2 hk2 = new HammerSkill2(50);
	
	public Hammer(String name, int physicalAttackPower, int requiredLevel){
		this.name = name;
		this.requiredLevel = requiredLevel;
		this.weaponSpeed = 25;
		this.hsm = hn;
		this.physicalAttackPower = physicalAttackPower + hn.getPhysicalAttackPower();
	}
	

	public void attackAreaPlus(Animal animal, int positionNum, int hitAreaCenterX, int hitAreaCenterY) {
		
		hsm.attackAreaPlus(animal, positionNum, hitAreaCenterX, hitAreaCenterY);
		
	}

	public void attackAreaMinus(int positionNum, int hitAreaCenterX, int hitAreaCenterY) {
		
		hsm.attackAreaMinus(positionNum, hitAreaCenterX, hitAreaCenterY);
	}
	
	
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower) {
		hsm.attackAreaMinus(dc.getCh().getPositionNum(), dc.getCh().getHitAreaCenterX(), dc.getCh().getHitAreaCenterY());
		skillChage(dc.getCh().getSkillNum());
		hsm.attackAreaPlus(dc.getCh(), dc.getCh().getPositionNum(), dc.getCh().getHitAreaCenterX(), dc.getCh().getHitAreaCenterY());
		
		hsm.attackAreaHitTest(dc, physicalAttackPower);
	}
	
	
	public void attack(Animal animal, DisplayComponent dc, int physicalAttackPower) {
		
	}
	
	public void skillChage(int skillNum) {
		switch (skillNum) {
		case 0 : hsm = hn; break;
		case 1 : hsm = hk1;	break;
		case 2 : hsm = hk2; break;
		}

		this.physicalAttackPower = physicalAttackPower + hn.getPhysicalAttackPower();
	}
	
	public HammerSkillManager hsmChage(int skillNum) {
		switch (skillNum) {
		case 0 : return hn;
		case 1 : return hk1;
		case 2 : return hk2;
		}
		return null;
	}
	
	public int getDecreaseMp(int skillNum) {
		
		HammerSkillManager tempHsm = hsmChage(skillNum);
		
		return tempHsm.getDecreaseMp();
	}

}
