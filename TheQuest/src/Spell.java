import java.util.ArrayList;
import java.util.List;

public class Spell extends Item {
	
	private SpellType spellType;
	
	private int damage;
	private int manaCost;

	Spell(SpellType type, String name, int price, int level, int damage, int mana) {
		super(name, price, level);
		this.spellType = type;
		this.damage = damage;
		this.manaCost = mana;
	}
	
	public SpellType getSpellType() {
		return spellType;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getManaCost() {
		return manaCost;
	}

	public String toString() {
		return String.format("Spell Type: %s Spell Name: %s Price: %d Level Requirement: %d Damage Range: %d Required Mana: %d \n", 
				getSpellType(), getName(), getPrice(), getLevel(), getDamage(),getManaCost());
  }

	@Override
	public Item newInstance() {
		return new Spell(this.getSpellType(), this.getName(), this.getPrice(), this.getLevel(), this.getDamage(), this.getManaCost());
	}

	@Override
	public List<String> printItemInfo() {
		List<String> itemInfo = new ArrayList<>();
		
		itemInfo.add("Level: " + this.getLevel());
		itemInfo.add("Category: " + this.getSpellType());
		itemInfo.add("Damage: " + this.getDamage());
		itemInfo.add("Mana cost: " + this.getManaCost());
		return itemInfo;
	}
	
}
