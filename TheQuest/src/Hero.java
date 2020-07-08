import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Heroes class represents the three different types of heroes available in the game
 * 
 * @author btanaga
 *
 */
public abstract class Hero extends Character {
	public static final int MIN_BAG_SLOTS = 8;
	
	private int mana;
	private int maxMana;
	private int str;
	private int agi;
	private int dex;
	private int exp;
	private int money;
	private int numOfInvetorySlots;
	private final List<Item> inventory;
	private Weapon equipedWeapon;
	private final Map<Armor.ArmorSlotEnum, Armor> equipedArmor;

	/**
	 * Create a new level 1 character
	 * 
	 * @param name
	 * @param str
	 * @param agi
	 * @param dex
	 */
	public Hero(String name, int initialMana,
			int str, int agi, int dex,
			int money, int exp) {
		
		super(name, 1);
		this.mana = initialMana;
		this.maxMana = initialMana;
		this.str = str;
		this.agi = agi;
		this.dex = dex;
		this.money = money;
		this.exp = exp;
		this.numOfInvetorySlots = MIN_BAG_SLOTS;
		
		this.inventory = new ArrayList<>();
		this.equipedArmor = new HashMap<>();
	}

	/**
	 * Create a new character, with a given level
	 * 
	 * @param name
	 * @param level
	 * @param str
	 * @param agi
	 * @param dex
	 * @param exp
	 * @param money
	 */
	public Hero(String name, int level, int mana,
			int str, int agi, int dex, 
			int money, int exp, List<Item> inventoryItems) {
		
		super(name, level);
		this.mana = mana;
		this.str = str;
		this.agi = agi;
		this.dex = dex;
		this.money = money;
		this.exp = exp;
		
		
		this.mana = 100;
		for(int i = 1; i < level; i++) {
			this.mana += this.mana * 0.1;
		}
		
		this.inventory = new ArrayList<>();
		this.inventory.addAll(inventoryItems);
		this.equipedArmor = new HashMap<>();
	}
	
	public void printHeroInformation() {
		
		// return hero's current armor and weapon, and show statistics
		// Name, level, HP, mana, str, dex, agi, exp, NEED TO ADD: additional weapon damage/armor def, weapon and armor
		System.out.printf(
				"Name: %s\n"
				+ "Level: %d\n"
				+ "HP: %d\n"
				+ "Mana: %d\n"
				+ "Str: %d\n"
				+ "Dex: %d\n"
				+ "Agi: %d\n"
				+ "Experience: %d\n\n",
				this.getName(), this.getLevel(), this.getHp(), this.getMana(),
				this.getStr(), this.getDex(), this.getAgi(),
				this.getExp());
		Weapon weapon = this.getEquipedWeapon();
		System.out.printf("Equipped weapon: %s\n\n",
				((weapon != null)?weapon.getName():"(no weapon equipped)")
		);
		System.out.println("Equipped Armor:");
		for(Armor.ArmorSlotEnum itemSlot: Armor.ArmorSlotEnum.values()) {
			Armor armor = this.getEquipedArmor(itemSlot);
			System.out.printf("\t%s: %s\n", 
					itemSlot.toString(),
					((armor != null)?armor.getName():"(not equipped)")
			);
		}
		System.out.println();
		System.out.printf("%-20s  %5d\n", "Basic attack damage:", this.getAttackDmg());
		System.out.printf("%-20s  %5d\n", "Armor defense:", this.getArmorDef());
		System.out.printf("%-20s  %5.2f%%\n", "Dodge chance:", this.getDodgeChance());
		
		System.out.println();
		System.out.printf("Wallet: %d\n", this.getMoney());
	}

	public double getDodgeChance() {
		return this.getAgi()*0.02;
	}

	private int getArmorDef() {
		int totalDef = 0;
		for(Armor.ArmorSlotEnum itemSlot: Armor.ArmorSlotEnum.values()) {
			Armor armor = this.getEquipedArmor(itemSlot);
			if(armor != null) {
				totalDef += armor.getDefense();
			}
		}
		return totalDef;
	}

	private int getAttackDmg() {
		int totalDmg = (int)(this.getStr()*0.05);
		if(this.getEquipedWeapon() != null) {
			totalDmg = (int) ((this.getStr()+this.getEquipedWeapon().getDamage())*0.05);
		}
		return totalDmg;
	}

	private Armor getEquipedArmor(Armor.ArmorSlotEnum itemSlot) {
		return this.getEquipedArmor().get(itemSlot);
	}

	/**
	 * Will attempt to equip an item to an unused slot
	 * @param item
	 * @throws EquipException is an item is already equipped to this slot, or is not equip-able
	 */
	public void equipItem(Item item) throws EquipException {
		// first make sure the item is equip-able
		
		// if it is a weapon
		if( Weapon.isWeapon(item)) {
			// first check if a weapon is equipped
			if(isEquippedWeapon()) {
				throw new EquipException("please unequip weapon first");
			}
			
			this.equipedWeapon = (Weapon) item;
			
			this.calcStats();
		} else if (Armor.isArmor(item)) {
			// first check if armor is already equipped
			Armor armorItem = (Armor) item;
			Armor.ArmorSlotEnum equipSlot = armorItem.getEquipSlot();
			if(isEquippedArmor(equipSlot)) {
				throw new EquipException("please unequip armor first");
			}
			
			this.equipedArmor.put(equipSlot, armorItem);
			this.calcStats();
		} else {
			throw new EquipException("item not equipable");
		}
	}
	
	protected abstract void calcStats();

	public boolean isEquippedWeapon() {
		return this.equipedWeapon != null;
	}
	
	public boolean isEquippedArmor(Armor.ArmorSlotEnum equipSlot) {
		return this.equipedArmor.containsKey(equipSlot);
	}
	
	public Weapon unEquipWeapon() {
		Weapon temp = this.equipedWeapon;
		this.equipedWeapon = null;
		this.calcStats();
		
		return temp;
	}
	
	public Armor unEquipArmor(Armor.ArmorSlotEnum armorSlot) {
		Armor temp = this.equipedArmor.get(armorSlot);
		this.equipedArmor.remove(armorSlot);
		this.calcStats();
		
		return temp;
	}
	
	public boolean isInventoryFull() {
		return this.inventory.size() >= this.numOfInvetorySlots;
	}
	
	public void pushItemIntoInventory(Item item) {
		if(isInventoryFull()) {
			throw new RuntimeException("Inventory is full");
		}
		this.inventory.add(item);
	}
	
	/**
	 * This will remove a number of items of a given type from the inventory.
	 * 
	 * @param name the name of the item
	 * @return the items removed, null otherwise
	 */
	public Item popItemFromInventory(String name) {
		Item item = this.peekItemFromInventory(name);
		if(item != null) this.inventory.remove(item);
		return item;
	}
	
	public Item popItemFromInventory(int inventoryNum) {
		Item item = this.peekItemFromInventory(inventoryNum);
		if(item != null) this.inventory.remove(item);
		return item;
	}
	
	/**
	 * This will remove a number of items of a given type from the inventory.
	 * 
	 * @param name the name of the item
	 * @return the items removed, null otherwise
	 */
	public Item peekItemFromInventory(String name) {
		// find the item in the inventory and remove
		for(Item item: this.inventory) {
			if(item.getName().equals(name)) {
				return item;
			}
		}
		
		return null;
	}
	
	public Item peekItemFromInventory(int inventoryNum) {
		return this.inventory.get(inventoryNum);
	}
	
	public void printInventory() {
		this.printInventory(false, false);
	}
	
	public void printInventory(boolean onlyEquippableItems, boolean inCombat) {
		System.out.println("Inventory items:");
		if(this.getInventory().size() > 0) {
			for(int i = 0; i < this.getInventory().size(); i++) {
				Item item = this.getInventory().get(i);
				if(onlyEquippableItems 
						&& !(item instanceof Weapon)
						&& !(item instanceof Armor)
				) {
					System.out.println("No equippable items.\n");
					continue;
				}
				System.out.printf("\t%2d. %s\n", i, item.getName());
				List<String> attributeStrings = item.printItemInfo();
				for(String str: attributeStrings) {
					System.out.printf("\t\t%s \n", str);
				}
			}
		}
		else {
			if(inCombat) {
				System.out.println("\t No equippable items.\n");
			} else {
				System.out.println("\t Inventory is empty.");
			}
		}
	}
	
	/**
	 * This will remove a number of items of a given type from the inventory.
	 * 
	 * @param name the name of the item
	 * @param qty the quantity of items to remove
	 * @return a list containing the items removed, null otherwise
	 */
	public List<Item> popItemsFromInventory(String name, int qty) {
		// need to see if we have the correct number of these items
		List<Item> removedItems = new ArrayList<>();
		// TODO
		throw new UnsupportedOperationException();
		//return removedItems;
	}
	
	public int addExp(int amount) {
		this.exp += amount;
		return this.exp;
	}
	
	public int expTillNextLevel() {
		return this.getLevel()*10;
	}
	
	public void levelUp() {
		this.setLevel(this.getLevel()+1);
		
		// Reset Hp to maxHP, check if we need have to reset
		this.setHp(this.getMaxHP());
		
		// Reset mana to maxMana
		this.setMana(this.maxMana());
		
		// Reset exp to 0
		this.setExp(0);
		
		System.out.printf("Congratulations, %s has leveled up to level %d", this.getName(), this.getLevel());
	}

	protected abstract String getGameClass();

	/**
	 * @return the mana
	 */
	public int getMana() {
		return mana;
	}

	/**
	 * @param mana the mana to set
	 */
	public void setMana(int mana) {
		this.mana = Math.min(mana, maxMana);
	}
	
	public int maxMana() {
		return maxMana;
	}

	/**
	 * @return the str
	 */
	public int getStr() {
		return str;
	}

	/**
	 * @param str the str to set
	 */
	public void setStr(int str) {
		this.str = str;
	}

	/**
	 * @return the agi
	 */
	public int getAgi() {
		return agi;
	}

	/**
	 * @param agi the agi to set
	 */
	public void setAgi(int agi) {
		this.agi = agi;
	}

	/**
	 * @return the dex
	 */
	public int getDex() {
		return dex;
	}

	/**
	 * @param dex the dex to set
	 */
	public void setDex(int dex) {
		this.dex = dex;
	}

	/**
	 * @return the equipedWeapon
	 */
	public Weapon getEquipedWeapon() {
		return equipedWeapon;
	}

	/**
	 * @return the equipedArmor
	 */
	public Map<Armor.ArmorSlotEnum, Armor> getEquipedArmor() {
		return equipedArmor;
	}

	/**
	 * @return the exp
	 */
	public int getExp() {
		return exp;
	}

	/**
	 * @param exp the exp to set
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}

	/**
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @param money the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * @return the numOfInvetorySlots
	 */
	public int getNumOfInvetorySlots() {
		return numOfInvetorySlots;
	}

	/**
	 * @param numOfInvetorySlots the numOfInvetorySlots to set
	 */
	public void setNumOfInvetorySlots(int numOfInvetorySlots) {
		this.numOfInvetorySlots = numOfInvetorySlots;
	}

	/**
	 * @return the inventory
	 */
	public List<Item> getInventory() {
		return inventory;
	}

	protected abstract boolean canUse(Item item); 
}
