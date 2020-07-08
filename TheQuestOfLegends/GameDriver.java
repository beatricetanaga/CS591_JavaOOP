import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator; 
public class GameDriver {
	
	private static final int MIN_HEROES = 3; 
	private static final int MAX_HEROES = 3; 
	
	public void executeGame(Scanner scan) {
		displayWelcomeMessage(); 
		HeroCreationHelper helper = displayAvailableHeroes(); 
		
		Team heroTeam = new Team(); 
		Team monsterTeam = new Team(); 
		
		heroTeam.teamOfHeroes = pickHeroes(scan, 3, helper); 
		monsterTeam.teamOfMonsters = new ArrayList<>(); 
		
		ArrayList<Hero> heroes = heroTeam.teamOfHeroes; 
		ArrayList<Monster> monsters = monsterTeam.teamOfMonsters; 
		
		showHeroesInformation(heroes); 
		
		QuestMap map = createGameMap(heroes, monsters); 
		int rounds = 0; 
		
		// booleans to check if heroes or monsters have won
		boolean heroesHaveWon = false; // true when a hero reaches Monsters' nexus
		boolean monstersHaveWon = false; // true when a monster reaches Heroes' nexus
		
		while (!heroesHaveWon && !monstersHaveWon) {			
			// make moves for heroes 
			for (Hero h : heroes) {
				heroesHaveWon = h.makeMove(scan); 
				if (heroesHaveWon) {
					break; 
				}
				findFighters(h, heroes, monsters, scan, map); // check if there is a fight 
			}
			if (heroesHaveWon) {
				map.printQuestMap();
				break; 
			}
			
			// make moves for monsters 
			for (Monster m : monsters) {
				monstersHaveWon = m.makeMove(scan); 
				if (monstersHaveWon) {
					break; 
				}
			}
			if (monstersHaveWon) {
				map.printQuestMap();
				break; 
			}

			map.printQuestMap();
			rounds += 1; 
			if (rounds % 8 == 0) { // respawn new monsters every 8 rounds 
				MonsterCreationHelper monsterHelper = new MonsterCreationHelper(); 
				createMonsters(monsters.size(), monsterHelper, heroes, map, monsters); 
			}
		}
	}
	
	// method that finds all the heroes and monsters in a given row and initiates a fight between them
	public void findFighters(Hero hero, ArrayList<Hero> heroes, ArrayList<Monster> Monsters, Scanner scan, QuestMap map) {
		ArrayList<Hero> heroesForFightList = new ArrayList<>(); // arrayList to store heroes before we know how many there will be
		ArrayList<Monster> monstersForFightList = new ArrayList<>(); //arrayList to store monsters before we know how many there will be 
		
		int heroRow = hero.getRowPosition(); // get the row the hero is in 
		int heroCol = hero.getColPosition(); // get the column the hero is in 
		
		for (Monster m : Monsters) { // add all the monsters in the same row as the hero to the fight 
			if (inLane(heroCol, m.getColPosition()) && neighboringRow(heroRow, m.getRowPosition())) {
				monstersForFightList.add(m); 
			}
		}
		
		for (Hero h : heroes) { // add all the heroes in the same row as the hero to the fight 
			if (inLane(heroCol, h.getColPosition()) && neighboringRow(heroRow, h.getRowPosition())) {
				heroesForFightList.add(h); 
			}
		}
		
		Hero[] heroesForFight = new Hero[heroesForFightList.size()]; // array to hold the heroes 
		Monster[] monstersForFight = new Monster[monstersForFightList.size()]; // array to hold the monsters 
		
		int heroIndex = 0; 
		int monsterIndex = 0; 
		
		for (Hero h : heroesForFightList) { // add all the heroes from the arrayList to the array
			heroesForFight[heroIndex] = h; 
			heroIndex += 1; 
		}
		
		for (Monster m : monstersForFightList) { // add all the monsters from the arrayList to the array 
			monstersForFight[monsterIndex] = m; 
			monsterIndex += 1; 
		}
		
		
		if (monstersForFightList.size() > 0) { // initiate a fight if there ARE monsters in the area 
			Fight fight = new Fight(heroesForFight, monstersForFight); 
			fight.start(scan);
			
			Iterator<Monster> iterator = Monsters.iterator(); 
			while (iterator.hasNext()) { // remove all the dead monsters from the game after the fight 
				Monster m = iterator.next(); 
				if (m.hasDied()) {
					map.getGrid()[m.getRowPosition()][m.getColPosition()].exit(m);
					iterator.remove();
				}
			}
			map.printQuestMap();
		}
	}
	
	// helper method for findFighters
	// method that checks if a character is in a neighboring row of another character
	public boolean neighboringRow(int row, int rowPosition) {
		return (rowPosition == row || rowPosition == row + 1 || rowPosition == row - 1); 
	}
	
	// helper method for findFighters
	// method that checks if in a character is in the same lane as another character
	public boolean inLane(int col, int colPosition) {
		return (colPosition == col || colPosition == col + 1 || colPosition == col - 1); 
	}
 
	// method that creates the map and sets the heroes and monsters on the map 
	private QuestMap createGameMap(ArrayList<Hero> heroes, ArrayList<Monster> Monsters) {
		QuestMap map = new QuestMap(8, 8, 2); 
		// player.map = map; 
		map.createGrid();
		map.setStartingPosition(heroes);
		
		MonsterCreationHelper helper = new MonsterCreationHelper();
		createMonsters(0, helper, heroes, map, Monsters); 
		
		for (Hero h : heroes) {
			h.setMap(map);
		}
		
		map.printQuestMap();
		
		return map; 
	}

	// method that creates all the heroes available in the game and displays them
	private HeroCreationHelper displayAvailableHeroes() {
		HeroCreationHelper helper = new HeroCreationHelper();
		helper.createHeroes();
		helper.displayHeroes();
		return helper;
	}

	// method that prints a welcome message, a few lines explaining the game, and a line asking how many heroes 
	// the user wants to play with 
	private void displayWelcomeMessage() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("W E L C O M E  T O  T H E  Q U E S T  O F  L E G E N D S !");
		System.out.println("Get ready to roam the kingdom and fight the monsters!"); 
		System.out.println("The objective of the game is to get one of your three heroes into the Monsters' Nexus."); 
		System.out.println("Be sure to return to your Nexus to purchase items in the market to assist in your heroes' quest!"); 
		System.out.println("You may quit the game at any time once you are outside your Nexus and not in a fight."); 
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	// method for the hero-picking procedure that sets the heroes of the game to the ones the player selects
	private ArrayList<Hero> pickHeroes(Scanner scan, int numHeroes, HeroCreationHelper helper) {
		Hero[] heroes = new Hero[numHeroes];
		ArrayList<Hero> heroesForGame = new ArrayList<>(); 
		for (int i = 0; i < numHeroes; i++) {
			boolean alreadySelected = true;
			int heroID = -1; 
			while (alreadySelected) {
				heroID = getHeroID(i+1, scan, helper);
				// check if this ID is already selected
				alreadySelected = false; 
				for (int j = 0; j < i; j++) {
					if (heroID == heroes[j].getId()) {
						alreadySelected = true;
						System.out.println("You have already selected this hero. Please enter a different ID."); 
						break;
					} 
				}
			}

			for (Hero h : helper.getAllHeroes()) {
				if (heroID == h.getId()) {
					heroes[i] = h;
					String heroLabel = "H" + (i+1); 
					h.setHeroLabel(heroLabel);
					heroesForGame.add(h); 
				}
			}
		}
		return heroesForGame;
	}
	
	// method that creates the monsters (TAKEN FROM FIGHT CLASS'S START METHOD) 
	public void createMonsters(int numExistingMonsters, MonsterCreationHelper helper, ArrayList<Hero> heroes, QuestMap map, ArrayList<Monster> Monsters) {
		Monster[] tempMonsters = new Monster[3]; 
		// find the level of the hero with the maximum level
		int maxLevel = 0; 
		for (Hero h : heroes) {
			if (h.getLevel() > maxLevel) {
				maxLevel = h.getLevel();
			}
		}
		
		// find the correct set of monsters to fight with based on level
		Monster[] monsters; 
		if (maxLevel == 1) {
			monsters = MonsterCreationHelper.levelOneMonsters;
		} else if (maxLevel == 2) {
			monsters = MonsterCreationHelper.levelTwoMonsters;
		} else if (maxLevel == 3) {
			monsters = MonsterCreationHelper.levelThreeMonsters;
		} else if (maxLevel == 4) {
			monsters = MonsterCreationHelper.levelFourMonsters;
		} else {
			monsters = MonsterCreationHelper.levelFiveMonsters;
		}
		
		// create new tempMonsters from MonsterCreationHelper monsters template
		for (int i = 0; i < 3; i++) {
			Monster m = monsters[i]; 
			if (m instanceof Dragon) {
				tempMonsters[i] = new Dragon(m.getName(), m.getLevel(), m.getHp(), m.getDamage(),
						m.getDefense(), m.getDodge());
			} else if (m instanceof Exoskeleton) {
				tempMonsters[i] = new Exoskeleton(m.getName(), m.getLevel(), m.getHp(), m.getDamage(),
						m.getDefense(), m.getDodge());
			} else {
				tempMonsters[i] = new Spirit(m.getName(), m.getLevel(), m.getHp(), m.getDamage(),
						m.getDefense(), m.getDodge());
			}
			tempMonsters[i].setMonsterLabel("M" + (numExistingMonsters + 1));
			numExistingMonsters += 1; 
		}
		
		// set the starting position of each monster in their nexus
		int nexusCol = 0; 
		for (Monster m : tempMonsters) {
			map.getGrid()[0][nexusCol].setMonsterLabel(m.getMonsterLabel());
			m.setRowPosition(0);
			m.setColPosition(nexusCol);
			Monsters.add(m);
			nexusCol += 3; 
			m.setMap(map);
		}
	}

	// method that keeps obtaining the number of heroes the player wants to play with 
	// until they enter a valid number 
	public int getNumHeroes(Scanner scan) {
		int numHeroes = -1;
		do {
			numHeroes = UtilityHelper.getIntFromScanner(scan);
		} while (!validNumHeroes(numHeroes));
		return numHeroes;
	}

	// method that checks if the number of heroes selected is valid (must be between 1 and 3) 
	public boolean validNumHeroes(int numHeroes) {
		if (numHeroes < MIN_HEROES || numHeroes > MAX_HEROES) {
			System.out.print(
					"Please enter a number between " + MIN_HEROES + " and " + MAX_HEROES + ": ");
			return false;
		}
		return true;
	}

	// method that obtains the hero ID from the user based on which hero they want to play with 
	public int getHeroID(int i, Scanner scan, HeroCreationHelper helper) {
		int heroID = -1;
		do {
			System.out.print("ID of Hero #" + i + ": ");
			heroID = UtilityHelper.getIntFromScanner(scan);
		} while (!validHeroID(heroID, helper));
		return heroID;
	}

	// method that checks if the hero ID selected by the user is valid (must be between 1 and 15) 
	public boolean validHeroID(int heroID, HeroCreationHelper helper) {
		int totalNumHeroes = helper.getAllHeroes().size();
		if ((heroID < 1) || (heroID > totalNumHeroes)) {
			System.out.print("Please enter a number between 1 and " + totalNumHeroes + ": ");
			return false;
		}
		return true;
	}
	
	// method that prints information on the player's heroes 
	public void showHeroesInformation(ArrayList<Hero> heroes) {
		System.out.println("ID\t Name\t\tHealth Power\t Money \tMana\t Experience\t Strength\t Dexterity\tAgility\t");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------"); 
		for (Hero h : heroes) {
			System.out.print(h.getId() + "\t" + h.getName() + "\t" + (int)h.getHp() + "\t\t "  + (int)h.getMoney() + "\t" + (int)h.getMana() + "\t " + (int)h.getExperience() + "\t\t ");
			h.getSkill().printSkillSet(); 
		}
		System.out.println();
	}

}
