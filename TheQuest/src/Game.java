
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


public class Game {

	static class _Position {
		int posX;
		int posY;
		
		public _Position(int x, int y) {
			this.posX = x;
			this.posY = y;
		}
	}

	public static final int GAME_MAP_WIDTH  = 9;
	public static final int GAME_MAP_HEIGHT = 9;
	public static final int MAX_HEROES = 3;

	private List<Hero> heroPreloadList = GameData.getHeroPreloadList();
	private Map<String, Item> gameItemPreloadMap = GameData.getGameItemPreloadMap();
	private List<Monster> monsterPreloadList = GameData.getMonsterPreloadList();
	
	// Game state variables
	private boolean gameInPlay;
	private List<Hero> heroList;
	private _Position pos = new _Position(0, 0);
	private GameMap gameMap;
	private List<Item> marketItemList = null;
	//private boolean newSpawn = false;
	private boolean inCombat = false;
	private List<Monster> monsterList = null;
	//private Hero currentHero = null;
	//private Monster currentMonster = null;
	private int numOfRounds = 0;
	private boolean spellCastedFlag = false;
	private boolean potionUsedFlag = false;
	private boolean equippedGearFlag = false;
	
	private Random rand = new Random();
	
	public Game() {
		
	}
	
	public void run() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome Player! ");
		
		this.heroList = this.getHeroList(sc);
//		this.heroList = this.getDebugHeroList();
		
		// Build the game map
		this.gameMap = new GameMap(GAME_MAP_WIDTH, GAME_MAP_HEIGHT);
		
		// start game loop
		this.gameInPlay = true;
		
		// set the starting positions
		setStartingPosition();
		
		// check if this is a market square and if so generate inventory
		if(this.isMarketCell()) {
			this.marketItemList = generateStoreInventory(this.pos);
		}
		
		while(gameInPlay) {
			this.numOfRounds++;
			
			// Print a status message before each move
			printOutOfCombatStatusMessage();
			
			// Prompt the user for a command
			System.out.print("command: ");
			String cmdStr = sc.nextLine();
			
			Command cmd = Command.parseInput(cmdStr);
			
			if(cmd == null) {
				System.out.println("Sorry I didn't get that, please try again.");
				continue;
			}
			
			// process the command
			switch(cmd.getType()) {
				case MOVE:
					move(sc, cmd.getArgs());
					break;
				case EQUIP:
					equipGear(sc);
					break;
				case DISPLAY:
					displayCharacter(sc);
				case HELP:
					showHelpMenu();
					break;
				case INVENTORY:
					showInventory(sc);
					break;
				case QUIT:
					gameInPlay = false;
					break;
				case USE:
					usePotion(sc, null);
					break;
				case BUY:
					buyItems(sc);
					break;
				case SELL:
					sellItems(sc);
					break;
				default:
					break;
			}
		}
		
		// Game is no longer in play so do any cleanup steps and print a goodbye message
		printGoodByeMessage();
	}

	private void sellItems(Scanner sc) {
		// first check we are in a market square
		if(!this.isMarketCell()) {
			System.out.println("Must be at a market to buy.");
			return;
		}
		// select specific hero's inventory to sell from
		System.out.println();
		System.out.print("Which hero's inventory would you like to sell from?");
		System.out.println();
		
		// list items in hero's inventory
		List<Hero> heroes = new ArrayList<>();
		heroes.addAll(this.heroList);
		
		System.out.println();
		System.out.println("Select a hero:");
		
		for(int i = 0; i < heroes.size(); i++) {
			System.out.printf("\t%3d. %s \n", i, heroes.get(i).getName());
		}
		
		int attempts = 0;
		Hero heroChoice = null;
		while(attempts < 3) {
			// try to get a valid user choice
			System.out.println();
			System.out.print("Enter the hero's name or number: ");
			String choice = sc.nextLine();
			
			// first try the number
			try {
				int heroNumber = Integer.parseInt(choice);
				if(heroNumber >=0 && heroNumber<heroes.size()) {
					heroChoice = heroes.get(heroNumber);
					break;
				}
				
			} catch(NumberFormatException e) {
				// ignore it
			}
			
			// not an inventory number so scan list for a match
			for(Hero h: heroes) {
				if(h.getName().startsWith(choice)) {
					heroChoice = h;
					break;
				}
			}
			
			attempts++;
		}
		
		// make sure we found a hero
		if(heroChoice == null) {
			return;
		}
				
		// prompt for an item to sell
		System.out.println();
		System.out.println("Which item would you like to sell? ");
		Item itemChoice = null;
		
		{
			int tries = 0;
			while(tries < 3) {
				// try to get a valid user choice
				
				System.out.println();
				heroChoice.printInventory();
				System.out.println();
				
				System.out.print("Enter the item name or inventory number: ");
				
				String choice = sc.nextLine();
				
				// first try the number
				try {
					int inventoryNumber = Integer.parseInt(choice);
					if(inventoryNumber >=0 && inventoryNumber<heroChoice.getInventory().size()) {
						itemChoice = heroChoice.getInventory().get(inventoryNumber);
						heroChoice.popItemFromInventory(inventoryNumber);
						break;
					}
					
				} catch(NumberFormatException e) {
					// ignore it
				}
				
				// not an inventory number so scan list for a match
				for(Item item: heroChoice.getInventory()) {
					if(item.getName().startsWith(choice)) {
						itemChoice = item;
						heroChoice.popItemFromInventory(choice);
						break;
					}
				}
				
				tries++;
				System.out.println("Sorry could not find item " + choice);
			}
		}
		
		if(itemChoice == null) {
			System.out.println("Item not found");
			return;
		}
		
		// transfer money into hero's wallet
		heroChoice.setMoney(heroChoice.getMoney() + (int)(itemChoice.getPrice()*0.5));
		
		System.out.println();
		System.out.printf("You have successfully sold item '%s' for %d coins\n", itemChoice.getName(), (int)(itemChoice.getPrice()*0.5));
		
	}

	private void buyItems(Scanner sc) {
		// first check we are in a market square
		if(!this.isMarketCell()) {
			System.out.println("Must be at a market to buy.");
			return;
		}
		
		// list items for sale
		System.out.println();
		this.printMarketInventory();
		System.out.println();
		
		// prompt for an item to buy
		System.out.print("Which item would you like to buy? ");
		Item itemChoice = null;
		
		{
			int tries = 0;
			while(tries < 3) {
				// try to get a valid user choice
				System.out.print("Enter item name or inventory number: ");
				String choice = sc.nextLine();
				
				// first try the number
				try {
					int inventoryNumber = Integer.parseInt(choice);
					if(inventoryNumber >=0 && inventoryNumber<this.marketItemList.size()) {
						itemChoice = this.marketItemList.get(inventoryNumber);
						break;
					}
					
				} catch(NumberFormatException e) {
					// ignore it
				}
				
				// not an inventory number so scan list for a match
				for(Item item: this.marketItemList) {
					if(item.getName().startsWith(choice)) {
						itemChoice = item;
						break;
					}
				}
				
				tries++;
				System.out.println("Sorry could not find item " + choice);
			}
		}
		
		if(itemChoice == null) {
			System.out.println("Item not found");
			return;
		}
		
		// prompt for a user
		List<Hero> heroes = new ArrayList<>();
		heroes.addAll(this.heroList);
		
		// removes heroes who cannot buy item choice from heroList
		for(Hero h: heroes) {
			if(!h.canUse(itemChoice) || itemChoice.getPrice() > h.getMoney()) {
				heroes.remove(h);
			}
		}
		
		if(heroes.size() == 0) {
			System.out.println("Sorry none of your heroes can buy this item");
		}
		
		System.out.println();
		System.out.println("Select a hero:");
		
		for(int i = 0; i < heroes.size(); i++) {
			System.out.printf("\t%3d. %s \n", i, heroes.get(i).getName());
		}
		
		int tries = 0;
		Hero heroChoice = null;
		while(tries < 3) {
			// try to get a valid user choice
			System.out.println();
			System.out.print("Enter hero name or number: ");
			String choice = sc.nextLine();
			
			// first try the number
			try {
				int heroNumber = Integer.parseInt(choice);
				if(heroNumber >=0 && heroNumber<heroes.size()) {
					heroChoice = heroes.get(heroNumber);
					break;
				}
				
			} catch(NumberFormatException e) {
				// ignore it
			}
			
			// not an inventory number so scan list for a match
			for(Hero h: heroes) {
				if(h.getName().startsWith(choice)) {
					heroChoice = h;
					break;
				}
			}
			
			tries++;
		}
		
		// make sure we found a hero
		if(heroChoice == null) {
			return;
		}

		// make sure the hero has sufficient funds
		if(heroChoice.getMoney() < itemChoice.getPrice()) {
			throw new RuntimeException("Hero insufficient coin");
		}
		
		// transfer item to user's inventory
		this.marketItemList.remove(itemChoice);
		heroChoice.pushItemIntoInventory(itemChoice);
		
		// remove coin from user's wallet
		heroChoice.setMoney(heroChoice.getMoney() - itemChoice.getPrice());
		
		System.out.println();
		System.out.printf("Item '%s' successfully purchased by %s\n", itemChoice.getName(), heroChoice.getName());
	}

	private void setStartingPosition() {
		while(true) {
			this.pos.posX = (int)(Math.random()*this.gameMap.getWidth());
			this.pos.posY = (int)(Math.random()*this.gameMap.getHeight());
			
			if(this.isCommonCell() || this.isMarketCell()) {
				return;
			}
		}
		
	}

	private void printOutOfCombatStatusMessage() {
		System.out.println();
		System.out.printf("Round %d (%d, %d)\n", 
				this.numOfRounds,
				this.pos.posX, this.pos.posY);
		
		// print out the game map
		this.gameMap.printMap(this.pos.posX, this.pos.posY);
		
		System.out.println();
		
		// type of cell we are in, has a new monster spawned
		if(this.isMarketCell()) {
			System.out.println("Welcome to the market! Enter 'B' or 'S' to buy or sell items. ");
			
		} else if (this.isCommonCell()) {
			System.out.println("common square");
//			if(this.newSpawn) {
//				System.out.println("Monsters have spawned! Enter 'A' to fight or 'M' to flee. ");
//			}
			
		} else {
			System.out.println();
			throw new RuntimeException("bad cell type");
		}
		
		System.out.println();
	}
	
	private void printGoodByeMessage() {
		System.out.println("You have exited the game, goodbye! ");
	}

	/**
	 * Returns if the current position is a market cell
	 * @return true if the current cell is a market cell.
	 */
	private boolean isMarketCell() {
		return this.gameMap.isMarket(this.pos.posX, this.pos.posY);
	}
	
	/**
	 * Returns if the current position is a common cell
	 * @return true if the current cell is a common cell.
	 */
	private boolean isCommonCell() {
		return this.gameMap.isCommon(this.pos.posX, this.pos.posY);		
	}
	
	/**
	 * Returns if the cell in the direction is accessible
	 * @return true if party can move in the given direction.
	 */
	private boolean isAccessable(DirectionType direction) {
		_Position testPos = this.moveTo(direction);
		if(testPos == null) return false;
		return this.gameMap.isAccessable(testPos.posX, testPos.posY);
	}

	/**
	 * Display what a Hero is currently wearing/ equipped with 
	 * 
	 * @param sc
	 */
	private void displayCharacter(Scanner sc) {
		System.out.println();
		// Add all hero's to display list 
		List<Hero> heroes = new ArrayList<>();
		List<Monster> monsters = new ArrayList<>();
		heroes.addAll(this.heroList);
		
		if(!inCombat) {
			Hero heroChoice = this.getHeroChoice(sc, heroes, "Which Hero would you like to display? ");
			
			if(heroChoice != null) {
				System.out.println();
				heroChoice.printHeroInformation();
			} else {
				System.out.println("Sorry no hero selected cannot display.");
			}
			System.out.println();
		} else {
			monsters.addAll(this.monsterList);
			System.out.println("***HERO TEAM***");
			for(Hero hero: heroes) {
				hero.printHeroInformation();
				System.out.println();
			}
			System.out.println();
			System.out.println("***MONSTERS***");
			for(Monster monster: monsters) {
				monster.printMonsterInformation();
				System.out.println();
			}
		}
	}

	private void showInventory(Scanner sc) {
		
		// select specific hero's inventory to sell from
		System.out.println();
		System.out.println("Which hero's inventory would you like to view? ");
		System.out.println();
		
		// Add all hero's to display list 
		List<Hero> heroes = new ArrayList<>();
		heroes.addAll(this.heroList);
		
		System.out.println("Select a hero: ");
		
		for(int i = 0; i < heroes.size(); i++) {
			System.out.printf("\t%3d. %s, \n", i, heroes.get(i).getName());
		}
		
		int attempts = 0;
		Hero heroChoice = null;
		while(attempts < 3) {
			// try to get a valid user choice
			System.out.println();
			System.out.print("Enter the hero's name or number: ");
			String choice = sc.nextLine();
			
			// first try the number
			try {
				int heroNumber = Integer.parseInt(choice);
				if(heroNumber >=0 && heroNumber<heroes.size()) {
					heroChoice = heroes.get(heroNumber);
					break;
				}
				
			} catch(NumberFormatException e) {
				// ignore it
			}
			
			// not an inventory number so scan list for a match
			for(Hero h: heroes) {
				if(h.getName().startsWith(choice)) {
					heroChoice = h;
					break;
				}
			}
			
			attempts++;
		}
		heroChoice.printInventory();
	}

	private void showHelpMenu() {
		// TODO Auto-generated method stub
		
	}
	
	private Hero getHeroChoice(Scanner sc, List<Hero> heroes, String prompt) {
		if(prompt != null) System.out.println(prompt);
		for(int i = 0; i < heroes.size(); i++) {
			System.out.printf("\t%3d. %s \n", i, heroes.get(i).getName());
		}
		
		int attempts = 0;
		while(attempts < 3) {
			// try to get a valid user choice
			System.out.println();
			System.out.print("Enter the hero's name or number: ");
			String choice = sc.nextLine();
			
			// first try the number
			try {
				int heroNumber = Integer.parseInt(choice);
				if(heroNumber >=0 && heroNumber<heroes.size()) {
					Hero heroChoice = heroes.get(heroNumber);
					return heroChoice;
				}
				System.out.print("Out of range please enter a number from 0 to " + (heroes.size()-1));
			} catch(NumberFormatException e) {
				// not an inventory number so scan list for a match
				for(Hero hero: heroes) {
					if(hero.getName().startsWith(choice)) {
						return hero;
					}
				}
				System.out.print("Could not find a match for: " + choice + " - Please try again.");
			}
			
			attempts++;
		}
		
		return null;
	}
	
	private Item getItemChoice(Scanner sc, Hero hero, List<Item> itemList, String prompt) {
		System.out.println();
		if(prompt != null) System.out.println(prompt);
		for(int i = 0; i < itemList.size(); i++) {
			System.out.printf("\t%3d. %s \n", i, itemList.get(i).getName());
		}
		
		Item itemChoice = null;
		int tries = 0;
		while(tries < 3) {
			// try to get a valid user choice
			System.out.print("Enter item name or number: ");
			String choice = sc.nextLine();
			
			// first try the number
			try {
				int inventoryNumber = Integer.parseInt(choice);
				if(inventoryNumber >=0 && inventoryNumber<itemList.size()) {
					itemChoice = itemList.get(inventoryNumber);
					break;
				}
				
			} catch(NumberFormatException e) {
				// ignore it
			}
			
			// not an inventory number so scan list for a match
			for(Item item: itemList) {
				if(item.getName().startsWith(choice)) {
					itemChoice = item;
					break;
				}
			}
			
			tries++;
			System.out.println("Sorry could not find item " + choice);
		}
		if(itemChoice.getLevel() > hero.getLevel()) {
			System.out.printf("%s does not meet the level requirements to use this item. \n", hero.getName());
			System.out.println();
			itemChoice = null;
		}
		return itemChoice;
	}
	
	private boolean equipItemHelper(Hero heroChoice, Item itemChoice) {
		// make sure this item is equippable
		if(Weapon.isWeapon(itemChoice)) { 
			// weapon: first unequip current weapon
			Weapon temp = heroChoice.unEquipWeapon();
			// equip new weapon
			heroChoice.equipItem(itemChoice);
			// stick unequipped weapon into inventory (if exists)
			if (temp != null) {
				heroChoice.pushItemIntoInventory(temp);
			}
		} else if (Armor.isArmor(itemChoice)) { 
			// armor
			Armor armorChoice = (Armor) itemChoice;
			Armor.ArmorSlotEnum equipSlot = armorChoice.getEquipSlot();
			Armor temp = heroChoice.unEquipArmor(equipSlot);
			// equip new armor item
			heroChoice.equipItem(itemChoice);
			// stick unequipped armor item into inventory (if exists)
			if (temp != null) {
				heroChoice.pushItemIntoInventory(temp);
			}
		} else {
			// error: unequippable item
			// TODO: print error message
			return false;
		}
		System.out.printf("You have successfully equipped '%s' on %s", itemChoice.getName(), heroChoice.getName());
		System.out.println();
		return true;
	}

	private void equipGear(Scanner sc) { 
		// First get a hero
		List<Hero> heroes = new ArrayList<>();
		heroes.addAll(this.heroList);
		Hero heroChoice = getHeroChoice(sc, heroes, "To equip an item, select a hero: ");
		
		// check that the user successfully chose a hero
		if(heroChoice == null) {
			// print error message
			System.out.println("Sorry select hero for equip failed");
			return;
		}
		
		System.out.println();
		heroChoice.printInventory(true, false); // only print equip-able items
		System.out.println();
		
		if(!heroChoice.getInventory().isEmpty()) {
			int tries = 0;
			while(tries < 3) {
				System.out.print("Select the weapon/armor you would like to equip onto your hero: ");
				
				String choice = sc.nextLine();
				
				// first try the number
				try {
					int inventoryNumber = Integer.parseInt(choice);
					if(inventoryNumber >=0 && inventoryNumber<heroChoice.getInventory().size()) {
						// remove item from hero's inventory 
						Item itemChoice = heroChoice.popItemFromInventory(inventoryNumber);
						boolean tryEquip = this.equipItemHelper(heroChoice, itemChoice);
						if(tryEquip) {
							return;
						}
						System.out.printf("You have successfully equipped %s on %s", itemChoice.getName(), heroChoice.getName());
					} else {
						// option out of range
						System.out.print("Out of range please enter a number from 0 to " + (heroChoice.getInventory().size()-1));
						continue;
					}
				} catch(NumberFormatException e) {
					// not an inventory number so scan list for a match
					for(Item item: heroChoice.getInventory()) {
						if(item.getName().startsWith(choice)) {
							boolean tryEquip = this.equipItemHelper(heroChoice, item);
							if(tryEquip) {
								return;
							}
							System.out.printf("You have successfully equipped %s on %s", item.getName(), heroChoice.getName());
						}
					}
				}
				
				tries++;
				System.out.println("Sorry could not find item " + choice);
			} 
		} else {
			equippedGearFlag = false;
		}
		System.out.println("Failed to equip item.");
	}

	private void move(Scanner sc, List<String> args) {
		
		String directionStr;
		
		if(args.size() > 0) {
			directionStr = args.get(0);
		} else {
			System.out.println("Which direction would you like to move in?");
			String raw = sc.nextLine().trim().toLowerCase();
			String[] toks = raw.split("//s+");
			directionStr = toks[0];
		}
		
		DirectionType moveDirection = DirectionType.toDirection(directionStr);
		
		// check if valid direction
		if(moveDirection == null) {
			System.out.println("unknown direction " + directionStr);
			return;
		}

		// check if move direction is accessible
		if(!this.isAccessable(moveDirection)) {
			// output a cannot move that way message and return
			System.out.println("Cannot move " + moveDirection);
			return;
		}

		// check if in combat
		if(inCombat) {
			System.out.println("Unable to move during combat.");
			return;
		}
		
		// modify current position
		this.pos = this.moveTo(moveDirection);
		
		// reset the variables for the new current cell
		this.marketItemList = null;
//		this.newSpawn = false;
		this.inCombat = false;
		this.monsterList = null;
		
		
		// check if common square to see if we need to spawn a monster
		if(this.isCommonCell()) {
			//check if we want to spawn a monster
			// Make it a 75% chance that monsters will spawn
			int spawnChance = rand.nextInt(1000);
			
			if(spawnChance <= 750) {
				// spawn a level appropriate monster,
				// set new spawn, in combat, monsterList
				this.monsterList = generateMonsters(this.pos);
				this.startCombat(sc);
			}
		} else if (this.isMarketCell()) {
			// we just moved to a market square so build an inventory for this vendor
			this.marketItemList = generateStoreInventory(this.pos);
		}
	}
	
	/**
	 * Combat loop in this function.
	 * Give each hero a chance to perform an action each fighting round.
	 */
	private void startCombat(Scanner sc) {
		
		System.out.println();
		System.out.println("Monster(s) have spawned, get ready to battle! ");
		System.out.println();
		
		int maxMonsterLevel = Integer.MIN_VALUE;
		
		for(Monster monster: monsterList) {
			if(monster.getLevel() > maxMonsterLevel) {
				maxMonsterLevel = monster.getLevel();
			}
		}

		// inCombat loop state variables
		inCombat = true;
		Hero currentHero = heroList.get(0);
		Monster currentMonster = monsterList.get(0);
		int combatRound = 0;
		boolean newRoundFlag = true;
		boolean newMonsterFlag = true;
		boolean newHeroFlag = true;
		
		while(inCombat) {
			// TODO do new round stuff (print a summary for new round, ...)
			if(newRoundFlag) {
				combatRound++;
				newRoundFlag = false;
			}
			if(newMonsterFlag) {
				
				newMonsterFlag = false;
			}
			if(newHeroFlag) {
				
				newHeroFlag = false;
			}
			
			System.out.printf("Battle Round: %d \n", combatRound);
			System.out.println("\t0. Basic attack");
			System.out.println("\t1. Cast a spell");
			System.out.println("\t2. Use a potion");
			System.out.println("\t3. Switch armor/weapon");
			System.out.println("\t4. Switch Hero");
			System.out.println("\t5. Display battle information");
			System.out.println();
			System.out.printf("%s is currently battling, pick his/her next move: ", currentHero.getName());
			
			String action = sc.nextLine();
		
			try {
				int actionNumber = Integer.parseInt(action);
				switch(actionNumber) {
					case 0:
						int heroAttackSuccess = rand.nextInt(1000);
						if(heroAttackSuccess <= currentMonster.getDodgeChance()) {
							basicAttack(currentHero, currentMonster);
						} else {
							System.out.println();
							System.out.printf("%s has dodged your attack!\n", currentMonster.getName());
						}

						newRoundFlag = true;
						break;
					case 1:
						castSpell(sc, currentHero, currentMonster);
						if(spellCastedFlag) {

							newRoundFlag = true;
							break;
						} else {
							newRoundFlag = false;
							continue;
						}
					case 2:
						usePotion(sc, currentHero);
						if(potionUsedFlag) {
							newRoundFlag = true;
							break;
						} else {
							newRoundFlag = false;
							continue;
						}
					case 3:
						switchGear(sc, currentHero);
						if(equippedGearFlag) {
							newRoundFlag = true;
							break;
						} else {
							newRoundFlag = false;
							continue;
						}
					case 4:
						{
							List<Hero> livingHeroes = new ArrayList<>();
							for(Hero hero: heroList) {
								if(hero.getHp()>0) {
									livingHeroes.add(hero);
								}
							}
							if(livingHeroes.size()<=1) {
								System.out.println("There are no heroes to switch to.\n");
							} else {
								switchHero(sc, livingHeroes);
								newRoundFlag = true;
							}
						}
						continue;
					case 5:
						displayCharacter(sc);
						newRoundFlag = false;
						continue;
					default:
						System.out.print("Out of range please enter a number from 0 to 5");
				}
			} catch(NumberFormatException e) {
				// not an inventory number so scan list for a match
				System.out.print("Could not find a match for: " + action + " - Please try again.");
			}
			
			int monsterAttackSuccess = rand.nextInt(1000);
			if(monsterAttackSuccess <= currentHero.getDodgeChance()*10) {
				monsterAttack(currentMonster, currentHero);
			} else {
				System.out.println();
				System.out.printf("%s attempted to attack, but %s dodged his attack!\n", currentMonster.getName(), currentHero.getName());
				System.out.println();
			}
			
			// check if current hero is dead
			if(currentHero.getHp()<=0) {
				// check if all heroes are dead
				List<Hero> livingHeroes = new ArrayList<>();
				for(Hero hero: heroList) {
					if(hero.getHp()>0) {
						livingHeroes.add(hero);
					}
				}
				if(livingHeroes.size()==0) {
					inCombat = false;
					break;
				} else {
					// switch to new hero
					switchHero(sc, livingHeroes);
					newHeroFlag = true;
				}
			}
			
			// check if current monster is dead
			if(currentMonster.getHp()<=0) {
				// if all monsters are dead
				List<Monster> livingMonsters = new ArrayList<>();
				for(Monster monster: monsterList) {
					if(monster.getHp()>0) {
						livingMonsters.add(monster);
					}
				}
				if(livingMonsters.size()==0) {
					System.out.println("Congratulations, you have won this battle!");
					inCombat = false;
					break;
				}
				else {
					// switch to next monster
					livingMonsters.get(0);
					newMonsterFlag = true;
				}
			}
			//during every round of a fight the heroes regain 5% of their hp and 5% of their mana.
			for(Hero hero: heroList) {
				if(hero.getHp()>0) {
					hero.setHp((int)(hero.getHp()+hero.getMaxHP()*0.05));
					hero.setMana((int)(hero.getMana()+hero.maxMana()*0.05));
				}
			}
		};
		
		// Out of combat check who won
		List<Hero> livingHeroes = new ArrayList<>();
		for(Hero hero: heroList) {
			if(hero.getHp()>0) {
				livingHeroes.add(hero);
			}
		}
		// if all heroes are dead then the game is over
		if(livingHeroes.size()==0) {
			System.out.println("All your heroes have been defeated, game over.");
			exitGame();
		}
		
		// At least one hero is alive, so the player won, and give out XP and loot
		for(Hero hero: heroList) {
			if(hero.getHp() > 0) {
				hero.setMoney(hero.getMoney()+100*maxMonsterLevel);
				// add 2 to the xp and check if this hero should level up
				if(hero.addExp(2) >= hero.expTillNextLevel()) {
					// level up
					hero.levelUp();
				}
				System.out.printf("%s has gained %d coins and 2 exp.", hero.getName(), 100*maxMonsterLevel);
			} else {
				// dead hero so revive and subtract coin
				reviveHero(hero);
			}
		}
	}

	private void exitGame() {
		this.gameInPlay = false;
	}
	
	private void reviveHero(Hero hero) {
		hero.setHp((int)(hero.getMaxHP()*0.5));
		hero.setMoney((int)(hero.getMoney()*0.5));
		System.out.printf("%s has been revived to %d hp and %d coins has been substracted from his/her wallet.", hero.getName(), hero.getHp(), (int)(hero.getMoney()*0.5));
	}

	private void switchGear(Scanner sc, Hero currentHero) {
		System.out.println();
		currentHero.printInventory(true, true); // only print equip-able items
		System.out.println();
	
		int tries = 0;
		while(tries < 3) {
			System.out.print("Select the weapon/armor you would like to equip onto your hero: ");
			
			String choice = sc.nextLine();
			
			// first try the number
			try {
				int inventoryNumber = Integer.parseInt(choice);
				if(inventoryNumber >=0 && inventoryNumber<currentHero.getInventory().size()) {
					// remove item from hero's inventory 
					Item itemChoice = currentHero.popItemFromInventory(inventoryNumber);
					boolean tryEquip = this.equipItemHelper(currentHero, itemChoice);
					if(tryEquip) {
						return;
					}
					System.out.printf("You have successfully equipped %s on %s", itemChoice.getName(), currentHero.getName());
				} else {
					// option out of range
					System.out.print("Out of range please enter a number from 0 to " + (currentHero.getInventory().size()-1));
					continue;
				}
			} catch(NumberFormatException e) {
				// not an inventory number so scan list for a match
				for(Item item: currentHero.getInventory()) {
					if(item.getName().startsWith(choice)) {
						boolean tryEquip = this.equipItemHelper(currentHero, item);
						if(tryEquip) {
							return;
						}
						System.out.printf("You have successfully equipped %s on %s", item.getName(), currentHero.getName());
					}
				}
			}
			
			tries++;
			System.out.println("Sorry could not find item " + choice);
		}

		System.out.println("Failed to equip item.");
	}

	private void switchHero(Scanner sc, List<Hero> livingHeroList) {
		getHeroChoice(sc, livingHeroList, "Please choose the hero you want to use in combat: ");
	}

	// Generate random list of monsters depending on how many heroes are fighting, 
	private List<Monster> generateMonsters(_Position pos) {
		int numHeroes = heroList.size();
		int minHeroLevel = Integer.MAX_VALUE;
		int maxHeroLevel = Integer.MIN_VALUE;
		
		for(Hero hero: heroList) {
			if(hero.getLevel()<minHeroLevel) {
				minHeroLevel = hero.getLevel();
			}
			if(hero.getLevel()>maxHeroLevel) {
				maxHeroLevel = hero.getLevel();
			}
		}
		
		MonsterType[] monsterTypes = MonsterType.values();
		
		List<Monster> monsterList = new ArrayList<>();
		for(int i = 0; i < numHeroes; i++) {
			int randomMonsterTypeindex = rand.nextInt(monsterTypes.length); // a random integer from 0 to (monsterTypes.length-1)
			Monster newMonster = getAppropriateMonster(monsterTypes[randomMonsterTypeindex], minHeroLevel, maxHeroLevel);
			
			monsterList.add(newMonster);
		}
		
		return monsterList;
	}
	
	private Monster getAppropriateMonster(MonsterType type, int minLevel, int maxLevel) {
		// get random monster of level between minLevel and maxLevel
		int level = rand.nextInt(maxLevel - minLevel + 1) + minLevel;
		for(Monster monster: monsterPreloadList) {
			if(monster.getLevel()==level) {
				switch(type) {
					case DRAGON:
						if(monster instanceof Dragon) {
							return monster.newInstance();
						}
					case EXOSKELETON:
						if(monster instanceof Exoskeleton) {
							return monster.newInstance();
						}
					case SPIRIT:
						if(monster instanceof Spirit) {
							return monster.newInstance();
						}
					default:
						break;
				
				}
			}
		}
		return null;
	}

	private void basicAttack(Hero currentHero, Monster currentMonster) {
		int initialMonsterHP = currentMonster.getHp();
		int damage = (int)((currentHero.getStr())*0.05);
				
		// Check if hero is wearing a weapon
		if(currentHero.isEquippedWeapon()) {
			damage = (int)((damage + currentHero.getEquipedWeapon().getDamage())*0.05);
		}
		// Successful attack, reduce monster's hp
		currentMonster.setHp(currentMonster.getHp()-damage);
		System.out.printf("%s's health has decreased from %d to %d.", currentMonster.getName(), initialMonsterHP, currentMonster.getHp());

		return;
	}
	
	private void monsterAttack(Monster currentMonster, Hero currentHero) {
		int initialHeroHP = currentHero.getHp();
		int damage = (int)(currentMonster.getDamage()/10);
		
		//successful attack, reduce hero's hp
		currentHero.setHp(currentHero.getHp()-damage);
		System.out.println();
		System.out.printf("%s made its attack! %s's health has decreased from %d to %d.\n",currentMonster.getName(), currentHero.getName(), initialHeroHP, currentHero.getHp());
	}
	
	private void castSpell(Scanner sc, Hero currentHero, Monster currentMonster) {
		// print only spells in currentHero's inventory
		List<Item> spells = new ArrayList<>();
		for(Item item: currentHero.getInventory()) {
			if(item instanceof Spell) {
				spells.add(item);
			}
		}
		
		if(!spells.isEmpty()) {
			Spell spellChoice = (Spell) getItemChoice(sc, currentHero, spells, "Which spell would you like to cast? ");
			
			if(spellChoice != null) {
				int spellCastSuccess = rand.nextInt(1000);
				if(spellCastSuccess <= currentMonster.getDodgeChance()) {
					// Calculate spell's damage and reduce from currentMonster's hp if cast is successful
					int spellDamage = 0;
					int initialMonsterHP = currentMonster.getHp();
					spellDamage = spellChoice.getDamage()+(currentHero.getDex()/10000)*spellChoice.getDamage();
					currentMonster.setHp(currentMonster.getHp()-spellDamage);
					
					System.out.printf("%s's health has decreased from %d to %d.", currentMonster.getName(), initialMonsterHP, currentMonster.getHp());
					
					switch(spellChoice.getSpellType()) {
						case FIRE:
							int initialDefense = currentMonster.getDefense();
							currentMonster.setDefense((int)(currentMonster.getDefense()*0.9));
							System.out.println();
							System.out.printf("Spell Bonus: %s's defense has decreased from %d to %d.", currentMonster.getName(), initialDefense, currentMonster.getDefense());
							break;
						case ICE:
							int initialDamage = currentMonster.getDamage();
							currentMonster.setDamage((int)(currentMonster.getDamage()*0.9));
							System.out.println();
							System.out.printf("Spell Bonus: %s's damage has decreased from %d to %d.", currentMonster.getName(), initialDamage, currentMonster.getDamage());
							break;
						case LIGHTNING:
							int initialDodge = currentMonster.getDodgeChance();
							currentMonster.setDodgeChance((int)(currentMonster.getDodgeChance()*0.9));
							System.out.println();
							System.out.printf("Spell Bonus: %s's dodge chance has decreased from %d%% to %d%%.", currentMonster.getName(), initialDodge, currentMonster.getDodgeChance());
							break;
						default:
							break;
					}
				} else {
					System.out.println();
					System.out.printf("You've case your spell, but %s managed to dodge!\n", currentMonster.getName());
					System.out.println();
				}
				// remove spell from hero's inventory
				spellCastedFlag = true;
				currentHero.popItemFromInventory(spellChoice.getName());
			}
		} else {
			spellCastedFlag = false;
			System.out.print("You have no spells to use.\n");
			System.out.println();
		}
		return;
	}
	
	private void usePotion(Scanner sc, Hero hero) {
		if(hero == null) {
			List<Hero> heroes = new ArrayList<>();
			heroes.addAll(this.heroList);
			hero = this.getHeroChoice(sc, heroes, "Choose a hero: ");
		}
		if(hero == null) {
			System.out.print("Potion consumption failed.");
			return; 
		}
		// print out potions available for use from inventory
		List<Item> potions = new ArrayList<>();
		for(Item item: hero.getInventory()) {
			if(item instanceof Potion) {
				potions.add(item);
			}
		}
		
		if(!potions.isEmpty()) {
			Potion potionChoice = (Potion) getItemChoice(sc, hero, potions, "Which potion would you like to use? ");
			
			if(potionChoice != null) {
				AttributeType attributetype = getAttributeType(sc, potionChoice.getAttribute());
				int attributeBoost = potionChoice.getAttributePoints();
				
				switch(attributetype) {
					case AGI:
						int oldAgility = hero.getAgi();
						hero.setAgi(oldAgility + attributeBoost);
						int newAgility = hero.getAgi();
						System.out.println();
						System.out.println("You have successfully boosted " + hero.getName() + "'s agility from " + oldAgility + " to " + newAgility);
						break;
					case DEX:
						int oldDex = hero.getDex();
						hero.setAgi(oldDex + attributeBoost);
						int newDex = hero.getAgi();
						hero.setDex(hero.getDex() + attributeBoost);
						System.out.println();
						System.out.println("You have successfully boosted " + hero.getName() + "'s dexterity from " + oldDex + " to " + newDex);
						break;
					case HP:
						int oldHp = hero.getHp();
						hero.setHp(oldHp + attributeBoost);
						int newHp = hero.getHp();
						hero.setHp(hero.getHp() + attributeBoost);
						System.out.println();
						System.out.println("You have successfully boosted " + hero.getName() + "'s health from " + oldHp + " to " + newHp);
						break;
					case MANA:
						int oldMana = hero.getMana();
						hero.setMana(oldMana + attributeBoost);
						int newMana = hero.getMana();
						hero.setMana(hero.getMana() + attributeBoost);
						System.out.println();
						System.out.println("You have successfully boosted " + hero.getName() + "'s mana from " + oldMana + " to " + newMana);
						break;
					case STR:
						int oldStr = hero.getStr();
						hero.setStr(oldStr + attributeBoost);
						int newStr = hero.getStr();
						hero.setStr(hero.getStr()+attributeBoost);
						System.out.println();
						System.out.println("You have successfully boosted " + hero.getName() + "'s strength from " + oldStr + " to " + newStr);
						break;
					default:
						break;		
				}
				potionUsedFlag = true;
				hero.popItemFromInventory(potionChoice.getName());
			}
		} else {
			potionUsedFlag = false;
			System.out.println("You have no potions to use.\n");
		}
		return;
	}
	
	private AttributeType selectAttribute(Scanner sc) {
		System.out.println("Choose an attribute to boost: ");
		System.out.println("\t0. HP");
		System.out.println("\t1. Mana");
		System.out.println("\t2. Str");
		System.out.println("\t3. Dex");
		System.out.println("\t4. Agi");
		
		int attempts = 0;
		while(attempts < 3) {
			// try to get a valid user choice
			System.out.println();
			System.out.print("Enter the attribute number: ");
			String choice = sc.nextLine();
			
			// first try the number
			try {
				int attributeChoice = Integer.parseInt(choice);
				if(attributeChoice >=0 && attributeChoice<=4) {
					switch(attributeChoice) {
						case 0:
							return AttributeType.HP;
						case 1:
							return AttributeType.MANA;
						case 2:
							return AttributeType.STR;
						case 3:
							return AttributeType.DEX;
						case 4:
							return AttributeType.AGI;
					}
				}
				System.out.print("Out of range please enter a number from 0 to 4");
			} catch(NumberFormatException e) {
				System.out.print("Please enter a number from 0 to 4");
			}
			
			attempts++;
		}
		return null;
	}
	
	public AttributeType getAttributeType(Scanner sc, PotionType potionType) {
		switch(potionType) {
			case AGI:
				return AttributeType.AGI;
			case AMBROSIA:
				return selectAttribute(sc);
			case DEX:
				return AttributeType.DEX;
			case HP:
				return AttributeType.HP;
			case MANA:
				return AttributeType.MANA;
			case STR:
				return AttributeType.STR;
			default:
				break;
		}
		return null;
	}
	
	private void printMarketInventory() {
		// first check we are in a market square
		if(this.marketItemList == null) {
			return;
		}
		
		System.out.println("Items:");
		String s = String.format("\t%3s.  %-20s - %6s\n", "no", "item name", "cost");
		System.out.printf(s);
		System.out.println("\t" + new String(new char[s.length()]).replace('\0', '-'));
		if(this.marketItemList.size() > 0) {
			for(int i = 0; i < this.marketItemList.size(); i++) {
				Item item = this.marketItemList.get(i);
				System.out.printf("\t%3d.  %-20s - %6d\n", i, item.getName(), item.getPrice());
				
				List<String> attributeStrings = item.printItemInfo();
				for(String str: attributeStrings) {
					System.out.printf("\t\t%s \n", str);
				}
			}
		}
		else {
			System.out.println("\tno items to sell");
		}
	}
	
	private List<Item> generateStoreInventory(_Position pos) {

		List<Item> itemList = new ArrayList<>();	
		
		Set<String> itemSet = this.gameItemPreloadMap.keySet();
		String[] itemKeyArray = new String[itemSet.size()];
		itemSet.toArray(itemKeyArray);
		
		// number of items to add
		int nItems = 5;
		
		while(itemList.size() < nItems) {
			String itemKey = itemKeyArray[rand.nextInt(itemKeyArray.length)];
			Item item = this.gameItemPreloadMap.get(itemKey);
			
			// only add the item to list if it meets the requirements
			if(true) {
				addToItemList(itemList, item);
			}
		}
		
		return itemList;
	}

	private void addToItemList(List<Item> itemList, Item item) {
		if(item != null && itemList != null) {
			itemList.add(item.newInstance());
		}
	}

	private _Position moveTo(DirectionType direction) {
		switch(direction) {
			case EAST:
				if(this.pos.posX >= this.gameMap.getWidth()-1) {
					return null;
				}
				return new _Position(this.pos.posX+1,this.pos.posY);
			case NORTH:
				if(this.pos.posY >= this.gameMap.getHeight()-1) {
					return null;
				}
				return new _Position(this.pos.posX,this.pos.posY+1);
			case SOUTH:
				if(this.pos.posY <= 0) {
					return null;
				}
				return new _Position(this.pos.posX,this.pos.posY-1);
			case WEST:
				if(this.pos.posX <= 0) {
					return null;
				}
				return new _Position(this.pos.posX-1,this.pos.posY);
			default:
				return null;
		}
	}

	private List<Hero> getDebugHeroList() {
		heroList = new ArrayList<>();
		heroList.add(this.heroPreloadList.get(0));
		return heroList;
	}

	private List<Hero> getHeroList(Scanner sc) {
		heroList = new ArrayList<>();

		// Choose the team of heroes
		System.out.println("Choose your team of heroes:");
		for(int i = 0; i < heroPreloadList.size(); i++) {
			Hero hero = this.heroPreloadList.get(i);
			String str = String.format("%d. (%s) %s", i, 
					hero.getGameClass(), hero.getName());
			System.out.println(str);
		}

		System.out.println();
		String chooseAnother = "n";
		do {
			System.out.print("Choose a hero: ");
			int choice = sc.nextInt();
			if(choice < 0 || choice >= heroPreloadList.size()) {
				System.out.println("Invalid choice please try again.");
				continue;
			}
			heroList.add(this.heroPreloadList.get(choice));
			if(heroList.size() == MAX_HEROES) {
				break;
			}
			System.out.print("Choose another (y/n)");
			chooseAnother = sc.next();
		} while(chooseAnother.equals("y"));
		
		sc.nextLine();
		
		return heroList;		
	}
}
