import java.util.ArrayList;
import java.util.List;

public class Weapon extends Item{
	
	private int damage;
	private int numHands;
	
	public static boolean isWeapon(Item testItem) {
		return testItem instanceof Weapon;
	}

	Weapon(String name, int price, int level, int dmg, int hands) {
		super(name, price, level);
		this.damage = dmg;
		this.numHands = hands;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getNumHands() {
		return numHands;
	}
	
	public String toString() {
		return String.format("Armor Name: %s Price: %d Level Requirement: %d Damage : %d , Weapon Type: %d \n",
                getName(), getPrice(), getLevel(), getDamage(), getNumHands());
	}

	@Override
	public Item newInstance() {
		return new Weapon(this.getName(), this.getPrice(), this.getLevel(), this.getDamage(), this.getNumHands());
	}

	@Override
	public List<String> printItemInfo() {
		List<String> itemInfo = new ArrayList<>();
		
		itemInfo.add("Level: " + this.getLevel());
		itemInfo.add("Damage: " + this.getDamage());
		itemInfo.add("Type: " + this.getNumHands() + "-handed");
		return itemInfo;
	}
}
