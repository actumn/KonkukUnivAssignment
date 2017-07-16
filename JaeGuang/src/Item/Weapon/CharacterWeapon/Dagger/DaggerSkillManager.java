package Item.Weapon.CharacterWeapon.Dagger;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;

public interface DaggerSkillManager {
	
	public void attackAreaPlus(Animal animal, int positionNum, int hitAreaCenterX, int hitAreaCenterY);
	public void attackAreaMinus(int positionNum, int hitAreaCenterX, int hitAreaCenterY);
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower);
	
	public int getDecreaseMp();
}
