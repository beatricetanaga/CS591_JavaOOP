// Class that creates static variables of all of the items in the game available in the market 
public class ItemConstants {
	public static Weapon sword = new Weapon(1, "Sword", 500, 1, 800, 1);
	public static Weapon bow = new Weapon(2, "Bow", 300, 2, 500, 2); 
	public static Weapon scythe = new Weapon(3, "Scythe", 1000, 5, 900, 2); 
	public static Weapon axe = new Weapon(4, "Axe", 550, 4, 750, 1);
	public static Weapon shield = new Weapon(5, "Shield", 400, 3, 600, 1);
	public static Weapon tSword = new Weapon (6, "T Sword", 1400, 5, 1600, 2); 
	public static Weapon dagger = new Weapon(7, "Dagger", 200, 1, 350, 1); 
	
	public static Armor platinumShield = new Armor(8, "Platinum Shield", 150, 1, 300); 
	public static Armor breastplate = new Armor(9, "Breastplate", 350, 2, 350); 
	public static Armor fullBodyArmor = new Armor(10, "Full Body Armor", 1000, 4, 390); 
	public static Armor wizardShield = new Armor(11, "Wizard Shield", 1200, 5, 400); 
	public static Armor speedBoots = new Armor(12, "Speed Boots", 550, 3, 375); 
	
	public static Potion healingPotion = new Potion(13, "Healing Potion", 250, 1, 145); 
	public static Potion strengthPotion = new Potion(14, "Strength Potion", 200, 1, 120); 
	public static Potion magicPotion = new Potion(15, "Magic Potion", 350, 2, 150); 
	public static Potion luckElixir = new Potion(16, "Luck Elixir", 500, 3, 150); 
	public static Potion mermaidTears = new Potion(17, "Mermaid Tears", 850, 4, 180); 
	public static Potion ambrosia = new Potion(18, "Ambrosia", 1000, 5, 200); 
	
	//public IceSpell (int id, String name, int price, int minHeroLevel, int manaDeduction, int damage)
	public static IceSpell snowCanon = new IceSpell(19, "Snow Canon", 500, 2, 270, 50); 
	public static IceSpell iceBlade = new IceSpell(20, "Ice Blade", 250, 1, 250, 40); 
	public static IceSpell frostBlizzard = new IceSpell(21, "Frost Blizzard", 750, 4, 350, 60); 
	public static IceSpell arcticStorm = new IceSpell(22, "Arctic Storm", 700, 3, 300, 80); 

	//public FireSpell (int id, String name, int price, int minHeroLevel, int manaDeduction, int damage)
	public static FireSpell flameTornado = new FireSpell(23, "Flame Tornado", 700, 4, 300, 40); 
	public static FireSpell breathOfFire = new FireSpell(24, "Breath of Fire", 350, 1, 150, 20); 
	public static FireSpell heatWave = new FireSpell(25, "Heat Wave", 450, 2, 200, 60); 
	public static FireSpell lavaCommet = new FireSpell(26, "Lava Commet", 800, 5, 400, 90); 

	//public LightningSpell (int id, String name, int price, int minHeroLevel, int manaDeduction, int damage)
	public static LightningSpell lightDagger = new LightningSpell(27, "Light Dagger", 400, 1, 300, 70); 
	public static LightningSpell thunderBlast = new LightningSpell(28, "Thunder Blast", 750, 4, 240, 60); 
	public static LightningSpell electricArrow = new LightningSpell(29, "Electric Arrow", 550, 5, 200, 50); 
	public static LightningSpell sparkNeedle = new LightningSpell(30, "Spark Needle", 500, 2, 230, 30); 

	
}
