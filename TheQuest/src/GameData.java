import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameData {
	
	private static Warrior GaerdalIronhand = new Warrior("Gaerdal Ironhand", 100, 700, 500, 600, 1354, 7);
	private static Warrior SehanineMonnbow = new Warrior("Sehanine Monnbow", 600, 700, 800, 500, 2500, 8);
	private static Warrior MuammanDuathall = new Warrior("Muamman Duathall", 300, 900, 500, 750, 2546, 6);
	private static Warrior FlandalSteelskin = new Warrior("Flandal Steelskin", 200, 750, 650, 700, 2500, 7);

	private static Sorcerer GarlGlittergold = new Sorcerer("Garl Glittergold", 700, 550, 600, 500, 2500, 7);
	private static Sorcerer RillifaneRallathil = new Sorcerer("Rillifane Rallathil", 1300, 750, 450, 500, 2500, 9);
	private static Sorcerer SegojanEarthcaller = new Sorcerer("Segojan Earthcaller", 900, 800, 500, 650, 2500, 5);
	private static Sorcerer SkoraeusStonebones = new Sorcerer("Segojan Earthcaller", 800, 850, 600, 450, 2500, 6);
	
	private static Paladin SolonorThelandira = new Paladin("Solonor Thelandir", 300, 750, 650, 700, 2500, 7);
	private static Paladin SehanineMoonbow = new Paladin("Sehanine Moonbow", 300, 750, 700, 700, 2500, 7);
	private static Paladin SkoraeusStonebones2 = new Paladin("Skoraeus Stonebones", 250, 650, 600, 350, 2500, 4);
	private static Paladin GarlGlittergold2 = new Paladin("Garl Glittergold", 100, 600, 500, 400, 2500, 5);
	
	public static List<Hero> getHeroPreloadList() {
		List<Hero> heroList = new ArrayList<>();

		heroList.add(GameData.GaerdalIronhand);
		heroList.add(GameData.SehanineMonnbow);
		heroList.add(GameData.MuammanDuathall);
		heroList.add(GameData.FlandalSteelskin);
		
		heroList.add(GameData.GarlGlittergold);
		heroList.add(GameData.RillifaneRallathil);
		heroList.add(GameData.SegojanEarthcaller);
		heroList.add(GameData.SkoraeusStonebones);
		
		heroList.add(GameData.SolonorThelandira);
		heroList.add(GameData.SehanineMoonbow);
		heroList.add(GameData.SkoraeusStonebones2);
		heroList.add(GameData.GarlGlittergold2);

		return heroList;
	}
	
	private static String[] allClasses = null;
	private static String[] warriorPaladin = {Warrior.gameClassName(), Paladin.gameClassName()};
	private static String[] paladinSorcerer = {Paladin.gameClassName(), Sorcerer.gameClassName()};
	
	public static Map<String, Item> getGameItemPreloadMap() {
		Map<String, Item> gameItems = new HashMap<>();
		
		// Armory items
		gameItems.put("Platinum Shield", new Armor("Platinum Shield", 150, 1, 200, Armor.ArmorSlotEnum.SHIELD, warriorPaladin));
		gameItems.put("Full Body Armor", new Armor("Full Body Armor", 1000, 8, 1100, Armor.ArmorSlotEnum.BODY, allClasses));
		gameItems.put("Breastplate", new Armor("Breastplate", 350, 3, 600, Armor.ArmorSlotEnum.BODY, warriorPaladin));
		gameItems.put("Wizard Shield", new Armor("Wizard Shield", 1200, 10, 1500,  Armor.ArmorSlotEnum.SHIELD, paladinSorcerer));
		gameItems.put("Speed Boots", new Armor("Speed Boots", 550, 4, 600, Armor.ArmorSlotEnum.BOOTS, allClasses));
		
		// Weaponry items
		gameItems.put("Sword", new Weapon("Sword", 500, 1, 800, 1));
		gameItems.put("Bow", new Weapon("Bow", 300, 2, 500, 2));
		gameItems.put("Scythe", new Weapon("Scythe", 1000, 6, 1100, 2));
		gameItems.put("Axe", new Weapon("Axe", 550, 5, 850, 1));
		gameItems.put("Shield", new Weapon("Shield", 400, 1, 100, 1));
		gameItems.put("TSwords", new Weapon("TSwords", 1400, 8, 1600, 2));
		gameItems.put("Dagger", new Weapon("Dagger", 200, 1, 250, 1));
		
		// Spells
		gameItems.put("Flame Tornado", new Spell(SpellType.FIRE, "Flame Tornado", 700, 4, 850, 300));
		gameItems.put("Breakth Of Fire", new Spell(SpellType.FIRE, "Breath Of Fire", 350, 1, 450, 100));
		gameItems.put("Heat Wave", new Spell(SpellType.FIRE, "Heat Wave", 450, 2, 600, 150));
		gameItems.put("Lava Comment", new Spell(SpellType.FIRE, "Lava Comment", 800, 7, 1000, 550));
		
		gameItems.put("Snow Canon", new Spell(SpellType.ICE, "Snow Canon", 500, 2, 650, 250));
		gameItems.put("Ice Blade", new Spell(SpellType.ICE, "Ice Blade", 250, 1, 450, 100));
		gameItems.put("Frost Blizzard", new Spell(SpellType.ICE, "Frost Blizzard", 750, 5, 850, 350));
		gameItems.put("Arctic Storm", new Spell(SpellType.ICE, "Arctic Storm", 700, 6, 800, 300));
		
		gameItems.put("Lightning Dagger", new Spell(SpellType.LIGHTNING, "Lightning Dagger", 400, 1, 500, 150));
		gameItems.put("Thunder Blast", new Spell(SpellType.LIGHTNING, "Thunder Blast", 750, 4, 950, 400));
		gameItems.put("Electric Arrows", new Spell(SpellType.LIGHTNING, "Electric Arrows", 550, 5, 650, 200));
		gameItems.put("Spark Needles", new Spell(SpellType.LIGHTNING, "Spark Needles", 500, 2, 600, 200));
		
		// Potions
		gameItems.put(PotionType.HP.toString(), new Potion(PotionType.HP.toString(), 250, 1, PotionType.HP, 100));
		gameItems.put(PotionType.STR.toString(), new Potion(PotionType.STR.toString(), 200, 1, PotionType.STR, 75));
		gameItems.put(PotionType.MANA.toString(), new Potion(PotionType.MANA.toString(), 350, 2, PotionType.MANA, 100));
		gameItems.put(PotionType.DEX.toString(), new Potion(PotionType.DEX.toString(), 500, 4, PotionType.DEX, 65));
		gameItems.put(PotionType.AGI.toString(), new Potion(PotionType.AGI.toString(), 850, 5, PotionType.AGI, 100));
		gameItems.put(PotionType.AMBROSIA.toString(), new Potion(PotionType.AMBROSIA.toString(), 1000, 8, PotionType.AMBROSIA, 150));
		
		return gameItems;
	}
	
	public static List<Monster> getMonsterPreloadList() {
		List<Monster> monsterList = new ArrayList<>();
		
		// Dragons
		monsterList.add(new Dragon("Natsunomeryu", 1, 100, 200, 10));
		monsterList.add(new Dragon("Chrysophylax", 2, 200, 500, 20));
		monsterList.add(new Dragon("Desghidorrah", 3, 300, 400, 35));
		monsterList.add(new Dragon("Bunsen Burner", 4, 400, 500, 45));
		monsterList.add(new Dragon("Kas-Ethelinh", 5, 600, 500, 60));
		monsterList.add(new Dragon("Phaarthurnax", 6, 600, 700, 60));
		monsterList.add(new Dragon("The Scaleless", 7, 700, 600, 75));
		monsterList.add(new Dragon("The Weatherbe", 8, 800, 900, 80));
		monsterList.add(new Dragon("D-Maleficent", 9, 900, 950, 85));
		monsterList.add(new Dragon("Alexstraszan", 10, 1000, 9000, 55));
		
		// Exoskeletons
		monsterList.add(new Exoskeleton("BigBad-Wolf", 1, 150, 250, 15));
		monsterList.add(new Exoskeleton("Wicked Witch", 2, 250, 350, 25));
		monsterList.add(new Exoskeleton("Brandobaris", 3, 350, 450, 30));
		monsterList.add(new Exoskeleton("Aasterinian", 4, 400, 500, 45));
		monsterList.add(new Exoskeleton("St-Shargaas", 5, 550, 650, 55));
		monsterList.add(new Exoskeleton("Chronepsish", 6, 650, 750, 60));
		monsterList.add(new Exoskeleton("Cyrrollalee", 7, 700, 800, 75));
		monsterList.add(new Exoskeleton("Kiaransalee", 8, 850, 950, 85));
		monsterList.add(new Exoskeleton("St-Yeenoghu", 9, 950, 850, 90));
		monsterList.add(new Exoskeleton("Merrshaullk", 10, 1000, 900, 55));
		
		// Spirits
		monsterList.add(new Spirit("Aim-Haborym", 1, 450, 350, 35));
		monsterList.add(new Spirit("Andrealphus", 2, 600, 500, 40));
		monsterList.add(new Spirit("Andromalius", 3, 550, 450, 25));
		monsterList.add(new Spirit("Chiang-shih", 4, 700, 600, 40));
		monsterList.add(new Spirit("Fallen Angel", 5, 800, 700, 50));
		monsterList.add(new Spirit("Ereshkigall", 6, 950, 450, 35));
		monsterList.add(new Spirit("Melchiresas", 7, 350, 150, 75));
		monsterList.add(new Spirit("Jormunngand", 8, 600, 900, 20));
		monsterList.add(new Spirit("Rakkshasass", 9, 550, 600, 35));
		monsterList.add(new Spirit("Taltecuhtli", 10, 300, 200, 50));
		
		return monsterList;
	}

}
