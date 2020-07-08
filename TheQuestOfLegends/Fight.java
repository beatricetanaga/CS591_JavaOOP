import java.util.*;
// A class that represents a fight in the game 
public class Fight {
	private Hero[] heroes;
	private Monster[] tempMonsters;
	private int round;
	
	public Fight(Hero[] heroes, Monster[] monsters) {
		this.heroes = heroes; 
		this.tempMonsters = monsters; 
		this.round = 1; 
	}

	public Hero[] getHeroes() {
		return heroes;
	}

	public void setHeroes(Hero[] heroes) {
		this.heroes = heroes;
	}

	public Monster[] getTempMonsters() {
		return tempMonsters;
	}

	public void setTempMonsters(Monster[] tempMonsters) {
		this.tempMonsters = tempMonsters;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public void start(Scanner scan) {
		// get the collective HP of each side to keep track of when the whole
		// side's fainted
		double totalHeroHP = 0;
		double totalMonsterHP = 0;
		round = 1;

		for (Hero h : heroes) {
			totalHeroHP += h.getHp();
		}
		for (Monster m : tempMonsters) {
			totalMonsterHP += m.getHp();
		}

		// set the initial index to the last fighter from each array of
		// opponents
		int currHeroIndex = heroes.length - 1;
		int currMonsterIndex = tempMonsters.length - 1;

		// start the actual fight
		while (totalHeroHP > 0 && totalMonsterHP > 0) {
			System.out.println("G E T  R E A D Y  F O R  R O U N D  " + round);
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			displayHeroAndMonsterInfo();
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


			for (Hero h : heroes) {
				h.displayItems();
				System.out.println();
			}

			int numActiveHeroes = 0;
			int numActiveMonsters = 0;

			for (Hero h : heroes) {
				if (!h.hasFainted()) {
					numActiveHeroes += 1;
				}
			}

			for (Monster m : tempMonsters) {
				if (!m.hasDied()) {
					numActiveMonsters += 1;
				}
			}

			int maxActivePlayers = Math.max(numActiveHeroes, numActiveMonsters);

			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("A T T A C K  I N F O R M A T I O N");
			System.out.println("Weapons damage the monster's HP.");
			System.out.println("Armors reduce the damage the monster does.");
			System.out.println("Potions can be used once to increase the hero's strength.");
			System.out.println("Spells cost mana and weaken the monster's skills.");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

			// code for a single round, which consists of multiple encounters where every hero and every monster performs at least one attack
			for (int encounter = 0; encounter < maxActivePlayers; encounter++) {

				// obtain the hero and monster index for each encounter in the
				// round
				do {
					currHeroIndex = nextTurn(currHeroIndex, heroes.length);
				} while (heroes[currHeroIndex].hasFainted());

				do {
					currMonsterIndex = nextTurn(currMonsterIndex, tempMonsters.length);
				} while (tempMonsters[currMonsterIndex].hasDied());

				// "re-enter" the tile so that hero's stats boost every round they are in that tile
				heroes[currHeroIndex].getMap().getGrid()[heroes[currHeroIndex].getRowPosition()][heroes[currHeroIndex]
						.getColPosition()].enter(heroes[currHeroIndex], scan); 
				
				System.out.println(heroes[currHeroIndex].getName().trim() + " IS FIGHTING "
						+ tempMonsters[currMonsterIndex].getName().trim());
				
				// obtain the move you would like the hero to perform 
				System.out.print("Enter the ID of the item you would like " + heroes[currHeroIndex].getName().trim()
						+ " to use for this round, or enter 0 to perform a regular attack: ");
				int itemID = heroes[currHeroIndex].getHeroItemIDForFight(scan);

				if (itemID == 0) { // if they are performing a regular attack 
					heroes[currHeroIndex].attack(tempMonsters[currMonsterIndex]);
					tempMonsters[currMonsterIndex].attack(heroes[currHeroIndex]);

				} else { // if they are using an item 
					for (Item i : heroes[currHeroIndex].getItems()) {
						if (itemID == i.getId()) {
							if (i instanceof Spell) {
								i = heroes[currHeroIndex].useAnotherItem(scan, (Spell) i,
										tempMonsters[currMonsterIndex]);
							}
							if (i != null) {
								i.use(heroes[currHeroIndex], tempMonsters[currMonsterIndex]); // hero's attack on monster 
																							
								tempMonsters[currMonsterIndex].attack(heroes[currHeroIndex]); // monster's attack on hero 
							}
							break;
						}
					}
				}

				// adjust the heroes' mana and HP and monsters' HP to 0 when they are below 1 
				adjustLowStats(heroes, tempMonsters);
				
			
				if (heroesLost()) { // if all of the heroes have fainted, end the game 
					System.out.println("The monsters have won - the fight has ended. Better luck next time!");
					displayHeroAndMonsterInfo();
					totalHeroHP = 0; 
					break; 
				} else if (allMonstersDied()) { // if all of the monsters have fainted, perform victory calculations and proceed 
					System.out.println("V I C T O R Y ! You have defeated the monsters.");

					for (Hero h : heroes) {
						if (h.hasFainted()) {
							h.respawn(); 
						} else {
							h.getSuccessGains();
						}
						if (h.getExperience() >= 10 * h.getLevel()) {
							h.levelUp();
						}
					}
					displayHeroAndMonsterInfo();
					totalMonsterHP = 0; 
					break;
				}

				displayHeroAndMonsterInfo();

				// calculate the total HP of each side after an encounter 
				totalHeroHP = 0;
				totalMonsterHP = 0;

				for (Hero h : heroes) {
					totalHeroHP += h.getHp();
				}

				for (Monster m : tempMonsters) {
					totalMonsterHP += m.getHp();
				}
			}

			// post round stats adjustment for all heroes who haven't fainted 
			for (Hero h : heroes) {
				if (!h.hasFainted()) {
					h.postRoundRegainInFight();
				}
			}
			round++;
		}
		
		for (Hero h : heroes) {
			if (h.hasFainted()) {
				h.respawn();
			}
		}
	}
	
	// method to check if the heroes have lost a fight
	private boolean heroesLost() {
		for (Hero h : heroes) {
			if (!h.hasFainted()) {
				return false; 
			}
		}
		return true; 
	}

	// method to display info on the heroes and the monsters 
	private void displayHeroAndMonsterInfo() {
		System.out.println("The Enemy's Troops:");
		showMonstersInformation();
		System.out.println("Your Team's Troops:");
		showHeroesInformation(heroes);
	}

	// method that sets HP and mana to 0 when it drops below 1 (meaning it's a decimal) 
	private void adjustLowStats(Hero[] heroes, Monster[] tempMonsters) {
		for (int currIndex = 0; currIndex < heroes.length; currIndex++) {
			if (heroes[currIndex].getHp() < 1) { // if HP is less than 1 set it to 0 
				heroes[currIndex].setHp(0);
			}
			
			if (heroes[currIndex].getMana() < 1) { // if mana is less than 1 set it to 0 
				heroes[currIndex].setMana(0);
			}
		}
		
		for (int currIndex = 0; currIndex < tempMonsters.length; currIndex++) {
			if (tempMonsters[currIndex].getHp() < 1) { // if HP is less than 1 set it to 0 
				tempMonsters[currIndex].setHp(0);
			}
		}
	}

	// method that prints information on the monsters in the fight 
	public void showMonstersInformation() {
		System.out.println("Name\t\tHealth Power\t Damage\t\tDefense\t\tDodge Probability\t\t");
		System.out.println(
				"---------------------------------------------------------------------------------------------");
		for (Monster m : this.getTempMonsters()) {
			System.out.println(m.getName() + "\t" + (int) m.getHp() + "\t\t " + (int) m.getDamage() + "\t\t"
					+ (int) m.getDefense() + "\t\t" + (int) m.getDodge());
		}
		System.out.println();
	}
	
	// method that prints information on the player's heroes 
	public void showHeroesInformation(Hero[] heroes) {
		System.out.println("ID\t Name\t\tHealth Power\t Money \tMana\t Experience\t Strength\t Dexterity\tAgility\t");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------"); 
		for (Hero h : heroes) {
			System.out.print(h.getId() + "\t" + h.getName() + "\t" + (int)h.getHp() + "\t\t "  + (int)h.getMoney() + "\t" + (int)h.getMana() + "\t " + (int)h.getExperience() + "\t\t ");
			h.getSkill().printSkillSet(); 
		}
		System.out.println();
	}

	// method that switches the index of the current player so that heroes take turn fighting 
	public int nextTurn(int currPlayerIndex, int numPlayers) {
		int newPlayerIndex = (currPlayerIndex + 1) % numPlayers;
		return newPlayerIndex;
	}
	
	// method that checks if all the monsters in the fight have died 
	public boolean allMonstersDied() {
		for (Monster m : tempMonsters) {
			if (!m.hasDied()) {
				return false;
			}
		}
		return true; 
	}

}
