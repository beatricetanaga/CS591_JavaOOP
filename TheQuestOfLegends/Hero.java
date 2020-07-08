import java.util.*;

public abstract class Hero extends LivingCreature {
	private int id;
	private double preFightHP; 
	private double mana;
	private Skill skill;
	private double money;
	private int experience;
	private Set<Item> items;
	private Integer wornArmor;
	private String heroLabel; // newly created, label on the map (H1, H2, H3) 


	// constructor 
	public Hero(int id, String name, int level, double hp, double mana, Skill skill, double money, int experience) {
		this.id = id;
		this.name = name;
		this.level = level;
		this.hp = hp;
		this.preFightHP = hp; 
		this.mana = mana;
		this.skill = skill;
		this.money = money;
		this.experience = experience;
		this.wornArmor = null;
		this.items = new HashSet<Item>(); 
	}

	// method to upgradeSkills, different skills are updated depending on the
	// type of hero
	public abstract void upgradeSkills();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}
	
	public double getPreFightHP() {
		return preFightHP;
	}

	public void setPreFightHP(double preFightHP) {
		this.preFightHP = preFightHP;
	}

	public double getMana() {
		return mana;
	}

	public void setMana(double mana) {
		this.mana = mana;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public Integer getWornArmor() {
		return wornArmor;
	}

	public void setWornArmor(Integer wornArmor) {
		this.wornArmor = wornArmor;
	}

	public String getHeroLabel() {
		return heroLabel;
	}

	public void setHeroLabel(String heroLabel) {
		this.heroLabel = heroLabel;
	}
	
	public int getRowPosition() {
		return rowPosition;
	}

	public void setRowPosition(int rowPosition) {
		this.rowPosition = rowPosition;
	}

	public int getColPosition() {
		return colPosition;
	}

	public void setColPosition(int colPosition) {
		this.colPosition = colPosition;
	}
	
	public QuestMap getMap() {
		return map;
	}

	public void setMap(QuestMap map) {
		this.map = map;
	}
	
	// method for the hero to make a move 
	public boolean makeMove(Scanner scan) {
		boolean isValidPosition = false; 
		int newRow = rowPosition; 
		int newCol = colPosition; 
		int[] teleportTile = new int[2];
		int[] backMove = new int[2]; 
		
		while(!isValidPosition) {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("L I S T  O F  M O V E S"); 
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("W/w: to move up"); 
			System.out.println("S/s: to move down"); 
			System.out.println("A/a: to move left");
			System.out.println("D/d: to move right"); 
			System.out.println("B/b: to return to the Nexus"); 
			System.out.println("T/t: to teleport to a different lane"); 
			System.out.println("M/m: to view the map"); 
			System.out.println("I/i: for hero's info");
			System.out.println("Q/q: to quit");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.print("Enter a move for " + heroLabel + " " + name.trim() + ": ");
			scan.reset();
			String move = scan.nextLine(); 
			newRow = rowPosition; 
			newCol = colPosition; 
			
			if (move.equals("W") || move.equals("w")) {
				newRow -= 1;
			} else if (move.equals("A") || move.equals("a")) {
				newCol -= 1;
			} else if (move.equals("S") || move.equals("s")) {
				newRow += 1;
			} else if (move.equals("D") || move.equals("d")) {
				newCol += 1;
			} else if (move.equals("B") || move.equals("b")) {
				backMove = backMove(); 
				newRow = backMove[0]; 
				newCol = backMove[1]; 
			} else if (move.equals("T") || move.equals("t")) {
				teleportTile = teleportTo(scan); 
				newRow = teleportTile[0]; 
				newCol = teleportTile[1]; 
			} else if (move.equals("I") || move.equals("i")) {
				showInformation();
			} else if (move.equals("M") || move.equals("m")) {
				map.printQuestMap();
			} else if (move.equals("Q") || move.equals("q")) {
				System.out.println("Thank you for playing T H E  Q U E S T.");
				return true; // the game ends if the user decides to quit
			} else {
				newRow = -1;
				newCol = -1;
			}
			
			if (move.equals("I") || move.equals("i") || move.equals("M") || move.equals("m")) {
				isValidPosition = false; // set to false to allow while loop
											// to continue so player can
											// enter a move
			} else {
				isValidPosition = isValidPosition(newRow, newCol); 
				if (!isValidPosition) {
					System.out.println("This is not a valid move. Please enter another move.");
				}
			
				if (move.equals("B") || move.equals("b")) {
					exitAndEnter(scan,newRow, newCol);
					isValidPosition = false; 
				}	
			}
		}
		
		postRoundRegainInGame(); // increase HP and mana by 10% after they make a move 
		exitAndEnter(scan, newRow, newCol); // set the new position of the player based on where they want to go
		return (hasWon()); // exit their current tile, enter the new tile
	}
	
	// method that places heroes back in their nexus if they enter b 
	public int[] backMove() {
		int[] move = new int[2]; 
		move[0] = 7; 
		if (heroLabel.equals("H1")) {
			move[1] = 0;  
		} else if (heroLabel.equals("H2")) {
			move[1] = 3; 
		} else if (heroLabel.equals("H3")) {
			move[1] = 6;
		}
		return move; 
		
	}
	
	// method that places heroes back in their nexus if they lose a fight 
	public void respawn() {
		map.getGrid()[rowPosition][colPosition].exit(this);
		setRowPosition(7);
		if (heroLabel.equals("H1")) {
			setColPosition(0);  
		} else if (heroLabel.equals("H2")) {
			setColPosition(3); 
		} else if (heroLabel.equals("H3")) {
			setColPosition(6);
		}
		map.getGrid()[rowPosition][colPosition].setHeroLabel(heroLabel);
		setHp(preFightHP); 
		
	}
	// method that has hero exit a tile and enter a different tile 
	private void exitAndEnter(Scanner scan, int newRow, int newCol) {
		map.getGrid()[rowPosition][colPosition].exit(this);
		setRowPosition(newRow);
		setColPosition(newCol);
		map.getGrid()[newRow][newCol].enter(this, scan);
		map.printQuestMap();
	}
	
	// method that asks for the player's desired teleport move
	// checks if the player has entered a valid teleport choice
	public int[] teleportTo(Scanner scan) {
		System.out.println(name.trim() + ", where would you like to teleport to?"); 
		int[] move = new int[2]; 
		
		do {
			System.out.print("Enter row number: ");
			move[0] = UtilityHelper.getIntFromScanner(scan);
			System.out.print("Enter column number: ");
			move[1] = UtilityHelper.getIntFromScanner(scan);
			// Checks if the input is a valid position, and whether the player's choice is within the same lane
			while (!isValidPosition(move[0], move[1]) || move[1] == colPosition || move[1] == colPosition + 1 || move[1] == colPosition - 1 || !isValidTeleport(move[0], move[1])) {
				System.out.println("This is not a valid move. Please enter another move."); 
				System.out.print("Enter row number: ");
				move[0] = UtilityHelper.getIntFromScanner(scan);
				System.out.print("Enter column number: ");
				move[1] = UtilityHelper.getIntFromScanner(scan);
			}
		} while (!isValidPosition(move[0], move[1])); 
		return move; 
	}
	
	// method that checks if the position a hero is trying to teleport to does not pass any monsters
	private boolean isValidTeleport(int newRow, int newCol) {
		if (newRow == 7) {
			return true;
		}
		int leftCol;
		int rightCol;
		if (newCol == 0 || newCol == 1) {
			leftCol = 0;
			rightCol = 1;
		} else if (newCol == 3 || newCol == 4) {
			leftCol = 3;
			rightCol = 4;
		} else {
			leftCol = 6;
			rightCol = 7;
		}
		for (int i = newRow + 1; i <= 7; i++) {
			for (int j = leftCol; j <= rightCol; j++) {
				if (!(map.getGrid()[i][j].getMonsterLabel().equals("  "))) { // if a monster is present 
					System.out.println("You cannot pass a monster.");
					return false;
				}
			}
		}
		return true;
	}
	
	// method to buy an item from the market
	public void buyItem(Market market, int itemID) {
		Item itemToBuy = new Item();
		for (Item i : market.allItems) {
			if (i.getId() == itemID) {
				itemToBuy = i;
				break;
			}
		}

		if (itemToBuy.getMinHeroLevel() > level) {
			System.out.println(
					"Sorry, " + name.trim() + " must reach level " + itemToBuy.getMinHeroLevel() + " to buy this item.");
		} else if (itemToBuy.getPrice() > money) {
			System.out.println("Sorry, " + name + " does not have enough money for this item.");
		} else {
			items.add(itemToBuy);
			money = money - itemToBuy.getPrice();
			System.out.println(name.trim() + " has bought the following item: " + itemToBuy.getName());
		}

	}

	// method to sell an item to the market at half price
	public void sellItem(Market market, int itemID) {
		Item itemToSell = new Item();
		for (Item i : items) {
			if (i.getId() == itemID) {
				itemToSell = i;
				break;
			}
		}
		System.out.println(name.trim() + " has sold the following item: " + itemToSell.getName());
		money = (int) (money + 0.5 * itemToSell.getPrice());
		items.remove(itemToSell);
	}

	// method to get the ID of the item in the market from the user
	public int getMarketItemID(Market market, Scanner scan) {
		int itemID = -1;
		do {
			itemID = UtilityHelper.getIntFromScanner(scan);
		} while (!validMarketItemID(market, itemID));
		return itemID;
	}

	// method to get the ID of an item the hero owns from the user
	public int getHeroItemID(Scanner scan) {
		int itemID = -1;
		do {
			itemID = UtilityHelper.getIntFromScanner(scan);
		} while (!validHeroItemID(itemID));
		return itemID;
	}
	
	// method to get the ID of an item the hero owns from the user, including 0 as an acceptable input
	// used in the fight
	public int getHeroItemIDForFight(Scanner scan) {
		int itemID = -1;
		do {
			itemID = UtilityHelper.getIntFromScanner(scan);
			if (itemID == 0) {
				break; 
			}
		} while (!validHeroItemID(itemID));
		return itemID;
	}
	

	// method to check if the item ID matches an ID of an item in the market
	public boolean validMarketItemID(Market market, int itemID) {
		if (itemID < 1 || itemID > market.numItems) {
			System.out.print("Item with ID " + itemID + " does not exist. Please enter a different ID. ");
			return false;
		}
		return true;
	}

	// method to check if the item ID entered is an ID of an item the hero owns
	public boolean validHeroItemID(int itemID) {
		for (Item i : items) {
			if (i.getId() == itemID) {
				return true;
			}
		}
		System.out
				.print(name.trim() + " does not own item with item ID " + itemID + ". Please enter a different ID. ");
		return false;
	}

	// method that updates the hero's stats when they level up
	public void levelUp() {
		System.out.println(name.trim() + " just L E V E L E D  U P !"); 
		this.setLevel(level+1); // increase the level
		this.setHp(level * 100); // reset HP to level*100
		this.setMana(this.getMana() * 1.1); // increase mana by 10%
		upgradeSkills(); // upgrades skills depending on type of hero
	}

	// method that displays all the items a hero owns and the amount of damage they do
	public void displayItems() {
		System.out.println(name.trim() + "'s Available Items: ");
		for (Item i : items) {
			i.display();
		}
	}

	// method that checks if the hero dodged the attack
	public boolean dodgedAttack() {
		int randomInteger = (int) (100.0 * Math.random());
		if (randomInteger < skill.getAgility() * 0.02) {
			return true;
		}
		return false;
	}

	// method that checks if the hero has fainted based on their HP 
	public boolean hasFainted() {
		return (hp < 1);
	}
	
	// method that increases hero's HP and mana after a round of a fight 
	public void postRoundRegainInFight() {
		this.setHp(hp*1.05); // increase HP by 5% 
		this.setMana(mana*1.05); // increase mana by 5%
	}
	
	// method that increases hero's HP and mana after a round of a game
	public void postRoundRegainInGame() {
		this.setHp(hp*1.1);
		this.setMana(mana*1.1);
	}
	
	// method that increases hero's money and experience after a successful fight
	public void getSuccessGains() {
		this.setMoney(money + 150);
		this.setExperience(experience + 2);
	}
	
	// method that checks if the hero has enough mana to cast a certain spell
	public boolean hasEnoughMana(Spell spell) {
		if (mana < spell.getManaDeduction()) {
			System.out.println(name.trim() + " does not have enough mana to cast this spell."); 
		}
		return (mana >= spell.getManaDeduction()); 
	}
	
	// method that keeps asking for another item until it's a valid item (after an invalid spell is attempted to be used) 
	public Item useAnotherItem(Scanner scan, Spell spell, Monster monster) {
		boolean itemFound = false; 
		Item itemToUse = spell; 
		while (!itemFound) {
			if(hasEnoughMana(spell)) {
				itemToUse = spell; 
				itemFound = true; 
				return itemToUse; 
			}
			else {
				System.out.print("Enter the ID of the item you would like " + name.trim() + " to use for this round: ");
				int itemID = getHeroItemIDForFight(scan); 
				
				if (itemID == 0) { // a regular attack, no item 
					attack(monster);
					monster.attack(this);
					return null; 
				}
				 
				else { // an item  is used 
					for (Item i : items) {
						if (itemID == i.getId()) {
							if (!(i instanceof Spell)) { // if the item is not a spell 
								itemToUse = i; 
								return itemToUse; 
							}
							else { // if the item is a spell 
								spell = (Spell) i; 
								useAnotherItem(scan, spell, monster); 
							}
						}
					}
				}	
			}
			
	
		}
		return itemToUse; 
		
	}
	
	// method to perform a regular attack on a monster
	public void attack(Monster monster) {
		if (monster.dodgedAttack()) { // if monster dodges an attack 
			System.out.println(monster.getName().trim() + " dodged the attack!"); 
		}
		else { // if monster doesn't dodge attack
			monster.setHp(monster.getHp() - (skill.getStrength() + monster.getDefense())*0.05);
		}
	}
	
	public void attack(Hero hero) {
		
	}

	// method that prints the hero's information
	public void showInformation() {
		System.out.println("ID\t Name\t\tHealth Power\t Money \tMana\t Experience\t Strength\t Dexterity\tAgility\t");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------"); 
		System.out.print(id + "\t" + name + "\t" + (int)hp + "\t\t "  + (int)money + "\t" + (int)mana + "\t " + (int)experience + "\t\t ");
		skill.printSkillSet(); 
		System.out.println();
	}
	
	public boolean hasWon() {
		return (rowPosition == 0); 
	}
}
