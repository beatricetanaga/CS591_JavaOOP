import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Armor extends Item {
	
	public enum ArmorSlotEnum {
		BODY, SHIELD, BOOTS
	}

	private int defense;
	private ArmorSlotEnum equipSlot;
	private List<String> classRestriction;
	
	public static boolean isArmor(Item testItem) {
		return testItem instanceof Armor;
	}
	
	Armor(String name, int price, int level, int def, ArmorSlotEnum equipSlot) {
		super(name, price, level);
		this.defense = def;
		this.classRestriction = null;
		this.equipSlot = equipSlot;
		validate();
	}
	
	Armor(String name, int price, int level, int def,
			ArmorSlotEnum equipSlot,  List<String> classRestriction)
	{
		super(name, price, level);
		this.defense = def;
		this.equipSlot = equipSlot;
		if(classRestriction != null && classRestriction.size() > 0) {
			this.classRestriction = new ArrayList<>();
			this.classRestriction.addAll(classRestriction);
		} else {
			this.classRestriction = null;
		}
		validate();
	}
	
	Armor(String name, int price, int level, int def,
			ArmorSlotEnum equipSlot,  String[] classRestriction)
	{
		super(name, price, level);
		this.defense = def;
		this.equipSlot = equipSlot;
		if(classRestriction != null && classRestriction.length > 0) {
			this.classRestriction = new ArrayList<>();
			Collections.addAll(this.classRestriction, classRestriction);
		} else {
			this.classRestriction = null;
		}
		validate();
	}
	
	private void validate() {
		if(this.getEquipSlot() == null) {
			throw new RuntimeException("Equipslot not set.");
		}
	}

	public int getDefense() {
		return defense;
	}

	/**
	 * @return the equipSlot
	 */
	public ArmorSlotEnum getEquipSlot() {
		return equipSlot;
	}

	/**
	 * @return the classRestriction
	 */
	public List<String> getClassRestriction() {
		return classRestriction;
	}

	public String toString() {
		return String.format("Armor Name: %s Price: %d Level Requirement: %d Defense : %d \n",
                getName(), getPrice(), getLevel(), getDefense());
  }

	@Override
	public Item newInstance() {
		return new Armor(this.getName(), this.getPrice(), this.getLevel(), this.getDefense(), this.equipSlot, this.classRestriction);
	}

	@Override
	public List<String> printItemInfo() {
		List<String> itemInfo = new ArrayList<>();
		
		itemInfo.add("Level: " + this.getLevel());
		itemInfo.add("Defense: " + this.getDefense());
		return itemInfo;
	}
	
}
