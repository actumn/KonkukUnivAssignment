package Item.Weapon.MonsterWeapon;

import DisplayObject.Animal;
import DisplayObject.Monster;
import Item.Weapon.Weapon;

public abstract class MonsterWeapon extends Weapon {

	public abstract boolean attackAreaHitTest(Animal animal);
}
