package Item.Weapon;

import java.awt.Image;

import Item.Weapon.CharacterWeapon.CharacterWeapon;

public abstract class Effect extends CharacterWeapon {

	public abstract Image getIm();

	public abstract int getImageX();

	public abstract int getImageY();
}
