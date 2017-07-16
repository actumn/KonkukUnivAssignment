package Item.Weapon;

import DisplayObject.Animal;
import DisplayObject.DisplayComponent;
import DisplayObject.Monster;
import Item.Item;

public abstract class Weapon extends Item implements Attackable {
	
	public static final int extraArray = 20;
	
	protected static int[][] attackAreaArray = new int[20 + 2 + extraArray][20 + 2 + extraArray];
	protected static Animal[][][] attackAnimalArray = new Animal[20 + 2 + extraArray][20 + 2 + extraArray][1];
	
	protected int attackAreaCenterX = extraArray / 2;
	protected int attackAreaCenterY = extraArray / 2;
   
	protected int physicalAttackPower;
	protected int weaponSpeed;
	protected int attackDistance;
	
	private static boolean a = true;
	
	public abstract void attackAreaPlus(Animal animal, int positionNum, int hitAreaCenterX, int hitAreaCenterY);
	public abstract void attackAreaMinus(int positionNum, int hitAreaCenterX, int hitAreaCenterY);
	
	
	public Weapon() {
		if (a == true) {
			areaLimit();
			a = false;
		}
	}

	public void attackAreaDistance(int positionNum, int hitAreaCenterX, int hitAreaCenterY) {
		attackAreaCenterX = hitAreaCenterX;
		attackAreaCenterY = hitAreaCenterY;
		
		switch (positionNum) {
		case DOWN : attackAreaCenterY += attackDistance; break;
		case LEFT : attackAreaCenterX -= attackDistance; break;
		case RIGHT : attackAreaCenterX += attackDistance; break;
		case UP : attackAreaCenterY -= attackDistance; break;
		}
		
	}
	
	public void areaLimit() {
		// 공간제한
		for (int i = extraArray / 2; i < attackAreaArray.length - (extraArray / 2); i++) {
			attackAreaArray[i][extraArray / 2] += ATTACK_VALUE;
			attackAreaArray[i][(attackAreaArray[i].length - 1) - extraArray / 2] += ATTACK_VALUE;
		}
		
		for (int i = 1 + (extraArray / 2); i < (attackAreaArray.length - 1) - (extraArray / 2); i++) {
			attackAreaArray[extraArray / 2][i] += ATTACK_VALUE;
			attackAreaArray[(attackAreaArray[i].length - 1) - (extraArray / 2)][i] += ATTACK_VALUE;
		}
		
	}
	
	public static void test() {

		for (int i = 0; i < attackAreaArray.length; i++) {
			for (int j = 0; j < attackAreaArray[i].length; j++) {
				if (attackAreaArray[j][i] < 10) {
					System.out.print("0" + attackAreaArray[j][i] + " ");
				} else {
					System.out.print(attackAreaArray[j][i] + " ");
				}
				
			}
			System.out.println();
		}
	}
	
	public void death(Animal animal, DisplayComponent dc) {
		
		if (animal.getHp() <= 0) {
			dc.getMonster().remove(animal);
			animal.death();
			dc.getCh().setExp(animal.getExp());
			dc.repaint();
		}
		
	}
	
	public int getWeaponSpeed() {
		return weaponSpeed;
	}

	public int getPhysicalAttackPower() {
		return physicalAttackPower;
	}
	
	public int getAttackDistance() {
		return attackDistance;
	}

	public int getDecreaseMp(int skillNum) {
		return 0;
	}
}
