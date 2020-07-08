// Class that creates all of the monsters in the game, up to level 5
public class MonsterCreationHelper {
	// public Monster(String name, int level, double hp, double damage, double defense, double dodge)
	
	public static Monster kans = new Dragon("Kans          ", 1, 100, 250, 200, 5);
	public static Monster shakuni = new Dragon("Shakuni       ", 2, 200, 350, 400, 8);
	public static Monster duryodhan = new Dragon("Duryodhan     ", 3, 300, 450, 400, 5);
	public static Monster ravan = new Dragon("Ravan         ", 4, 400, 550, 415, 8);
	public static Monster narkasur = new Dragon("Narkasur      ", 5, 500, 650, 420, 10);

	public static Monster khar = new Exoskeleton("Khar          ", 1, 100, 150, 350, 10);
	public static Monster dushan = new Exoskeleton("Dushan        ", 2, 200, 320, 500, 12);
	public static Monster meghnad = new Exoskeleton("Meghnad       ", 3, 300, 440, 450, 15);
	public static Monster kumbhakaran = new Exoskeleton("Kumbhakaran   ", 4, 400, 615, 600, 20);
	public static Monster hiranyakashyap = new Exoskeleton("Hiranyakashyap", 5, 520, 750, 650, 20);

	public static Monster sursa = new Spirit("Sursa         ", 1, 100, 225, 250, 25);
	public static Monster putna = new Spirit("Putna         ", 2, 200, 230, 300, 30);
	public static Monster bhasmasur = new Spirit("Bhasmasur     ", 3, 300, 320, 425, 28);
	public static Monster raktabij = new Spirit("Raktabij      ", 4, 400, 425, 450, 32);
	public static Monster mahishasur = new Spirit("Mahishasur    ", 5, 500, 530, 475, 34);

	// arrays that store monsters of the same level together, used based on level of heroes
	public static Monster[] levelOneMonsters = { kans, khar, sursa };
	public static Monster[] levelTwoMonsters = { shakuni, dushan, putna };
	public static Monster[] levelThreeMonsters = { duryodhan, meghnad, bhasmasur };
	public static Monster[] levelFourMonsters = { ravan, kumbhakaran, raktabij };
	public static Monster[] levelFiveMonsters = { narkasur, hiranyakashyap, mahishasur };

}

