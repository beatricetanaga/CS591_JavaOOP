import java.util.*; 

public class Market {
	// Market contains all of these collections of items 
	public ArrayList<Weapon> weapons = new ArrayList<>();  
	public ArrayList<Armor> armors = new ArrayList<>(); 
	public ArrayList<Potion> potions = new ArrayList<>(); 
	public ArrayList<IceSpell> iceSpells = new ArrayList<>(); 
	public ArrayList<FireSpell> fireSpells = new ArrayList<>(); 
	public ArrayList<LightningSpell> lightningSpells = new ArrayList<>();
	
	public HashSet<Item> allItems = new HashSet<>(); 
	public int numItems; 
	
	// constructor of market adds all the items to the market 
	public Market() {
		addWeapons(); 
		addArmors(); 
		addPotions(); 
		addIceSpells();
		addFireSpells(); 
		addLightningSpells(); 
		
		this.numItems = allItems.size();
	}
	
	public void addWeapons() {
		weapons.add(ItemConstants.sword); 
		weapons.add(ItemConstants.bow); 
		weapons.add(ItemConstants.scythe); 
		weapons.add(ItemConstants.axe); 
		weapons.add(ItemConstants.shield); 
		weapons.add(ItemConstants.tSword); 
		weapons.add(ItemConstants.dagger); 
		
		for (Weapon w : weapons) {
			allItems.add(w); 
		}
	}
	
	public void addArmors() {
		armors.add(ItemConstants.platinumShield); 
		armors.add(ItemConstants.breastplate); 
		armors.add(ItemConstants.fullBodyArmor); 
		armors.add(ItemConstants.wizardShield); 
		armors.add(ItemConstants.speedBoots); 
		
		for (Armor a : armors) {
			allItems.add(a); 
		}
	}
	
	public void addPotions() {
		potions.add(ItemConstants.healingPotion); 
		potions.add(ItemConstants.strengthPotion); 
		potions.add(ItemConstants.magicPotion); 
		potions.add(ItemConstants.luckElixir); 
		potions.add(ItemConstants.mermaidTears);
		potions.add(ItemConstants.ambrosia); 
		
		for (Potion p : potions) {
			allItems.add(p); 
		}
	}
	
	public void addIceSpells() {
		iceSpells.add(ItemConstants.snowCanon); 
		iceSpells.add(ItemConstants.iceBlade); 
		iceSpells.add(ItemConstants.frostBlizzard); 
		iceSpells.add(ItemConstants.arcticStorm); 
		
		for (IceSpell s : iceSpells) {
			allItems.add(s); 
		}
	}
	
	public void addFireSpells() {
		fireSpells.add(ItemConstants.flameTornado); 
		fireSpells.add(ItemConstants.breathOfFire); 
		fireSpells.add(ItemConstants.heatWave); 
		fireSpells.add(ItemConstants.lavaCommet); 
		
		for (FireSpell s : fireSpells) {
			allItems.add(s); 
		}
	}
	
	public void addLightningSpells() {
		lightningSpells.add(ItemConstants.lightDagger); 
		lightningSpells.add(ItemConstants.thunderBlast); 
		lightningSpells.add(ItemConstants.electricArrow); 
		lightningSpells.add(ItemConstants.sparkNeedle);
		
		for (LightningSpell s : lightningSpells) {
			allItems.add(s); 
		}
	}
	
	// method to display all of the items available in the market 
	public void displayMarket() {
		System.out.println("W E L C O M E  T O  T H E  M A R K E T ! "); 
		
		System.out.println("Available Weapons: "); 
		System.out.println("ID\t Name\t\t Cost\t\t Unlock Level\t Damage");
		System.out.println("--------------------------------------------------------------------------------"); 
		for (Weapon w : weapons) {
			System.out.println(w.getId() + "\t" + w.getName() + "\t\t " + w.getPrice() + "\t\t " + w.getMinHeroLevel() + "\t\t " + w.getDamage()); 
		}
		System.out.println(); 
		
		System.out.println("Available Armors: "); 
		System.out.println("ID\t Name\t\t Cost\t\t Unlock Level\t  Defense");
		System.out.println("--------------------------------------------------------------------------------");  
		for (Armor a : armors) {
			System.out.println(a.getId() + "\t" + a.getName() + "\t " + a.getPrice() + "\t\t " + a.getMinHeroLevel() + "\t\t  " + a.getDefense());  
		}
		System.out.println(); 
		
		System.out.println("Available Potions: "); 
		System.out.println("ID\t Name\t\t Cost\t\t Unlock Level\t Skill Increase Rate");
		System.out.println("--------------------------------------------------------------------------------");  
		for (Potion p : potions) {
			System.out.println(p.getId() + "\t" + p.getName() + "\t " + p.getPrice() + "\t\t " + p.getMinHeroLevel() + "\t\t " + p.getIncrementStat()); 
		}
		System.out.println(); 
		
		System.out.println("Available Ice Spells: "); 
		System.out.println("ID\t Name\t\t Cost\t\t Unlock Level\t Mana Cost\tDamage\t\t ");
		System.out.println("--------------------------------------------------------------------------------");  
		for (IceSpell s : iceSpells) {
			System.out.println(s.getId() + "\t" + s.getName() + "\t " + s.getPrice() + "\t\t " + s.getMinHeroLevel() + "\t\t  " + s.getManaDeduction() + "\t\t" + s.getDamage());  
		}
		System.out.println(); 
		
		System.out.println("Available Fire Spells: "); 
		System.out.println("ID\t Name\t\t Cost\t\t Unlock Level\t Mana Cost\tDamage\t\t ");
		System.out.println("--------------------------------------------------------------------------------"); 
		for (FireSpell s : fireSpells) {
			System.out.println(s.getId() + "\t" + s.getName() + "\t " + s.getPrice() + "\t\t " + s.getMinHeroLevel() + "\t\t  " + s.getManaDeduction() + "\t\t" + s.getDamage());  
		}
		System.out.println(); 
		
		System.out.println("Available Lightning Spells: "); 
		System.out.println("ID\t Name\t\t Cost\t\t Unlock Level\t Mana Cost\tDamage\t\t ");
		System.out.println("--------------------------------------------------------------------------------"); 
		for (LightningSpell s : lightningSpells) {
			System.out.println(s.getId() + "\t" + s.getName() + "\t " + s.getPrice() + "\t\t " + s.getMinHeroLevel() + "\t\t  " + s.getManaDeduction() + "\t\t" + s.getDamage());  
		}
		System.out.println(); 
	}
	
}
