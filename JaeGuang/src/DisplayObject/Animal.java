package DisplayObject;

import java.awt.Image;

public abstract class Animal extends DisplayObject {

	// 출력 필드
	protected static final int MOVE_ARRAY_NUM = 0;
	protected static final int ATTACK_ARRAY_NUM = 1;
	protected static final int POISTION_NUM = 4;
	protected String[] fileDirectory;
	protected String[] skillDirectory;
	protected String defaultDirectory;
	protected Image[][][][] im = new Image[5][4][4][100];
	protected int imageX;
	protected int imageY;
	protected int arrayNum;
	protected int skillNum;
	protected int positionNum;
	protected int motionNum;
	protected int count;

	// 능력치 필드
	protected String name;
	protected int lv;
    protected int hp;
    protected int mp;
    protected int physicalAttackPower;
    protected int magicalAttackPower;
    protected int physicalDefensePower;
    protected int magicalDefensePower;
    protected int accuracyRate;
    protected int evasionRate;
    protected int exp;
    protected int attackSpeed;
    protected int attackRange;
    
    protected int gold;

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp += hp;
	}
	
	public int getMp() {
		return mp;
	}
	
	public void setMp(int mp){
		this.mp += mp;
	}
	public void setPositionNum(int positionNum) {
		// TODO Auto-generated method stub
		
	}
	
	public int getExp() {
		return exp;
	}

	
	public int getLv() {
		return lv;
	}
	
	public int getPhysicalAttackPower() {
		return physicalAttackPower;
	}

	public void setPhysicalAttackPower(int physicalAttackPower) {
		this.physicalAttackPower = physicalAttackPower;
	}

	public int getMagicalAttackPower() {
		return magicalAttackPower;
	}

	public void setMagicalAttackPower(int magicalAttackPower) {
		this.magicalAttackPower = magicalAttackPower;
	}

	public int getPhysicalDefensePower() {
		return physicalDefensePower;
	}

	public void setPhysicalDefensePower(int physicalDefensePower) {
		this.physicalDefensePower = physicalDefensePower;
	}

	public int getMagicalDefensePower() {
		return magicalDefensePower;
	}

	public void setMagicalDefensePower(int magicalDefensePower) {
		this.magicalDefensePower = magicalDefensePower;
	}

	public abstract void death();
	
}
