package Item.Weapon;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;

public interface Attackable {
	
	public static final int ATTACK_VALUE = 20;
	public static final int ATTACK = 50;
	
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 0;
	
	public void attackAreaHitTest(DisplayComponent dc, int physicalAttackPower);
	public void attack(Animal animal, DisplayComponent dc, int physicalAttackPower);

}
